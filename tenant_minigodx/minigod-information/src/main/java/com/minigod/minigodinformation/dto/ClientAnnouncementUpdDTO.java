package com.minigod.minigodinformation.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.disclosure.dto.ClientAnnouncementAddDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/21 11:14
 * @Version: 1.0
 */
@Data
public class ClientAnnouncementUpdDTO {
    /**
     * 公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告
     * {@link AnnouncementTypeEnum}
     */
    @NotNull
    private Integer type;

    /**
     * 标题
     */
    @NotNull
    private String title;

    /**
     * 内容
     */
    @NotNull
    private String content;

    /**
     * 发布时间
     */
    @NotNull
    private Date time;

    /**
     * 状态;1.保存  2提交审核
     */
    @NotNull
    private Integer status;

    @NotNull
    private Integer id;


    private List<String> files;


}
