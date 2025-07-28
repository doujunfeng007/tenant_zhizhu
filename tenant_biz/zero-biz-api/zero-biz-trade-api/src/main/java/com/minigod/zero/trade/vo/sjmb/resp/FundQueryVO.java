package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName FundQueryVO.java
 * @Description TODO
 * @createTime 2024年01月19日 16:32:00
 */
@Data
public class FundQueryVO implements Serializable {

	private static final long serialVersionUID = 1L;


	private List<AssetInfoVO> fundStats ;

	private List<PositionVO> hkPosition;

	private List<PositionVO> usPosition;

	private List<PositionVO> cnPosition;

	private List<PositionVO> usopPosition;




}
