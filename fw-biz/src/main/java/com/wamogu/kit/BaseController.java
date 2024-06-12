package com.wamogu.kit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wamogu.querykit.FwQueryBase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
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
@Getter
public abstract class BaseController<DO, DTO, VO, QO extends FwQueryBase, PK extends Serializable> {
    protected BaseBizService<DO, DTO, VO, PK> bizService;

    @GetMapping("/crud/get/{id}")
    @Operation(summary = "获取Item")
    @ApiResponse(responseCode = "200", description = "")
    public VO getOne(@PathVariable PK id) {
        return getBizService().getOne(id);
    }

    @GetMapping("/crud/list")
    @Operation(summary = "获取Item列表")
    public List<VO> getAll(@ParameterObject QO query) {
        return getBizService().getAll(query);
    }

    @GetMapping("/crud/page")
    @Operation(summary = "获取Item翻页列表")
    public IPage<VO> getPage(@ParameterObject  QO query) {
        return getBizService().getPage(query);
    }

    @PostMapping("/crud/create")
    @Operation(summary = "创建Item")
    @ApiResponse(responseCode = "200", description = "创建成功")
    public VO createItem(@RequestBody DTO dto) {
        return getBizService().createOne(dto);
    }

    @PostMapping("/crud/update/{id}")
    @Operation(summary = "更新Item")
    @ApiResponse(responseCode = "200", description = "更新成功")
    public VO updateItem(@PathVariable PK id, @RequestBody DTO dto) {
        return getBizService().updateOne(id, dto);
    }

    @PostMapping("/crud/delete/{id}")
    @Operation(summary = "删除Item")
    @ApiResponse(responseCode = "200", description = "删除成功")
    public void deleteItem(@PathVariable PK id) {
        getBizService().deleteOne(id);
    }

    @PostMapping("/crud/batch/create")
    @Operation(summary = "批量创建Item")
    @ApiResponse(responseCode = "200", description = "创建成功")
    public List<VO> createItems(@RequestBody List<DTO> dtos) {
        return dtos.stream().map(x -> getBizService().createOne(x)).toList();
    }

    @GetMapping("/crud/batch/get/{ids}")
    @Operation(summary = "批量获取Item")
    @ApiResponse(responseCode = "200", description = "")
    public List<VO> getOnes(@PathVariable List<PK> ids) {
        return ids.stream().map(x -> getBizService().getOne(x)).toList();
    }

    @PostMapping("/crud/batch/update")
    @Operation(summary = "批量更新Item")
    @ApiResponse(responseCode = "200", description = "更新成功")
    public List<VO> updateItem(@RequestBody List<DTO> dtos) {
        return getBizService().updateList(dtos);
    }

    @PostMapping("/crud/batch/delete/{id}")
    @Operation(summary = "批量删除Item")
    @ApiResponse(responseCode = "200", description = "删除成功")
    public void deleteItem(@PathVariable List<PK> ids) {
        getBizService().deleteBatchByIds(ids);
    }
}
