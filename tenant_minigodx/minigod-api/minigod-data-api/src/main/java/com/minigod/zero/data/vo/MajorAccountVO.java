package com.minigod.zero.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 21:11
 * @description：
 */
@Data
public class MajorAccountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MajorAccountAssetsVO> majorAccountAssetsList;
	private BigDecimal hldTotalQuantity;
	private BigDecimal bondTotalQuantity;
	private BigDecimal totalQuantity;
	private BigDecimal proportion;
}
