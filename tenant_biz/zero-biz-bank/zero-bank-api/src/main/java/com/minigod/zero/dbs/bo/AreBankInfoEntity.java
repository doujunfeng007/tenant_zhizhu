package com.minigod.zero.dbs.bo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * DBS银行手续费流水
 *
 * @author sunline
 * @email aljqiang@163.com
 * @date 2020-05-18 13:25:16
 */
@Data
public class AreBankInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//自增ID
	private Long id;
	//请求流水号
	private String msgId;
	//来源交易流水
	private String sourceMsgId;
	//请求状态:0-系统异常；1-成功
	private String reqStatus;
	//响应状态:ACSP-查询成功；RJCT-查询失败；PART-查询成功记录超过1000
	private String enqStatus;
	//请求报文
	private String reqMessage;
	//请求时间
	private Date reqTime;
	//响应报文
	private String resMessage;
	//响应时间
	private Date resTime;
	//创建人
	private String createUser;
	//修改人
	private String updateUser;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;


}
