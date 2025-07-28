package com.minigod.zero.bpm.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author LiYangFeng
 * @createDate 2017/2/13
 * @description
 * @email justbelyf@gmail.com
 */
@Data
public class OtherAccountDetailInfo {

    @JSONField(name ="bankName")
    private String bankName;

    @JSONField(name ="accountNumber")
    private String accountNumber;

    @Override
    public String toString() {
        return "OtherAccountDetailInfo{" +
                "bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
