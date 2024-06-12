package com.wamogu.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Armin
 * @date 24-06-12 11:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserIdentifyEmail extends BaseUserIdentify {
    private String email;
    private String verified;
}
