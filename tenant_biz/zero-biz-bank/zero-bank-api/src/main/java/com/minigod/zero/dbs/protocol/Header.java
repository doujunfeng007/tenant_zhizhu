package com.minigod.zero.dbs.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * todo
 *
 * @author chenyu
 * @title Header
 * @date 2023-04-13 1:18
 * @description
 */
@Data
public class Header implements Serializable {

    private static final long serialVersionUID = -6016628488122917135L;

    private String msgId;
    private String orgId;
    private String timeStamp;
    private String ctry;
}
