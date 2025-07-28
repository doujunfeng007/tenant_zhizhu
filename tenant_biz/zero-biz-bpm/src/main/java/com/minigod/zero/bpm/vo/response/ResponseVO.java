package com.minigod.zero.bpm.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO implements Serializable {

	private static final long serialVersionUID = 4675414552962434446L;

	private Integer code = 0; //返回的状态
	private String message; //返回的消息
	private Integer id; // 返回前端生成的id,实盘版本添加
	private Object result; //返回的结果
}
