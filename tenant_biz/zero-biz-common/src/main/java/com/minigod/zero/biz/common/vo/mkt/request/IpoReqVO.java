package com.minigod.zero.biz.common.vo.mkt.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/4/4 11:20
 * @version: v1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpoReqVO implements Serializable {

    private IpoVO params;

    private String requestSrc; // 请求来源
}
