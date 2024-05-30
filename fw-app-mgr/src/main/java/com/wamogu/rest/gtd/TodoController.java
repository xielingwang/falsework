package com.wamogu.rest.gtd;

import com.wamogu.biz.gtd.pojo.TodoItemDto;
import com.wamogu.biz.gtd.pojo.TodoItemQuery;
import com.wamogu.biz.gtd.pojo.TodoItemVo;
import com.wamogu.biz.gtd.service.TodoBizService;
import com.wamogu.entity.gtd.TodoItem;
import com.wamogu.kit.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Armin
 * @date 24-05-22 00:45
 */
@Tag(name = "GTD")
@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public final class TodoController extends BaseController<TodoItem, TodoItemDto, TodoItemVo, TodoItemQuery, Integer> {
    private final TodoBizService todoBizService;

    @Override
    protected TodoBizService getBizService() {
        return todoBizService;
    }
}
