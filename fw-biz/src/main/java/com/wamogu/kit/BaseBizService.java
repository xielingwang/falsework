package com.wamogu.kit;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wamogu.exception.ErrorKit;
import com.wamogu.querykit.FwQueryBase;
import com.wamogu.querykit.FwQueryKit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.C;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author Armin
 * @datDO 24-05-29 14:52
 */
@AllArgsConstructor
public abstract class BaseBizService<DO, DTO, VO, PK extends Serializable> {
    @Getter
    protected IService<DO> baseRepository;
    @Getter
    protected BaseCastor<DO, DTO, VO> baseCastor;


    private Class<DO> clazzDO;
    private Class<DTO> clazzDTO;
    private Class<VO> clazzVO;

    public BaseBizService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazzDO = (Class<DO>) pt.getActualTypeArguments()[0];
        this.clazzDTO = (Class<DTO>) pt.getActualTypeArguments()[1];
        this.clazzVO = (Class<VO>) pt.getActualTypeArguments()[2];
    }

    public VO getOne(PK id) {
        DO itemDo = getBaseRepository().getById(id);
        if (itemDo == null) {
            throw new ErrorKit.IllegalParam("找不到对象");
        }
        return getBaseCastor().do2vo(itemDo);
    }

    public VO updateOne(PK id, DTO itemDto) {
        DO itemDoExisted = getBaseRepository().getById(id);
        if (itemDoExisted == null) {
            throw new ErrorKit.IllegalParam("找不到对象");
        }
        DO itemDo = getBaseCastor().dto2do(itemDto);
        setPriKeyValue(itemDo, id);
        getBaseRepository().updateById(itemDo);
        return getBaseCastor().do2vo(itemDo);
    }
    public List<VO> updateList(List<DTO> itemDtos) {
        List<DO> itemDos = itemDtos.stream().map(x -> getBaseCastor().dto2do(x)).toList();
        getBaseRepository().updateBatchById(itemDos);
        return getBaseCastor().dos2vos(itemDos);
    }

    public void deleteOne(PK id) {
        if (!getBaseRepository().removeById(id)) {
            throw new ErrorKit.IllegalParam("找不到对象");
        }
    }

    private void setPriKeyValue(DO itemDo, PK id) {
        Optional<Field> pkField = Arrays.stream(ReflectUtil.getFields(itemDo.getClass()))
                .filter(x -> x.getAnnotation(TableId.class) != null)
                .findFirst();
        if (pkField.isEmpty()) {
            throw new ErrorKit.Fatal(String.format("系统异常：类 %s 未定义 @TableId", itemDo.getClass().getSimpleName()) );
        }
        ReflectUtil.setFieldValue(itemDo, pkField.get(), id);;
    }

    private void getPriKeyValue(DO itemDo) {
        Optional<Field> pkField = Arrays.stream(ReflectUtil.getFields(itemDo.getClass()))
                .filter(x -> x.getAnnotation(TableId.class) != null)
                .findFirst();
        if (pkField.isEmpty()) {
            throw new ErrorKit.Fatal(String.format("系统异常：类 %s 未定义 @TableId", itemDo.getClass().getSimpleName()) );
        }
        ReflectUtil.getFieldValue(itemDo, pkField.get());
    }

    public List<VO> getAll(FwQueryBase baseQuery) {
        LambdaQueryChainWrapper<DO> qw = baseQuery != null
                ? FwQueryKit.buildSearch(getBaseRepository(), baseQuery)
                : getBaseRepository().lambdaQuery() ;
        return getBaseCastor().dos2vos(qw.list());
    }
    public List<VO> getAll() {
        return getAll(null);
    }

    public IPage<VO> getPage(FwQueryBase baseQuery) {
        Page<DO> pageDo = baseQuery != null
                ? FwQueryKit.page(getBaseRepository(), baseQuery)
                : getBaseRepository().lambdaQuery().page(FwQueryBase.getDefaultPage(clazzDO));
        return getBaseCastor().pageDo2vo(pageDo);
    }

    public VO createOne(DTO itemDto) {
        DO itemDo = getBaseCastor().dto2do(itemDto);
        getBaseRepository().save(itemDo);
        return getBaseCastor().do2vo(itemDo);
    }

    public void deleteBatchByIds(List<PK> ids) {
        getBaseRepository().removeByIds(ids);
    }
}
