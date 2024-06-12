package com.wamogu.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Armin
 * @date 24-06-12 11:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserIdentifyPhone extends BaseUserIdentify {
    private String phone;
    private String verified;
}
