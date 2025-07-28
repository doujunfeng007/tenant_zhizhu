package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.vo.StkVersionVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class StkVersionReqVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private StkVersionVO params;
}
