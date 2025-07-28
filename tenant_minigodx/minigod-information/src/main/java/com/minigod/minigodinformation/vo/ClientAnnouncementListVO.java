package com.minigod.minigodinformation.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.disclosure.vo.AnnouncementListVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/19 18:19
 * @Version: 1.0
 */
@Data
public class ClientAnnouncementListVO {

    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告
     * {@link AnnouncementTypeEnum}
     */
    private Integer type;

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
     * 发布人id
     */
    private String publisherId;

    /**
     * 状态;1.已保存  2待审核  3已驳回  4已审核  5已发布
     *  {@link AnnouncementStatusEnum}
     */
    private Integer status;

}
