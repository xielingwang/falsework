package com.wamogu.falsework;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.TableComment;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ReflectionUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.javapoet.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.*;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Slf4j
@UtilityClass
public class CodegenApp {
    static final String CLASS_PREFIX = "Fw";
    interface SUFFIX {
        String VO = "Vo";
        String DTO = "Dto";
        String QUERY = "Query";
        String BIZ_SERVICE = "BizService";
        String CONTROLLER = "Controller";
        String REPOSITORY = "Repository";
        String CASTOR = "Castor";
    }

    interface BASE_CLASS {
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
        Map<String, ClassName> map = new HashMap<>();
        map.put("PKType", ClassName.get(getPKType(enClass)));
        map.put(SUFFIX.REPOSITORY, ClassName.get("com.wamogu.dao.repository", enClass.getSimpleName()+SUFFIX.REPOSITORY));
        map.put(SUFFIX.VO, createVo(enClass, List.of("fw-biz"), map, true));
        map.put(SUFFIX.QUERY, createQuery(enClass, List.of("fw-biz"), map, true));
        map.put(SUFFIX.DTO, createDto(enClass, List.of("fw-biz"), map, true));
        map.put(SUFFIX.CASTOR, createCastor(enClass, List.of("fw-biz"), map, true));
        map.put(SUFFIX.BIZ_SERVICE, createBizService(enClass, List.of("fw-biz"), map, false));
        map.put(SUFFIX.CONTROLLER, createBizController(enClass, List.of("fw-app-mgr"), map, false));
    }

    private ClassName createCastor(Class<?> enClass, List<String> projects, Map<String, ClassName> map, boolean override) {

        ClassName entityClass = ClassName.get(enClass);

        String bizPack = enClass.getPackageName().replace("entity", "biz");

        ClassName dtoClass = map.get(SUFFIX.DTO);
        ClassName voClass = map.get(SUFFIX.VO);

        String castorClassName = CLASS_PREFIX+enClass.getSimpleName()+SUFFIX.CASTOR;

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .interfaceBuilder(castorClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(BASE_CLASS.classBaseCastor, entityClass, dtoClass, voClass))
                .addAnnotation(AnnotationSpec.builder(Mapper.class)
                        .addMember("componentModel", "$S", "spring")
                        .addMember("uses", "{}")
                        .addMember("unmappedTargetPolicy", "$T.IGNORE", ReportingPolicy.class)
                        .build());

        try {
            return writeJavaFile(bizPack+".convert", typeSpecBuilder, projects, override);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClassName createBizService(Class<?> enClass, List<String> projects, Map<String, ClassName> map, boolean override) {
        ClassName entityClass = ClassName.get(enClass);

        String bizPack = enClass.getPackageName().replace("entity", "biz");

        ClassName dtoClass = map.get(SUFFIX.DTO);
        ClassName voClass = map.get(SUFFIX.VO);
        ClassName castorClass = map.get(SUFFIX.CASTOR);
        ClassName repositoryClass = map.get(SUFFIX.REPOSITORY);
        ClassName pkClass = map.get("PKType");

        String bizServiceClassName = CLASS_PREFIX+enClass.getSimpleName()+SUFFIX.BIZ_SERVICE;

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(bizServiceClassName)
                .superclass(ParameterizedTypeName.get(BASE_CLASS.classBaseBizService, entityClass, dtoClass, voClass, pkClass))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Getter.class)
                .addAnnotation(Service.class)
                .addAnnotation(RequiredArgsConstructor.class);

        Map<String, ClassName> fields = new TreeMap<>();
        fields.put("baseRepository", repositoryClass);
        fields.put("baseCastor", castorClass);

        fields.entrySet().forEach(x -> {
            typeSpecBuilder.addField(FieldSpec.builder(x.getValue(), x.getKey(), Modifier.PRIVATE, Modifier.FINAL).build());
        });

        try {
            return writeJavaFile(bizPack+".service", typeSpecBuilder, projects, override);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private ClassName createBizController(Class<?> enClass, List<String> projects, Map<String, ClassName> map, boolean override) {

        ClassName entityClass = ClassName.get(enClass);
        ClassName dtoClass = map.get(SUFFIX.DTO);
        ClassName voClass = map.get(SUFFIX.VO);
        ClassName queryClass = map.get(SUFFIX.QUERY);
        ClassName bizServiceClass = map.get(SUFFIX.BIZ_SERVICE);
        ClassName pkClass = map.get("PKType");

        String comment = CharSequenceUtil.blankToDefault(enClass.getAnnotation(Table.class).comment(), enClass.getSimpleName());
        String controllerClassName = CLASS_PREFIX+enClass.getSimpleName()+SUFFIX.CONTROLLER;
        String pack = enClass.getPackageName().replace("entity", "rest");

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(controllerClassName)
                .superclass(ParameterizedTypeName.get(BASE_CLASS.classBaseController, entityClass, dtoClass, voClass, queryClass, pkClass))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Getter.class)
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
        });

        try {
            return writeJavaFile(pack, typeSpecBuilder, projects, override);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClassName createDto(Class<?> enClass, List<String> projects, Map<String, ClassName> map, boolean override) {
        String className = CLASS_PREFIX+enClass.getSimpleName()+SUFFIX.DTO;
        String pack = enClass.getPackageName().replace("entity", "biz")+".pojo";

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Data.class)
                .addAnnotation(swagger3Schema(className));

        List<Class<? extends Object>> supportedClasses = List.of(Integer.class, int.class, Double.class, double.class, Long.class, long.class, String.class, CharSequence.class);
        Arrays.stream(ReflectUtil.getFields(enClass)).forEachOrdered(x -> {
            String columnComment = getColumnComment(x);
            FieldSpec.Builder fieldSpecBuilder = FieldSpec.builder(toTypename(x), x.getName(), Modifier.PRIVATE)
                    .addAnnotation(swagger3Schema(columnComment));
            Arrays.stream(x.getAnnotations()).forEach(anno -> {
                if (anno.annotationType().getPackageName().contains("jakarta.validation.constraints")) {
                    AnnotationSpec.Builder annoSpecBuilder = AnnotationSpec.builder(anno.annotationType());
                    Arrays.stream(anno.annotationType().getDeclaredMethods()).forEach(method-> {
                        if (supportedClasses.contains(method.getReturnType())) {
                            Object r = ReflectUtil.invoke(anno, method);
                            if (r != null) {
                                if (r instanceof CharSequence && r.toString().startsWith("{")) {
                                    return;
                                }
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
            return writeJavaFile(pack, typeSpecBuilder, projects, override);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private ClassName writeJavaFile(String pack, TypeSpec.Builder typeSpecBuilder, List<String> projects, boolean override) throws IOException {
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

        String baseDir = ResourceUtils.getFile("").getAbsolutePath();
        for (String prj : projects) {
            String filepath = String.format("%s/%s/src/main/java/%s/%s.java", baseDir, prj, javaFile.packageName.replaceAll("[.]", "/"), javaFile.typeSpec.name);
            System.out.print(filepath);
            if (FileUtil.exist(filepath) && !override) {
                System.out.println(" skipped");
                continue;
            }

            String prjJavaDir = String.format("%s/%s/src/main/java", baseDir, prj);
            javaFile.writeTo(new File(prjJavaDir));
            System.out.println(" done");
        }
        return ClassName.get(pack, javaFile.typeSpec.name);
    }

    private ClassName createQuery(Class<?> enClass, List<String> projects, Map<String, ClassName> map, boolean override) {
        String className = CLASS_PREFIX+enClass.getSimpleName()+SUFFIX.QUERY;
        String pack = enClass.getPackageName().replace("entity", "biz")+".pojo";

        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(className)
                .superclass(BASE_CLASS.classFwQueryBase)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Data.class)
                .addAnnotation(AnnotationSpec.builder(EqualsAndHashCode.class).addMember("callSuper", "true").build())
                ;

        Arrays.stream(ReflectUtil.getFields(enClass)).forEachOrdered(x -> {
            String columnComment = getColumnComment(x);
            typeSpecBuilder.addField(FieldSpec.builder(toTypename(x), x.getName(), Modifier.PRIVATE)
                    .addAnnotation(swagger3Schema(columnComment))
                    .addAnnotation(BASE_CLASS.classFwQuery)
                    .build());
        });

        try {
            return writeJavaFile(pack, typeSpecBuilder, projects, override);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TypeName toTypename(Field field) {
        return TypeName.get(field.getGenericType());
    }

    private ClassName createVo(Class<?> enClass, List<String> projects, Map<String, ClassName> map, boolean override) {
        String className = CLASS_PREFIX+enClass.getSimpleName()+SUFFIX.VO;
        String pack = enClass.getPackageName().replace("entity", "biz")+".pojo";
        TypeSpec.Builder typeSpecBuilder = TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Data.class)
                .addAnnotation(swagger3Schema(String.format("%s VO", getTableComment(enClass))));


        Arrays.stream(ReflectUtil.getFields(enClass)).forEachOrdered(x -> {
            String columnComment = getColumnComment(x);
            typeSpecBuilder.addField(FieldSpec.builder(toTypename(x), x.getName(), Modifier.PRIVATE)
                    .addAnnotation(swagger3Schema(columnComment))
                    .build());
        });

        try {
            return writeJavaFile(pack, typeSpecBuilder, projects, override);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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