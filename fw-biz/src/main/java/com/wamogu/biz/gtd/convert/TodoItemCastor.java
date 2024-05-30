package com.wamogu.biz.gtd.convert;

import com.wamogu.biz.gtd.pojo.TodoItemVo;
import com.wamogu.biz.gtd.pojo.TodoItemDto;
import com.wamogu.entity.gtd.TodoItem;
import com.wamogu.kit.BaseCastor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @Author Armin
 * @date 24-05-29 11:09
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoItemCastor extends BaseCastor<TodoItem, TodoItemDto, TodoItemVo> {
    TodoItemCastor INST = Mappers.getMapper(TodoItemCastor.class);
}
