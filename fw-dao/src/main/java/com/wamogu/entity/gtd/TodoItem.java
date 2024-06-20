package com.wamogu.entity.gtd;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import com.wamogu.entity.MutableEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-05-22 00:42
 */
@Data @Builder @EqualsAndHashCode(callSuper=false)
@AutoDefine @AutoRepository
@Table(comment = "GTD-待办项")
@TableName(value = "gtd_todo_items", autoResultMap = true)
public class TodoItem extends MutableEntity {

    @Column(comment = "用户id", notNull = true)
    private Long uid;

    @NotNull(message = "内容不为空")
    @Column(value = "内容", notNull = true)
    private String content;

    @ColumnComment("备注")
    private String remark;

    @Column(value = "是否完成", notNull = true, defaultValue = "0")
    private Boolean isCompleted;

    @ColumnComment("完成时间")
    private LocalDateTime completedTime;
}
