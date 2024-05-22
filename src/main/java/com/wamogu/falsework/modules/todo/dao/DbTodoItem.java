package com.wamogu.falsework.modules.todo.dao;

import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoMapper;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Armin
 * @date 24-05-22 00:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("todo_item")
@AutoDefine
@AutoRepository
@ApiResponse
public class DbTodoItem {
    private Integer id;
    private Integer uid;
    private String content;
    private Boolean isCommpleted;
}
