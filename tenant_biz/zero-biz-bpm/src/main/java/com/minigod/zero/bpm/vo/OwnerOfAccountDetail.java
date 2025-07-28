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
public class OwnerOfAccountDetail {

    @JSONField(name ="ownName")
    private String ownName;

    @JSONField(name ="ownAddress")
    private String ownAddress;

    @Override
    public String toString() {
        return "OwnerOfAccountDetail{" +
                "ownName='" + ownName + '\'' +
                ", ownAddress='" + ownAddress + '\'' +
                '}';
    }
}
