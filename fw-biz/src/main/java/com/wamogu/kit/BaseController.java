package com.wamogu.kit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wamogu.querykit.FwQueryBase;
import com.wamogu.security.service.FwSecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

    @Resource
    protected FwSecurityService fss;

    @GetMapping("/crud/get/{id}")
    @Operation(summary = "获取Item")
    @ApiResponse(responseCode = "200", description = "")
    public VO getOne(@Valid @PathVariable PK id) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "get");
        return getBizService().getOne(id);
    }

    @GetMapping("/crud/list")
    @Operation(summary = "获取Item列表")
    public List<VO> getAll(@Valid @ParameterObject QO query) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "get");
        return getBizService().getAll(query);
    }

    @GetMapping("/crud/page")
    @Operation(summary = "获取Item翻页列表")
    public IPage<VO> getPage(@Valid @ParameterObject  QO query) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "get");
        return getBizService().getPage(query);
    }

    @PostMapping("/crud/create")
    @Operation(summary = "创建Item")
    @ApiResponse(responseCode = "200", description = "创建成功")
    public VO createItem(@Valid @RequestBody DTO dto) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "create");
        return getBizService().createOne(dto);
    }

    @PostMapping("/crud/update/{id}")
    @Operation(summary = "更新Item")
    @ApiResponse(responseCode = "200", description = "更新成功")
    public VO updateItem(@Valid @PathVariable PK id, @Valid @RequestBody DTO dto) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "update");
        return getBizService().updateOne(id, dto);
    }

    @PostMapping("/crud/delete/{id}")
    @Operation(summary = "删除Item")
    @ApiResponse(responseCode = "200", description = "删除成功")
    public void deleteItem(@PathVariable PK id) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "delete");
        getBizService().deleteOne(id);
    }

    @PostMapping("/crud/batch/create")
    @Operation(summary = "批量创建Item")
    @ApiResponse(responseCode = "200", description = "创建成功")
    public List<VO> createItems(@Valid @RequestBody List<DTO> dtos) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "create");
        return dtos.stream().map(x -> getBizService().createOne(x)).toList();
    }

    @GetMapping("/crud/batch/get/{ids}")
    @Operation(summary = "批量获取Item")
    @ApiResponse(responseCode = "200", description = "")
    public List<VO> getOnes(@Valid @PathVariable List<PK> ids) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "get");
        return ids.stream().map(x -> getBizService().getOne(x)).toList();
    }

    @PostMapping("/crud/batch/update")
    @Operation(summary = "批量更新Item")
    @ApiResponse(responseCode = "200", description = "更新成功")
    public List<VO> updateItem(@Valid @RequestBody List<DTO> dtos) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "update");
        return getBizService().updateList(dtos);
    }

    @PostMapping("/crud/batch/delete/{id}")
    @Operation(summary = "批量删除Item")
    @ApiResponse(responseCode = "200", description = "删除成功")
    public void deleteItem(@Valid @PathVariable List<PK> ids) {
        fss.checkHasPrivilege(getBizService().getClazzDO(), "delete");
        getBizService().deleteBatchByIds(ids);
    }
}
