package com.minigod.zero.trade.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 配售结果
 * @author: sunline
 * @date: 2019/9/6 10:48
 * @version: v1.0
 */
@Data
public class PlacingResultVo {

    private String stkName; //股票名称
    private String applyNum; //申购人数
    private String lotSize; //每手股数
    private String mktVal; //市值
    private String rate; //中签率
    private String clawBack; //回拨比例
    private String rlink; //公告文档链接
    private String slink;  //身份证查询文档链接
    private String subscribed; //认购倍数
    private String codesRate; //1手中签率
    private String headHammer; //顶头槌申购张数
    private List<PlacingResultDetail> detail; //配售结果明细

	@Data
    public static class PlacingResultDetail {
        private String applyQuantity; //申购股数
        private String applyNum; //申购人数
        private String quantityAllotted; //中签数量
        private String rateAllotted; //中签率
        private String remark; //备注
        private String type; //0甲 1乙
    }

}
