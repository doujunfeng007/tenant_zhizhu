package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 账户余额请求实体
 * @author: chenyu
 * @date: 2021/04/21 10:11
 * @version: v1.0
 */
@Data
public class SubAccountRequestVO extends DbsBaseRequestVO implements Serializable {

    // 消息流水号
    private String msgId;
    // 类型A代表新增  D代表删除
    private String requestType;
    // 主账号
    private String accountNo;
    // 固定前缀
    //按每个国家/地区列出 HK - 79 or 50
    private String vaPrefix;
    //公司代码
    private String corpCode;
    //子账户号
    private String vaNumber;
    //子账户名
    private String vaName;
    //最低限额
    private String minAmount;
    //最高限额
    private String maxAmount;


}
