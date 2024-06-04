package com.wamogu.biz.sys.pojo;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.wamogu.entity.sys.SysConfig;
import com.wamogu.kit.FwJsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Armin
 * @date 24-06-04 09:58
 */
public interface ISetting {
    default List<String> fieldNames() {
        return Arrays.stream(ReflectUtil.getFields(SiteSettingDto.class))
                .map(Field::getName).toList();
    }
    default List<SysConfig> toSysConfigs() {
        return Arrays.stream(ReflectUtil.getFields(this.getClass()))
                .map(f -> SysConfig.builder().key(f.getName())
                        .value(FwJsonUtils.bean2json(ReflectUtil.getFieldValue(this, f))).build()
                ).toList();
    }
    default void fromSysConfigs(List<SysConfig> list) {
        Map<String, String> listMap = list.stream().collect(Collectors.toMap(SysConfig::getKey, SysConfig::getValue));
        Arrays.stream(ReflectUtil.getFields(this.getClass()))
                .forEachOrdered(f -> {
                    ReflectUtil.setFieldValue(this, f, FwJsonUtils.json2Bean(listMap.get(f.getName()), f.getType()));
                });
    }

    default SettingVo toVo() {
        SettingVo vo = new SettingVo();
        Arrays.stream(ReflectUtil.getFields(this.getClass()))
                .forEachOrdered(f -> {
                    vo.put(f.getName(), ReflectUtil.getFieldValue(this, f));
                    Schema schema = f.getAnnotation(Schema.class);
                    vo.put(f.getName()+"_label", schema == null ? f.getName() : StrUtil.blankToDefault(schema.title(), f.getName()));
                });
        return vo;
    }
}
