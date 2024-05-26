package com.wamogu.falsework.modules.gtd.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.base.entity.BaseEntity;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-05-22 00:42
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AutoDefine
@AutoRepository
@Table("gtd_todo_item")
@Schema(title = "待办项", description = "待办项")
public class DbTodoItem extends BaseEntity<Long, LocalDateTime> {
    @TableId(type = IdType.AUTO)
    @Schema(title = "id", description = "id")
    private Integer id;
    @Schema(title = "用户id", description = "用户id")
    private Long uid;
    @Schema(title = "内容", description = "内容")
    private String content;
    @Schema(title = "备注", description = "备注")
    private String remark;
    @Schema(title = "是否完成", description = "是否完成")
    private Boolean isCommpleted;
    @Schema(title = "完成时间", description = "完成时间")
    private LocalDateTime completedTime;
}
