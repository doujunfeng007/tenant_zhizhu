package com.minigod.zero.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 配售结果
 * @author: sunline
 * @date: 2019/9/6 9:40
 * @version: v1.0
 */

@Data
public class GetPlacingResultVo implements Serializable {

    private String name; //股票名称
    private String num; //申请人数
    private String lot; //每手股数
    private String sz; // 市值
    private String rate; //中签率
    private String claw_back; //回拨比例
    private String rlink; //公告文档链接
    private String slink;  //身份证查询文档链接
    private String subscribed; //认购倍数
    private String codes_rate; //1手中签率
    private String head_hammer; //顶头槌申购张数
    private List<List<String>> list; //配售结果明细

}
