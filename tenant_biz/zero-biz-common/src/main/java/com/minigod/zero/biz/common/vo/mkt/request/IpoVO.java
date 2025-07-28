package com.minigod.zero.biz.common.vo.mkt.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/4/4 11:21
 * @version: v1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpoVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String assetId;
    private String stkName;
    private Integer count;
    private Integer userId;
    private String capitalAccount; // 资金账号
    private String tradeAccount; // 交易账号
    private String tradePwd; // 交易密码
    private String quantity; // 认购数量
    private String applyAmount; // 认购金额
    private String currency; // 币种
    private String handlingCharge; // 手续费
    private String type; // 0:现金 1:融资
    private String opStation; // 本地uid，可为空
    private String frozenAmount; // 冻结资金=使用金额+融资利息+认购手续费
    private String financeInterest;// 融资利息
    private String depositRate;// 融资比例
    private String key;// 解密的key
    private String password;// 加密后的密码
    private String tradeToken;// 指纹、面部解锁token
    private Integer page = 1;
    private int sort = 0;//0全部 1认购中 2待上市 4已上市
    private String beginDate;// 申购开始日期
    private String endDate;// 申购结束日期
    private String resultDate;// 中签公布日期
    private String listingDate;// 上市日期

    private String sponsor; //保荐人
    private String institutionName; //基石投资者名称
    private Integer hisProjectType; //0保荐人 4基石投资者
    private String industryCode; //行业板块代码
    private Integer pageIndex = 1; // 当前页
    private Integer pageSize = 20; // 页数
    private Integer selType = 0; //0全部 1主板 2创业板
    private Integer selStatus = 2; // 0全部 1正在处理 2通过聆讯
    private Integer tradeUnlock; // 是否进行交易解锁 0否 1是
    private Integer checkStep; // 下次不再提示查找步骤 1：是 0：否

    private String sortField;// 排序字段
    private String sortDirection;// D降序，A升序

    /******* 捷利数据接口需要登陆，第一期需要传对应的会话信息 ********/
    private String jlSession;
    private String jlUid;

	private int isQueue; //1.ipo融资认购排队(只有新版ipo排队才会传此参数)
	private Integer rewardId; //手续费抵扣券id
	private String actualFee; //实际抵扣金额
	private Long predictIpoConfigId; // 预约新股配置id
	private Long predictOrderId; // 预约新股订单id
	private Integer subscribed; // 倍数
	private Double predictIpoFinanceAmount; // 预约融资金额
	private String applyStatus; // 认购状态 0:已提交，1:已认购 2:已拒绝,3:待公布，4:已中签，5:未中签,6：已撤销 7：认购失败,8.排队中,


	/****************afe接口*****************/
	private String orderNo; //认购订单id

}
