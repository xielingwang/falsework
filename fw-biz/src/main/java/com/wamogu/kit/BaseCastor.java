package com.wamogu.kit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author Armin
 * @date 24-05-29 11:51
 */
public interface BaseCastor<DO, DTO, VO> {
    DTO do2dto(DO obj);
    DO dto2do(DTO obj);
    VO do2vo(DO obj);
    List<DTO> dos2dtos(List<DO> list);
    List<DO> dtos2dos(List<DTO> list);
    List<VO> dos2vos(List<DO> list);
    VO dto2vo(DTO obj);
    List<VO> dtos2vos(List<DTO> list);
    Page<VO> pageDo2vo(IPage<DO> page);
    Page<VO> pageDto2vo(IPage<DTO> page);
}
