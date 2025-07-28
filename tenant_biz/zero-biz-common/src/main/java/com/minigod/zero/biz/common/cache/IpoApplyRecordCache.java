package com.minigod.zero.biz.common.cache;


import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * IPO认购记录缓存
 */
@Data
public class IpoApplyRecordCache implements Serializable {
    private List<IpoVO> list = new ArrayList<>();
}
