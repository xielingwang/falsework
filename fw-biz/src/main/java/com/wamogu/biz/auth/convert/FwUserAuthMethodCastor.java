package com.wamogu.biz.auth.convert;

import com.wamogu.biz.auth.pojo.FwUserAuthMethodDto;
import com.wamogu.biz.auth.pojo.FwUserAuthMethodVo;
import com.wamogu.entity.auth.UserAuthMethod;
import com.wamogu.kit.BaseCastor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-13
 */
@Mapper(
        componentModel = "spring",
        uses = {},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FwUserAuthMethodCastor extends BaseCastor<UserAuthMethod, FwUserAuthMethodDto, FwUserAuthMethodVo> {
}
