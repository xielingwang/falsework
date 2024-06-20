package com.wamogu.security.service;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.tangzc.mpe.autotable.annotation.Table;
import com.wamogu.biz.auth.pojo.FwPrivilegeVo;
import com.wamogu.exception.ErrorKit;
import com.wamogu.security.FwAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @Author Armin
 * @date 24-06-20 02:28
 */
@Slf4j
@Service("fas")
public class FwSecurityService {
    public String package2mod(Package pack) {
        String[] arr = pack.getName().split("\\.");
        return StrUtil.toUnderlineCase(arr[arr.length-1]);
    }
    public String objectname(String str) {
        return StrUtil.toUnderlineCase(str);
    }

    public void checkHasPrivilege(Class<?> clazz, String op) {
        checkHasPrivilege(package2mod(clazz.getPackage())
                , objectname(clazz.getSimpleName())
                , objectname(op));
    }
    public void checkHasPrivilege(String mod, String objname, String op) {
        checkHasPrivilege(String.format("%s:%s:%s", mod, objname, op));
    }

    public void checkHasPrivilege(String privKey) {
        if (!FwAuthUtils.hasPrivilege(privKey)) {
            throw new ErrorKit.Forbidden(String.format("权限不足，需要 %s 权限", privKey));
        }
    }

    public void checkHasRole(String role) {
        if (!FwAuthUtils.hasRole(role)) {
            throw new ErrorKit.Forbidden(String.format("权限不足，需要 %s 角色", role));
        }
    }

    public List<FwPrivilegeVo> annoPrivileges() {
        return List.of();
    }
    public List<FwPrivilegeVo> crudPrivileges() {
        return ClassUtil.scanPackage("com.wamogu.entity")
                .stream().filter(x -> x.getAnnotation(Table.class) != null)
                .map(x -> List.of(
                        String.format("%s:%s", package2mod(x.getPackage()), objectname(x.getSimpleName())),
                        String.format("%s", StrUtil.blankToDefault(x.getAnnotation(Table.class).comment(), x.getSimpleName()))
                ))
                .flatMap(x -> Stream.of("get|读取", "create|创建", "update|更新", "delete|删除")
                        .map(y -> {
                            FwPrivilegeVo r = new FwPrivilegeVo();
                            String[] yparts = y.split("[|]");
                            r.setPrivilegeKey(String.format("%s:%s", x.get(0), yparts[0]));
                            r.setPrivilegeRemark(String.format("%s:%s", x.get(1), yparts[1]));
                            return r;
                        })).toList();
    }
}
