package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataDictionaryDto implements Serializable {

	private String name;
	private String value;
	private String sort;

}
