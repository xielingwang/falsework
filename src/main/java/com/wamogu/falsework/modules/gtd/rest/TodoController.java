package com.wamogu.falsework.modules.gtd.rest;

import com.wamogu.falsework.modules.gtd.dao.DbTodoItem;
import com.wamogu.falsework.modules.gtd.repository.DbTodoItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Armin
 * @date 24-05-22 00:45
 */
@Tag(name = "GTD")
@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final DbTodoItemRepository dbTodoItemRepository;

    @GetMapping("/{id}")
    @Operation(summary = "获取待办项")
    public DbTodoItem getTodoItem(Long id) {
        return dbTodoItemRepository.getById(id);
    }

    @GetMapping("/")
    @Operation(summary = "获取待办项列表")
    public List<DbTodoItem> getTodoItemList() {
        return dbTodoItemRepository.list();
    }

    @PostMapping("/")
    @Operation(summary = "创建待办项")
    @ApiResponse(responseCode = "200", description = "创建成功")
    public DbTodoItem createTodoItem(@RequestBody DbTodoItem dbTodoItem) {
        dbTodoItemRepository.save(dbTodoItem);
        return dbTodoItem;
    }
}
