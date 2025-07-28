package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.vo.PublishVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublishReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 封装params
	 */
	private PublishVO params;
}
