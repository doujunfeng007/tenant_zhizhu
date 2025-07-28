package com.minigod.zero.biz.common.cache;

import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import lombok.Data;

import java.io.Serializable;

/**
 * IPO垫资认购请求缓存
 *
 * @author sunline
 */
@Data
public class IpoApplyCache implements Serializable {
    private IpoVO ipoVO;
    private String paramJson;
}
