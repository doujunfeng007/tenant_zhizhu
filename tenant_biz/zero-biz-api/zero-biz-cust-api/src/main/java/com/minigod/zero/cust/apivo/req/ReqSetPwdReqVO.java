package com.minigod.zero.cust.apivo.req;

import com.minigod.zero.cust.apivo.SNVersion;
import lombok.Data;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.ReqSetPwdReqVO
 * @Date: 2023年02月18日 15:06
 * @Description:
 */
@Data
public class ReqSetPwdReqVO extends SNVersion {

	private static final long serialVersionUID = -1L;

	private ReqSetPwdVO params;


}
