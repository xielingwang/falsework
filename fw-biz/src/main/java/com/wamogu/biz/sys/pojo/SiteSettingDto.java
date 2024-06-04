package com.wamogu.biz.sys.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author Armin
 * @date 24-06-04 01:33
 */
@Data
public class SiteSettingDto implements ISetting {
    @Schema(title = "网站名称", description = "网站名称")
    private String siteTitle;
    @Schema(title = "网站描述", description = "网站描述")
    private String siteDesciption;
    @Schema(title = "网站地址", description = "网站地址")
    private String siteUrl;
    @Schema(title = "网站作者", description = "网站作者")
    private String siteName;
}
