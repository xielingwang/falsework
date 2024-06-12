package com.wamogu.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.base.entity.BaseEntity;
import com.wamogu.enums.UserGenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-06-12 00:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProps {
    @Schema(title = "民族", description = "民族")
    private String nation;
    @Schema(title = "民族", description = "民族")
    @JsonFormat
    private UserGenderType gender;
}
