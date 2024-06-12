package com.wamogu.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author Armin
 * @date 24-06-12 11:36
 */
public interface IEnumerator extends IEnum<String> {
    String getDescription();
}
