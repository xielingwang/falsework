package com.wamogu.falsework.modules.todo.rest;

import com.wamogu.falsework.modules.todo.dao.DbTodoItem;
import com.wamogu.falsework.modules.todo.dao.DbTodoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Armin
 * @date 24-05-22 00:45
 */
@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final DbTodoItemRepository dbTodoItemRepository;

    @GetMapping("/{id}")
    public DbTodoItem getTodoItem(Long id) {
        return dbTodoItemRepository.getById(id);
    }

    @GetMapping("/")
    public List<DbTodoItem> getTodoItemList() {
        return dbTodoItemRepository.list();
    }

    @PostMapping("/")
    public DbTodoItem createTodoItem(@RequestBody DbTodoItem dbTodoItem) {
        dbTodoItemRepository.save(dbTodoItem);
        return dbTodoItem;
    }
}
