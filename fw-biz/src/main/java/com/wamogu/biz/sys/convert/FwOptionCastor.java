package com.wamogu.biz.sys.convert;

import com.wamogu.biz.sys.pojo.FwOptionDto;
import com.wamogu.biz.sys.pojo.FwOptionVo;
import com.wamogu.entity.sys.Option;
import com.wamogu.kit.BaseCastor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-12
 */
@Mapper(
        componentModel = "spring",
        uses = {},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FwOptionCastor extends BaseCastor<Option, FwOptionDto, FwOptionVo> {
}