package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelProperty;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ip的真实地址信息对象 mkt_ip_address
 *
 * @author bpmx
 * @date 2022-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cust_ip_address")
public class IpAddress extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 请求ip
     */
    @ExcelProperty("请求ip")
    private String ip;

    /**
     * ip地址
     */
    @ExcelProperty("ip地址")
    private String realIp;

    /**
     * 国家中文名称
     */
    @ExcelProperty("国家中文名称")
    private String countryCnName;

    /**
     * 国家代码
     */
    @ExcelProperty("国家代码 ")
    private String countryCode;

    /**
     * 地区中文名称
     */
    @ExcelProperty("地区中文名称")
    private String regionCnName;

    /**
     * 地区code
     * 中国大陆访问全球、中国大陆，中国香港访问全球、中国香港、非大陆，中国澳门台湾访问全球、非大陆，非中国访问全球、非大陆
     */
    @ExcelProperty("地区code")
    private String regionCode;

    /**
     * 地区id
     */
    @ExcelProperty("地区id")
    private String regionId;


}
