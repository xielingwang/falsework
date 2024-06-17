package com.wamogu.security.model;

import lombok.Data;

/**
 * @Author Armin
 * @date 24-06-14 17:21
 */
@Data
public class FwPwdLoginQuery {
    private String username;
    private String password;
}
