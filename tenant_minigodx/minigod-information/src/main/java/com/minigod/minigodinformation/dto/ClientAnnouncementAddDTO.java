package com.minigod.minigodinformation.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.disclosure.dto.ClientAnnouncementAddDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/21 11:14
 * @Version: 1.0
 */
@Data
public class ClientAnnouncementAddDTO {
    /**
     * 公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告
     * {@link AnnouncementTypeEnum}
     */
    @NotNull(message = "公告类型不能为空")
    private Integer type;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空")
    private String content;


    /**
     * 状态;1.保存  2提交审核
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    private Integer id;


    private List<String> files;
    private List<AnnouncementProductDTO> productList;


    public ClientAnnouncementAddDTO() {
    }
}
