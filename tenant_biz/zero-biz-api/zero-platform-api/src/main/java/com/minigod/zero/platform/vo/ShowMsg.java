/**
 * @Title: ShowMsg.java
 * @Copyright: © 2015 sunline
 * @Company: sunline
 */

package com.minigod.zero.platform.vo;

import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @description
 *
 * @author Sunline
 * @date 2015-4-11 下午4:49:24
 * @version v1.0
 */
@Data
public class ShowMsg extends PlatformInvestMsgEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userIcon;
	private String nickName;
	private Integer userType;
}
