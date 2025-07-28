package com.minigod.zero.cust.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserInvestorStmtReqVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "params不能为空")
    private UserInvestorStmtVo params;
}
