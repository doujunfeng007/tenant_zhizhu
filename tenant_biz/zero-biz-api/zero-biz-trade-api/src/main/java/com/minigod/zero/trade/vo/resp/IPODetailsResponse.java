package com.minigod.zero.trade.vo.resp;

import com.minigod.zero.trade.vo.IPOInfo;
import com.minigod.zero.trade.vo.IPOLevel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IPODetailsResponse implements Serializable {
    private IPOInfo ipoInfo;
    private List<IPOLevel> levels;
    private long updateMills;// 最后更新时间
}
