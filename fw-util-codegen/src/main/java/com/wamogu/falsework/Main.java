package com.wamogu.falsework;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.TableComment;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.javapoet.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@UtilityClass
public class Main {
    interface SUFFIX {
        String VO = "Vo";
        String DTO = "Dto";
        String QUERY = "Query";
        String BIZ_SERVICE = "BizService";
        String CONTROLLER = "Controller";
        String REPOSITORY = "Repository";
        String CASTOR = "Castor";
    }

    interface CLASSNAME {
        ClassName classBaseBizService = ClassName.get("com.wamogu.kit", "BaseBizService");
        ClassName classBaseCastor = ClassName.get("com.wamogu.kit", "BaseCastor");
        ClassName classBaseController = ClassName.get("com.wamogu.kit", "BaseController");
        ClassName classFwQueryBase = ClassName.get("com.wamogu.querykit", "FwQueryBase");
        ClassName classFwQuery = ClassName.get("com.wamogu.querykit.anno", "FwQuery");
    }
    public void main(String[] args) {
        ClassUtil.scanPackage("com.wamogu.entity")
                .stream().filter(x -> x.getAnnotation(Table.class) != null)
                .forEach(x -> {
                    generate(x);
                });
    }

    private void generate(Class<?> enClass) {
        createPojos(enClass, List.of("fw-biz"));
        createCastor(enClass, List.of("fw-biz"));
        createBizService(enClass, List.of("fw-biz"));
        createBizController(enClass, List.of("fw-app-mgr"));
    }

    private void createCastor(Class<?> enClass, List<String> projects) {

        ClassName entityClass = ClassName.get(enClass);

        String enName = enClass.getSimpleName();
        String bizPack = enClass.getPackageName().replace("entity", "biz");
        String bizPojoPack = bizPack + ".pojo";

        ClassName dtoClass = ClassName.get(bizPojoPack, enName+SUFFIX.DTO);
        ClassName voClass = ClassName.get(bizPojoPack, enName+SUFFIX.VO);

        String castorClassName = enClass.getSimpleName()+SUFFIX.CASTOR;

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .interfaceBuilder(castorClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(CLASSNAME.classBaseCastor, entityClass, dtoClass, voClass))
                .addAnnotation(AnnotationSpec.builder(Mapper.class)
                        .addMember("componentModel", "$S", "spring")
                        .addMember("uses", "{}")
                        .addMember("unmappedTargetPolicy", "$T.IGNORE", ReportingPolicy.class)
                        .build());

        try {
            writeJavaFile(bizPack+".convert", typeSpecBuilder, projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBizService(Class<?> enClass, List<String> projects) {
        ClassName entityClass = ClassName.get(enClass);

        String enName = enClass.getSimpleName();
        String bizPack = enClass.getPackageName().replace("entity", "biz");
        String bizPojoPack = bizPack + ".pojo";

        ClassName dtoClass = ClassName.get(bizPojoPack, enName+SUFFIX.DTO);
        ClassName voClass = ClassName.get(bizPojoPack, enName+SUFFIX.VO);
        ClassName queryClass = ClassName.get(bizPojoPack, enName+SUFFIX.QUERY);
        ClassName repositoryClass = ClassName.get("com.wamogu.dao.repository", enName+SUFFIX.REPOSITORY);
        ClassName castorClass = ClassName.get(bizPack+".convert", enName+SUFFIX.CASTOR);
        ClassName pkClass = ClassName.get(getPKType(enClass));

        String bizServiceClassName = enClass.getSimpleName()+SUFFIX.BIZ_SERVICE;

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(bizServiceClassName)
                .superclass(ParameterizedTypeName.get(CLASSNAME.classBaseBizService, entityClass, dtoClass, voClass, pkClass))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Service.class)
                .addAnnotation(RequiredArgsConstructor.class);

        Map<String, ClassName> fields = new TreeMap<>();
        fields.put("baseRepository", repositoryClass);
        fields.put("baseCastor", castorClass);

        fields.entrySet().forEach(x -> {
            typeSpecBuilder.addField(FieldSpec.builder(x.getValue(), x.getKey(), Modifier.PRIVATE, Modifier.FINAL).build());

            typeSpecBuilder.addMethod(MethodSpec.methodBuilder("get"+CharSequenceUtil.upperFirst(x.getKey()))
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .returns(x.getValue())
                    .addCode(String.format("return this.%s;", x.getKey()))
                    .build());
        });

        try {
            writeJavaFile(bizPack+".service", typeSpecBuilder, projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Class<?> getPKType(Class<?> enClass) {
        Optional<Field> pkField = Arrays.stream(ReflectUtil.getFields(enClass))
                .filter(x -> x.getAnnotation(ColumnId.class) != null || x.getAnnotation(TableId.class) != null)
                .findFirst();
        if (pkField.isEmpty()) {
            throw new RuntimeException("%s 找不到 @ColumnId 或 @TableId");
        }
        return pkField.get().getType();
    }

    private void createBizController(Class<?> enClass, List<String> projects) {

        String bizPack = enClass.getPackageName().replace("entity", "biz");
        String bizPojoPack = bizPack + ".pojo";

        ClassName entityClass = ClassName.get(enClass);
        ClassName dtoClass = ClassName.get(bizPojoPack, enClass.getSimpleName()+SUFFIX.DTO);
        ClassName voClass = ClassName.get(bizPojoPack, enClass.getSimpleName()+SUFFIX.VO);
        ClassName queryClass = ClassName.get(bizPojoPack, enClass.getSimpleName()+SUFFIX.QUERY);
        ClassName pkClass = ClassName.get(getPKType(enClass));
        ClassName bizServiceClass = ClassName.get(bizPack+".service", enClass.getSimpleName()+SUFFIX.BIZ_SERVICE);

        String comment = CharSequenceUtil.blankToDefault(enClass.getAnnotation(Table.class).comment(), enClass.getSimpleName());
        String controllerClassName = enClass.getSimpleName()+SUFFIX.CONTROLLER;
        String pack = enClass.getPackageName().replace("entity", "rest");

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(controllerClassName)
                .superclass(ParameterizedTypeName.get(CLASSNAME.classBaseController, entityClass, dtoClass, voClass, queryClass, pkClass))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(ResponseBody.class)
                .addAnnotation(RestController.class)
                .addAnnotation(RequiredArgsConstructor.class)
                .addAnnotation(AnnotationSpec.builder(Tag.class).addMember("name", "$S", comment).build())
                .addAnnotation(AnnotationSpec.builder(RequestMapping.class).addMember("value", "$S", "/"+CharSequenceUtil.toUnderlineCase(enClass.getSimpleName())).build())
                ;

        Map<String, ClassName> fields = new TreeMap<>();
        fields.put("bizService", bizServiceClass);

        fields.entrySet().forEach(x -> {
            typeSpecBuilder.addField(FieldSpec.builder(x.getValue(), x.getKey(), Modifier.PRIVATE, Modifier.FINAL).build());

            typeSpecBuilder.addMethod(MethodSpec.methodBuilder("get"+CharSequenceUtil.upperFirst(x.getKey()))
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(x.getValue())
                    .addCode(String.format("return this.%s;", x.getKey()))
                    .build());
        });

        try {
            writeJavaFile(pack, typeSpecBuilder, projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPojos(Class<?> enClass, List<String> projects) {
        createVo(enClass, projects);
        createQuery(enClass, projects);
        createDto(enClass, projects);
    }
    private void createDto(Class<?> enClass, List<String> projects) {
        String className = enClass.getSimpleName()+SUFFIX.DTO;
        String pack = enClass.getPackageName().replace("entity", "biz")+".pojo";

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Data.class)
                .addAnnotation(swagger3Schema(className));

        List<Class<? extends Object>> supportedClasses = List.of(Integer.class, int.class, Double.class, double.class, Long.class, long.class, String.class, CharSequence.class);
        Arrays.stream(ReflectUtil.getFields(enClass)).forEachOrdered(x -> {
            String columnComment = getColumnComment(x);
            FieldSpec.Builder fieldSpecBuilder = FieldSpec.builder(x.getType(), x.getName(), Modifier.PRIVATE)
                    .addAnnotation(swagger3Schema(columnComment));
            Arrays.stream(x.getAnnotations()).forEach(anno -> {
                if (anno.annotationType().getPackageName().contains("jakarta.validation.constraints")) {
                    AnnotationSpec.Builder annoSpecBuilder = AnnotationSpec.builder(anno.annotationType());
                    Arrays.stream(anno.annotationType().getDeclaredMethods()).forEach(method-> {
                        if (supportedClasses.contains(method.getReturnType())) {
                            Object r = ReflectUtil.invoke(anno, method);
                            if (r != null) {
                                annoSpecBuilder.addMember(method.getName(), "$S", r);
                            }
                        }
                    });
                    fieldSpecBuilder.addAnnotation(annoSpecBuilder.build());
                }
            });
            typeSpecBuilder.addField(fieldSpecBuilder.build());
        });

        try {
            writeJavaFile(pack, typeSpecBuilder, projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getColumnComment(Field x) {
        ColumnComment cc = x.getAnnotation(ColumnComment.class);
        if (cc != null) {
            return CharSequenceUtil.blankToDefault(cc.value(), x.getName());
        }
        Column col = x.getAnnotation(Column.class);
        if (col != null) {
            return CharSequenceUtil.blankToDefault(col.comment(), x.getName());
        }
        return x.getName();
    }

    private void writeJavaFile(String pack, TypeSpec.Builder typeSpecBuilder, List<String> projects) throws IOException {
        typeSpecBuilder.addJavadoc(String.format("""
                generated by FwUtilCodegen
                @since %s
                """, DateUtil.today()));
        TypeSpec typeSpec = typeSpecBuilder.build();
        JavaFile javaFile = JavaFile.builder(pack, typeSpec)
                .skipJavaLangImports(true)
                .indent("    ")
                .addFileComment("")
                .build();

        String baseDir = "/Users/aminby/MacDir/earnings/falsework";
        for (String prj : projects) {
            String filepath = String.format("%s/%s/src/main/java/%s/%s.java", baseDir, prj, javaFile.packageName.replaceAll("[.]", "/"), javaFile.typeSpec.name);
            if (FileUtil.exist(filepath)) {
                System.out.println("skipped " + filepath);
                continue;
            }
            javaFile.writeTo(FileUtil.file(filepath));
        }
    }

    private void createQuery(Class<?> enClass, List<String> projects) {
        String className = enClass.getSimpleName()+SUFFIX.QUERY;
        String pack = enClass.getPackageName().replace("entity", "biz")+".pojo";

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(className)
                .superclass(CLASSNAME.classFwQueryBase)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Data.class)
                .addAnnotation(AnnotationSpec.builder(EqualsAndHashCode.class).addMember("callSuper", "true").build())
                ;

        Arrays.stream(ReflectUtil.getFields(enClass)).forEachOrdered(x -> {
            String columnComment = getColumnComment(x);
            typeSpecBuilder.addField(FieldSpec.builder(x.getType(), x.getName(), Modifier.PRIVATE)
                    .addAnnotation(swagger3Schema(columnComment))
                    .addAnnotation(CLASSNAME.classFwQuery)
                    .build());
        });

        try {
            writeJavaFile(pack, typeSpecBuilder, projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createVo(Class<?> enClass, List<String> projects) {
        String className = enClass.getSimpleName()+SUFFIX.VO;
        String pack = enClass.getPackageName().replace("entity", "biz")+".pojo";
        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Data.class)
                .addAnnotation(swagger3Schema(String.format("%s VO", getTableComment(enClass))));


        Arrays.stream(ReflectUtil.getFields(enClass)).forEachOrdered(x -> {
            String columnComment = getColumnComment(x);
            typeSpecBuilder.addField(FieldSpec.builder(x.getType(), x.getName(), Modifier.PRIVATE)
                    .addAnnotation(swagger3Schema(columnComment))
                    .build());
        });

        try {
            writeJavaFile(pack, typeSpecBuilder, projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTableComment(Class<?> enClass) {
        TableComment tc = enClass.getAnnotation(TableComment.class);
        if (tc != null) {
            return CharSequenceUtil.blankToDefault(tc.value(), enClass.getSimpleName());
        }
        Table tb = enClass.getAnnotation(Table.class);
        if (tb != null) {
            return CharSequenceUtil.blankToDefault(tb.comment(), enClass.getSimpleName());
        }
        return null;
    }

    private AnnotationSpec swagger3Schema(String title) {
        return AnnotationSpec.builder(Schema.class)
                .addMember("title", "$S", title)
                .addMember("description", "$S", title)
                .build();
    }
}