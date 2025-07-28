package com.minigod.zero.biz.common.vo.mkt;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName ApplyWaitListingCache.java
 * @Description TODO
 * @createTime 2024年05月11日 19:21:00
 */
@Data
public class ApplyWaitListingCache implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ApplyWaitListingVO> applyWaitListingVOList;
}
