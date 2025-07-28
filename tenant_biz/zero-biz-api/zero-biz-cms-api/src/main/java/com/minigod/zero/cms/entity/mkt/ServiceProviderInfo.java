package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 行情供应商对象 mkt_service_provider_info
 *
 * @author bpmx
 * @date 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("mkt_service_provider_info")
@ApiModel("供应商信息")
@Alias("cmsServiceProviderInfo")
public class ServiceProviderInfo extends BaseEntity {
private static final long serialVersionUID=1L;

//	private Long id;
//
//    /** $column.columnComment */
//    @TableId(value = "service_id", type = IdType.ASSIGN_ID)
//    @ApiModelProperty(value = "供应商id",  example = "1")
//    private Long serviceId;

    /** 供应商名称 */
    @ApiModelProperty(value = "供应商名称",  example = "供应商名称")
    @ExcelProperty("供应商名称")
    private String serviceName;

    /** 备注 */
    @ApiModelProperty(value = "备注",  example = "备注")
    @ExcelProperty("备注")
    private String serviceRemark;

    /** 供应商联系人 */
    @ApiModelProperty(value = "供应商联系人",  example = "供应商联系人")
    @ExcelProperty("供应商联系人")
    private String serviceContactName;

    /** 供应商联系电话 */
    @ApiModelProperty(value = "供应商联系电话",  example = "供应商联系电话")
    @ExcelProperty("供应商联系电话")
    private String serviceContactPhone;

    /** 0筹备中 1生效 2失效 */
    @ApiModelProperty(value = "供应商状态(0筹备中 1生效 2失效) 字典-mkt_supplier_status",  example = "供应商状态 字典-mkt_supplier_status")
    @ExcelProperty("0筹备中 1生效 2失效")
    private Integer status;


}
