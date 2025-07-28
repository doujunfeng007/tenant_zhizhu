package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.Valid;
import java.util.List;

/**
 * 行情品种对象 mkt_quotes_type
 *
 * @author bpmx
 * @date 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("mkt_quotes_type")
@ApiModel("行情品种信息")
@Alias("cmsQuotesType")
public class QuotesType extends BaseEntity {
private static final long serialVersionUID=1L;


    /** 品种标识 */
    @ApiModelProperty(value = "品种标识",  example = "品种标识")
    @ExcelProperty("品种标识")
    private String varietiesCode;

    /** 品种名称 */
    @ApiModelProperty(value = "品种名称",  example = "品种名称")
    @ExcelProperty("品种名称")
    private String varietiesName;

    /** 品种繁体名称 */
    @ApiModelProperty(value = "品种繁体名称",  example = "品种繁体名称")
    @ExcelProperty("品种繁体名称")
    private String varietiesTraditionalName;

    /** 品种英语名称 */
    @ApiModelProperty(value = "品种英语名称",  example = "品种英语名称")
    @ExcelProperty("品种英语名称")
    private String varietiesEnglishName;

    /** 0A股 1港股 2美股 3其他 */
    @ApiModelProperty(value = "品种服务市场(0A股 1港股 2美股 3其他) 字典 mkt_varieties_market",  example = "品种服务市场字典 mkt_varieties_market")
    @ExcelProperty("0A股 1港股 2美股 3其他")
    private Integer varietiesMarket;

    /** 0中国大陆 1中国香港 2全球 3非中国大陆 4其他 */
    @ApiModelProperty(value = "品种使用地区(0中国大陆 1中国香港 2全球 3非中国大陆 4其他) 字典 mkt_varieties_area",  example = "使用地区字典 mkt_varieties_area")
    @ExcelProperty("0中国大陆 1中国香港 2全球 3非中国大陆 4其他")
    private Integer varietiesArea;

    /** 0全平台 1网站 2移动APP 3PC终端 4非展示 5其他 */
    @ApiModelProperty(value = "品种设备类型(0全平台 1网站 2移动APP 3PC终端 4非展示 5其他) 字典 mkt_varieties_device",  example = "设备类型字典-mkt_varieties_device")
    @ExcelProperty("0全平台 1网站 2移动APP 3PC终端 4非展示 5其他")
    private Integer varietiesDevice;

    /** 0串流报价 1点击报价 2时长收费 3包干免费 4免费服务 5非展示 */
    @ApiModelProperty(value = "品种服务类型(0串流报价 1点击报价 2时长收费 3包干免费 4免费服务 5非展示) 字典-mkt_varieties_service",  example = "品种服务类型 字典-mkt_varieties_service")
    @ExcelProperty("0串流报价 1点击报价 2时长收费 3包干免费 4免费服务 5非展示")
    private Integer varietiesServiceType;

    /** 优先级 1~9 ，数字越大代表越优先 */
    @ApiModelProperty(value = "优先级 1~9 ，数字越大代表越优先",  example = "1")
    @ExcelProperty("优先级 1~9 ，数字越大代表越优先")
    private Integer varietiesPriority;

    /** 品种类型 0股票、1指数、2期货、3期权,字典mkt_varieties_type */
    @ApiModelProperty(value = "品种类型 0股票、1指数、2期货、3期权,字典mkt_varieties_type",  example = "品种类型,字典mkt_varieties_type")
    @ExcelProperty("品种类型 0股票、1指数、2期货、3期权,字典mkt_varieties_type")
    private Integer varietiesType;

    /** 更新类型 0静态历史、1延迟行情、2手动刷新、3实时行情,字典mkt_update_type */
    @ApiModelProperty(value = "更新类型 0静态历史、1延迟行情、2手动刷新、3实时行情,字典mkt_update_type",  example = "更新类型,字典mkt_update_type")
    @ExcelProperty("更新类型 0静态历史、1延迟行情、2手动刷新、3实时行情,字典mkt_update_type")
    private Integer updateType;

    /** 备注 */
    @ApiModelProperty(value = "备注",  example = "备注")
    @ExcelProperty("备注")
    private String varietiesRemark;

    /** 是否默认行情品种:1.是,0否 */
    @ApiModelProperty(value = "是否默认行情品种(兜底):1.是,0否",  example = "是否默认行情品种(兜底):1.是,0否")
    @ExcelProperty("是否默认行情品种:1.是,0否")
    private Integer defaultVarieties;

    /** icon地址*/
    @ApiModelProperty(value = "icon地址",  example = "icon地址")
    private String iconAddress;

    /** 1 生效 2 失效  3 准备中 */
//    @ApiModelProperty(value = "品种状态(0筹备中 1生效 2失效) 字典-mkt_varieties_status",  example = "品种状态字典-mkt_varieties_status")
//    @ExcelProperty("品种状态")
//    private String status;
//    private String mktVarietiesStatus;

    /** 子市场 all-全部 sz-深交所 hk-上交所 */
    @ExcelProperty("子市场")
    @ApiModelProperty(value = "子市场 all-全部 sz-深交所 sh-上交所 字典-mkt_A_market",  example = "子市场-mkt_A_market")
    private String varietiesMarketSegments;

    @ApiModelProperty(value = "供应商列表",  example = "供应商列表")
    @Valid
    @TableField(exist = false)
    private List<ServiceProviderInfo> supplierList;

    @TableField(exist = false)
    private List<Long> varietiesIdList;

    /** 0中国大陆 1中国香港 2全球 3非中国大陆 4其他 */
    @TableField(exist = false)
    private List<String> varietiesAreaList;

	/** 级别 1-LV0 2-LV1 3-LV2 */
	@ApiModelProperty(value = "级别 1-LV0 2-LV1 3-LV2")
	private Integer level;

	/** 点击次数 */
	@ApiModelProperty(value = "点击次数")
	private Integer num;
}
