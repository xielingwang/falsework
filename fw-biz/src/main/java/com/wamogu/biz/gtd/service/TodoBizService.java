package com.wamogu.biz.gtd.service;

import com.wamogu.biz.gtd.convert.TodoItemCastor;
import com.wamogu.biz.gtd.pojo.TodoItemDto;
import com.wamogu.biz.gtd.pojo.TodoItemVo;
import com.wamogu.entity.dao.repository.TodoItemRepository;
import com.wamogu.entity.gtd.TodoItem;
import com.wamogu.kit.BaseBizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author Armin
 * @date 24-05-29 17:37
 */
@Service
@RequiredArgsConstructor
public class TodoBizService extends BaseBizService<TodoItem, TodoItemDto, TodoItemVo, Integer> {
    private final TodoItemRepository todoItemRepository;
    private final TodoItemCastor todoItemCastor;

    @Override
    protected TodoItemRepository getBaseService() {
        return todoItemRepository;
    }

    @Override
    protected TodoItemCastor getBaseCastor() {
        return todoItemCastor;
    }
}
