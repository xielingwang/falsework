package com.wamogu.querykit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wamogu.exception.ErrorKit;
import com.wamogu.querykit.anno.FwQuery;
import com.wamogu.querykit.anno.MbpEditIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.wamogu.querykit.FwQueryKit.FlagEnum.*;

/**
 * mybatis plus工具类
 * 查询条件多的时候一个一个写好麻烦, 这个工具类可以根据注解自动构建查询条件并完成分页查询
 *
 * @Author Armin
 * @date 24-05-28 21:19
 */
public class FwQueryKit {

    protected enum FlagEnum {
        BetweenMax("Max"), BetweenMin("Min")
        , SortAsc("+"), SortDesc("-");

        private final String suffix;
        public String suffix() {
            return suffix;
        }

        FlagEnum(String suffix) {
            this.suffix = suffix;
        }
    }
    /**
     * id字段名
     */
    private static final String idFieldName = "id";
    /**
     * 排序列名
     */
    public static final String orderByFieldName = "orderBy";
    /**
     * 排序方向
     */
    public static final String sortDirection = "sortDirection";
    /**
     * 排序列排序方式
     */
    private static final char orderBySeparator = ',';

    /**
     * 用于快速构建检索条件
     *
     * @param service   service对象
     * @param query     检索条件, query对象
     * @param autoOrder 是否自动排序, 为true将取请求行中的排序参数进行排序
     * @param <M>       mapper interface
     * @param <T>       实体类
     * @return 构建完成的检索条件
     */
    public static <M extends BaseMapper<T>, T> LambdaQueryChainWrapper<T> buildSearch(IService<T> service, FwQueryBase query, boolean autoOrder) {
        LambdaQueryChainWrapper<T> result = service.lambdaQuery();
        HashMap<Field, Object> fieldValueMap = new HashMap<>(8);

        for (Field field : FieldUtils.getFieldsListWithAnnotation(query.getClass(), FwQuery.class)) {
            try {
                Object value = FieldUtils.readField(field, query, true);
                if (invalidValue(value)) {
                    continue;
                }
                fieldValueMap.put(field, value);
            } catch (IllegalAccessException ignored) {
            }
        }
        if (fieldValueMap.isEmpty()) {
            return result;
        }
        HashMap<Field, Object> betweenFieldValueMap = new HashMap<>(16);

        String entityClassName = service.getEntityClass().getName();
        Function<String, SFunctionMask<T>> nameMask = fieldName -> new SFunctionMask<>(fieldName, entityClassName);
        Function<Field, SFunctionMask<T>> mask = field -> new SFunctionMask<>(field.getName(), entityClassName);
        // 处理非between字段
        fieldValueMap.forEach((field, value) -> {
            FwQuery annotation = field.getAnnotation(FwQuery.class);
            switch (annotation.type()) {
                case EQ -> result.eq(mask.apply(field), value);
                case NE -> result.ne(mask.apply(field), value);
                case GT -> result.gt(mask.apply(field), value);
                case GE -> result.ge(mask.apply(field), value);
                case LT -> result.lt(mask.apply(field), value);
                case LE -> result.le(mask.apply(field), value);
                case LIKE -> result.like(mask.apply(field), value);
                case NOT_LIKE -> result.notLike(mask.apply(field), value);
                case LIKE_LEFT -> result.likeLeft(mask.apply(field), value);
                case LIKE_RIGHT -> result.likeRight(mask.apply(field), value);
                case NOT_LIKE_LEFT -> result.notLikeLeft(mask.apply(field), value);
                case NOT_LIKE_RIGHT -> result.notLikeRight(mask.apply(field), value);
                case IN -> result.in(mask.apply(field), value);
                case NOT_IN -> result.notIn(mask.apply(field), value);
                case BETWEEN, NOT_BETWEEN -> betweenFieldValueMap.put(field, value);
                default -> throw new ErrorKit.Fatal("架构支持能力不足");
            }
        });
        // 处理between字段
        betweenFieldValueMap.forEach((field, value) -> {
            if (field.getName().endsWith(BetweenMin.suffix())) {
                String fieldName = StringUtils.removeEnd(field.getName(), BetweenMin.suffix());
                String maxFieldName = fieldName + BetweenMax.suffix();
                Optional<Field> maxFieldOptional = betweenFieldValueMap.keySet().stream()
                        .filter(it -> it.getName().contentEquals(maxFieldName)).findFirst();
                if (maxFieldOptional.isEmpty()) {
                    return;
                }
                Field maxField = maxFieldOptional.get();
                Object maxValue = betweenFieldValueMap.get(maxField);
                FwQuery annotation = field.getAnnotation(FwQuery.class);
                // noinspection AlibabaSwitchStatement
                switch (annotation.type()) {
                    case BETWEEN -> result.between(nameMask.apply(fieldName), value, maxValue);
                    case NOT_BETWEEN -> result.notBetween(nameMask.apply(fieldName), value, maxValue);
                    default -> throw new ErrorKit.Fatal("架构支持能力不足");
                }
            }
        });

        // 排序
        do {
            if (!autoOrder) {
                break;
            }
            if (StringUtils.isEmpty(query.getOrderBy())) {
                break;
            }
            Arrays.stream(StringUtils.split(query.getOrderBy(), orderBySeparator)).forEach(sortByCol -> {
                boolean isAsc = true;
                if (sortByCol.endsWith(SortAsc.suffix())) {
                    isAsc = true;
                    sortByCol = StringUtils.removeEnd(sortByCol, SortAsc.suffix());
                } else if (sortByCol.endsWith(SortDesc.suffix())) {
                    isAsc = false;
                    sortByCol = StringUtils.removeEnd(sortByCol, SortDesc.suffix());
                }
                result.orderBy(true, isAsc, nameMask.apply(sortByCol));
            });
        } while (false);

        return result;
    }

    /**
     * 用于快速构建检索条件, 自动排序
     *
     * @param service service对象
     * @param query   检索条件, query对象
     * @param <M>     mapper interface
     * @param <T>     实体类
     * @return 构建完成的检索条件
     */
    public static <M extends BaseMapper<T>, T> LambdaQueryChainWrapper<T> buildSearch(IService<T> service, FwQueryBase query) {
        return buildSearch(service, query, true);
    }


    /**
     * 快速构建检索条件并分页
     *
     * @param service   service对象
     * @param query     检索条件, query对象
     * @param autoOrder 是否自动排序
     * @param <M>       mapper interface
     * @param <T>       实体类
     * @return 分页结果
     */
    public static <M extends BaseMapper<T>, T> Page<T> page(ServiceImpl<M, T> service, FwQueryBase query, boolean autoOrder) {
        Page<T> page = query.getPage(service.getEntityClass());
        return buildSearch(service, query, autoOrder)
                .page(page);
    }

    /**
     * 快速构建检索条件并分页, 自动排序
     *
     * @param service service对象
     * @param query   检索条件, query对象
     * @param <M>     mapper interface
     * @param <T>     实体类
     * @return 分页结果
     */
    public static <M extends BaseMapper<T>, T> Page<T> page(ServiceImpl<M, T> service, FwQueryBase query) {
        return page(service, query, true);
    }

    /**
     * id存在则执行更新操作, 否则执行删除操作, 仅操作dto内包含的对象
     *
     * @return 成功与否, 如修改失败则返回false, 其余均为true
     */
    public static <M extends BaseMapper<T>, T> boolean edit(ServiceImpl<M, T> service, Object record) {
        String entityClassName = service.getEntityClass().getName();
        Function<Field, SFunctionMask<T>> mask = field -> new SFunctionMask<>(field.getName(), entityClassName);
        Field idField = FieldUtils.getField(record.getClass(), idFieldName, true);
        if (Objects.isNull(idField)) {
            return service.save(obj2bean(record, service.getEntityClass()));
        }
        LambdaUpdateChainWrapper<T> updateChainWrapper;
        try {
            Object id = FieldUtils.readField(idField, record, true);
            if (invalidValue(id)) {
                return service.save(obj2bean(record, service.getEntityClass()));
            }
            updateChainWrapper = service.lambdaUpdate().eq(mask.apply(idField), id);
            for (Field field : FieldUtils.getAllFieldsList(record.getClass())) {
                if (idFieldName.equals(field.getName())) {
                    continue;
                }
                if (field.isAnnotationPresent(MbpEditIgnore.class)) {
                    continue;
                }
                updateChainWrapper.set(mask.apply(field), FieldUtils.readField(field, record, true));
            }
        } catch (IllegalAccessException e) {
            throw new ErrorKit.Fatal("可能需要提供id字段", e);
        }
        return updateChainWrapper.update();
    }

    /**
     * 非null, 非空字符串, 即为有效值
     *
     * @return true表示值有效
     */
    private static boolean invalidValue(Object value) {
        return Objects.isNull(value) || (value instanceof String && StringUtils.isEmpty((String) value));
    }

    private static <T> T obj2bean(Object obj, Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(obj, t);
        return t;
    }
}
