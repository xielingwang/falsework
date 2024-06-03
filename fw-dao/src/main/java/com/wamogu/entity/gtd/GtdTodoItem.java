package com.wamogu.entity.gtd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.ColumnNotNull;
import com.tangzc.autotable.annotation.PrimaryKey;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.base.entity.BaseEntity;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-05-22 00:42
 */
@Data @Builder @EqualsAndHashCode(callSuper=false)
@AutoDefine @AutoRepository
@Table(value = "gtd_todo_item", comment = "GTD-待办项")
public class GtdTodoItem extends BaseEntity<Long, LocalDateTime> {
    @TableId(type = IdType.AUTO)
    @PrimaryKey(value = true)
    @ColumnComment("id")
    private Integer id;

    @ColumnComment("用户id")
    private Long uid;

    @NotNull(message = "内容不为空")
    @ColumnComment("内容")
    private String content;

    @ColumnComment("备注")
    private String remark;

    @ColumnComment("是否完成")
    private Boolean isCommpleted;

    @ColumnComment("完成时间")
    private LocalDateTime completedTime;
}
