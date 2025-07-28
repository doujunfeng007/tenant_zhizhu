package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.util.List;

/**
 * @author chen
 * @ClassName PositionResp.java
 * @Description TODO
 * @createTime 2025年02月21日 17:32:00
 */
@Data
public class PositionResp {

    private List<TigerPositionResp> positions;

}
