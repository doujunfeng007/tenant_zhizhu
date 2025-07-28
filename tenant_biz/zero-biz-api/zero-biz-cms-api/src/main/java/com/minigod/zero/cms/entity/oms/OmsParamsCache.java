package com.minigod.zero.cms.entity.oms;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class OmsParamsCache implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, OmsParamsEntity> configs;

	public OmsParamsCache() {
		super();
	}

	public OmsParamsCache(Map<String, OmsParamsEntity> configs) {
		super();
		this.configs = configs;
	}
}
