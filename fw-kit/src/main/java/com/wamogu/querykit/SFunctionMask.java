package com.wamogu.querykit;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.AllArgsConstructor;

import java.lang.invoke.SerializedLambda;

/**
 * 构造mbp的lambda表达式, 用以欺骗mbp
 *
 * @Author Armin
 * @date 24-05-28 21:21
 */
@AllArgsConstructor
class SFunctionMask<T> implements SFunction<T, Object> {
    private String fieldName;
    private String instantiatedMethodType;

    @Override
    public Object apply(T t) {
        return null;
    }

    @SuppressWarnings("unused")
    private SerializedLambda writeReplace() {
        return new SerializedLambda(
                null,
                null,
                null,
                null,
                0,
                null,
                "get"+fieldName,
                null,
                "LY"+instantiatedMethodType+";",
                new Object[0]
        );
    }
}