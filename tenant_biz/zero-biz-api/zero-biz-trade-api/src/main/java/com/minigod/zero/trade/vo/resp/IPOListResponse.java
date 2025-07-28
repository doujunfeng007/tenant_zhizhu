package com.minigod.zero.trade.vo.resp;


import com.minigod.zero.trade.vo.IPOInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IPOListResponse implements Serializable {
	private List<IPOInfo> ipoInfoList;
}
