package com.minigod.minigodinformation.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.disclosure.dto.AnnouncementAddDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/19 17:48
 * @Version: 1.0
 */
@Data
public class AnnouncementAddDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date time;



    /**
     * 状态;1.保存  2提交审批
     *  {@link AnnouncementStatusEnum}
     */
    private Integer status;
}
