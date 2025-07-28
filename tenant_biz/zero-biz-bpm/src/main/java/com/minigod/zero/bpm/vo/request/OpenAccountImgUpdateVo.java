package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2019/1/16 16:21
 * @version: v1.0
 */
@Data
public class OpenAccountImgUpdateVo implements Serializable {
    private static final long serialVersionUID = 7957330193571238989L;

    private String location;
    private String type;
    private Integer userId;
    private String imagePath;
    private String applicationId;

}
