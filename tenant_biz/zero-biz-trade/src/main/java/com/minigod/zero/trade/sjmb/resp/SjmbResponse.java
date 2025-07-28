package com.minigod.zero.trade.sjmb.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName SjmbResponse.java
 * @Description 随机漫步柜台返回
 * @createTime 2024年01月10日 19:17:00
 */
@Data
public class SjmbResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code ;

    private String msg;

    private Object data;


}
