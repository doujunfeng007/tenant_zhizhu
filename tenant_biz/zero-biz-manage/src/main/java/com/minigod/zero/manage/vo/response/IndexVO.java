package com.minigod.zero.manage.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/4/12 17:24
 * @version: v1.0
 */
@Data
public class IndexVO implements Serializable {
    private static final long serialVersionUID = 2290460992353477288L;

    private Integer iconCount;//icon个数
    private String names;//icon名称
    private  Integer roleId;//用户角色类型
}
