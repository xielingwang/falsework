/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.querykit;

/**
 * mbp检索类型 @Author Armin
 *
 * @date 24-05-28 21:18
 */
public enum FwQueryEnum {

    /** 等于 */
    EQ,
    /** 不等于 */
    NE,
    /** 大于 */
    GT,
    /** 大于等于 */
    GE,
    /** 小于 */
    LT,
    /** 小于等于 */
    LE,
    /** 区间 需要有两个同样前缀的参数, 分别以Min和Max结尾, 表示区间的最小值和最大值 */
    BETWEEN,
    /** 不在区间 需要有两个同样前缀的参数, 分别以Min和Max结尾, 表示区间的最小值和最大值 */
    NOT_BETWEEN,
    /** 模糊匹配 */
    LIKE,
    /** 不模糊匹配 */
    NOT_LIKE,
    /** 左模糊匹配 */
    LIKE_LEFT,
    /** 右模糊匹配 */
    LIKE_RIGHT,
    /** 不左模糊匹配 */
    NOT_LIKE_LEFT,
    /** 不右模糊匹配 */
    NOT_LIKE_RIGHT,
    /** 在范围内 */
    IN,
    /** 不在范围内 */
    NOT_IN
}
