package com.minigod.zero.manage.vo;

import com.minigod.zero.biz.common.enums.CommonEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdLinkDetailVO  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestSrc;
	private List<Integer> posCodes;
	private Integer btype = CommonEnums.BoardType._XSGG.getValue();
	private Long version;
}
