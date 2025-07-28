package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 广告信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@TableName("oms_ad_info")
@ApiModel(value = "AdInfo对象", description = "广告信息表")
@EqualsAndHashCode(callSuper = true)
public class AdInfoEntity extends TenantEntity {
    /**
     * 广告位
     */
    @ApiModelProperty(value = "广告位")
    private Integer positionCode;
    /**
     * 广告位二级分类
     */
    @ApiModelProperty(value = "广告位二级分类")
    private Integer positionSecondType;
    /**
     * 语言类型  0: 简体中文, 2: 英文, 3: 繁体中文, 4: 越南语
     */
    @ApiModelProperty(value = "语言类型  0: 简体中文, 2: 英文, 3: 繁体中文, 4: 越南语")
    private Integer languageType;
    /**
     * 活动标志码
     */
    @ApiModelProperty(value = "活动标志码")
    private String adCode;
    /**
     * 广告用户群分组
     */
    @ApiModelProperty(value = "广告用户群分组")
    private Integer groupId;
    /**
     * 广告展示的判断条件
     */
    @ApiModelProperty(value = "广告展示的判断条件")
    private Integer adFlag;
    /**
     * 广告图片地址
     */
    @ApiModelProperty(value = "广告图片地址")
    private String img;
    /**
     * 广告链接地址
     */
    @ApiModelProperty(value = "广告链接地址")
    private String url;
    /**
     * 广告的调转类型(V-观点详情,P-组合详情,A-广告详情页)
     */
    @ApiModelProperty(value = "广告的调转类型(V-观点详情,P-组合详情,A-广告详情页)")
    private String linkType;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String adDesc;
    /**
     * 是否跳转到IM, 0:否 1:是
     */
    @ApiModelProperty(value = "是否跳转到IM, 0:否 1:是")
    private Boolean toIm;
    /**
     * 弹窗按钮
     */
    @ApiModelProperty(value = "弹窗按钮")
    private String popupButton;
    /**
     * 弹窗跳转连接
     */
    @ApiModelProperty(value = "弹窗跳转连接")
    private String popupUrl;
    /**
     * 广告开始时间
     */
    @ApiModelProperty(value = "广告开始时间")
    private Date startTime;
    /**
     * 广告结束时间
     */
    @ApiModelProperty(value = "广告结束时间")
    private Date endTime;
    /**
     * 数字越小优先级越高，同个用户群有多个广告时展示优先级最高的
     */
    @ApiModelProperty(value = "数字越小优先级越高，同个用户群有多个广告时展示优先级最高的")
    private Integer priority;
    /**
     * 活动参与人数
     */
    @ApiModelProperty(value = "活动参与人数")
    private Integer activityNum;
    /**
     * 活动专栏banner图
     */
    @ApiModelProperty(value = "活动专栏banner图")
    private String bananerImg;
    /**
     * 可见的渠道黑名单
     */
    @ApiModelProperty(value = "可见的渠道黑名单")
    private String maskChannel;
    /**
     * 是否需要底部导航栏
     */
    @ApiModelProperty(value = "是否需要底部导航栏")
    private Boolean bottomTab;
    /**
     * 是否需要头部
     */
    @ApiModelProperty(value = "是否需要头部")
    private Boolean isNeedHeader;
    /**
     * 手机号码归属 0:全量,1:大陆用户,2:海外用户(含港澳台)
     */
    @ApiModelProperty(value = "手机号码归属 0:全量,1:大陆用户,2:海外用户(含港澳台)")
    private Integer phoneArea;
    /**
     * IP地址归属 0:全量,1:大陆用户,2:海外用户(含港澳台)
     */
    @ApiModelProperty(value = "IP地址归属 0:全量,1:大陆用户,2:海外用户(含港澳台)")
    private Integer ipArea;
    /**
     * 应用市场 0:全量,1:IOS,2:安卓
     */
    @ApiModelProperty(value = "应用市场 0:全量,1:IOS,2:安卓")
    private Integer applicationMarket;
    /**
     * 登录后是否可见0:否 1:是
     */
    @ApiModelProperty(value = "登录后是否可见0:否 1:是")
    private Boolean isLoginShow;
    /**
     * 认购开始时间
     */
    @ApiModelProperty(value = "认购开始时间")
    private Date substartTime;
    /**
     * 认购结束时间
     */
    @ApiModelProperty(value = "认购结束时间")
    private Date subendTime;
    /**
     * 是否刷新
     */
    @ApiModelProperty(value = "是否刷新")
    private Boolean isRefresh;
    /**
     * 触达频率：0=每次，1=每天一次，2=每周一次，3=仅一次
     */
    @ApiModelProperty(value = "触达频率：0=每次，1=每天一次，2=每周一次，3=仅一次")
    private Integer showFrequency;
    /**
     * 可见用户类型：0=所有用户，1=特定用户，2=渠道白名单，3=渠道黑名单
     */
    @ApiModelProperty(value = "可见用户类型：0=所有用户，1=特定用户，2=渠道白名单，3=渠道黑名单")
    private Integer showType;
    /**
     * 可见的特定用户
     */
    @ApiModelProperty(value = "可见的特定用户")
    private String showUser;
    /**
     * 可见的渠道白名单
     */
    @ApiModelProperty(value = "可见的渠道白名单")
    private String whiteChannel;
    /**
     * 是否在活动专区可见：0=否，1=是
     */
    @ApiModelProperty(value = "是否在活动专区可见：0=否，1=是")
    private Integer isShowInActivity;

}
