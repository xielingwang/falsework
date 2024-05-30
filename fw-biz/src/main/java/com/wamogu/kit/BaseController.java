package com.wamogu.kit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wamogu.querykit.FwQueryBase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Armin
 * @date 24-05-29 14:52
 */
@Slf4j
public abstract class BaseController<DO, DTO, VO, QO extends FwQueryBase, PK extends Serializable> {
    protected abstract <BS extends BaseBizService<DO, DTO, VO, PK>> BS getBizService();

    @GetMapping("/{id}")
    @Operation(summary = "获取item")
    @ApiResponse(responseCode = "200", description = "")
    public VO getOne(@PathVariable PK id) {
        return getBizService().getOne(id);
    }

    @GetMapping("/all")
    @Operation(summary = "获取item列表")
    public List<VO> getAll(@ParameterObject QO query) {
        return getBizService().getAll(query);
    }

    @GetMapping("/page")
    @Operation(summary = "获取item翻页列表")
    public IPage<VO> getPage(@ParameterObject  QO query) {
        return getBizService().getPage(query);
    }

    @PostMapping("")
    @Operation(summary = "创建item")
    @ApiResponse(responseCode = "200", description = "创建成功")
    public VO createItem(@RequestBody DTO dto) {
        return getBizService().createOne(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = " 更新item")
    @ApiResponse(responseCode = "200", description = "更新成功")
    public VO createItem(@PathVariable PK id, @RequestBody DTO dto) {
        return getBizService().updateOne(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除item")
    @ApiResponse(responseCode = "200", description = "删除成功")
    public void deleteItem(@PathVariable PK id) {
        getBizService().deleteOne(id);
    }
}
