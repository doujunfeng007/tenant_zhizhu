package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiYangFeng
 * @createDate 2017/2/22
 * @description
 * @email justbelyf@gmail.com
 */
@Data
public class ImageInfo implements Serializable {

    private Integer imageLocation;

    private Integer imageLocationType;

    private String imageUrl;

}
