package com.minigod.minigodinformation.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.disclosure.vo.AnnouncementListVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/19 18:19
 * @Version: 1.0
 */
@Data
public class ExchangeAnnListVO {

    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告
     * {@link AnnouncementTypeEnum}
     */
    private Integer type;



    /**
     * 发布时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private Date time;


    /**
     * 发布人名字
     */
    private String custName;


    private List<String> files;

    /**
     * 状态;1.已保存  2待审核  3已驳回  4已审核  5已发布
     *  {@link AnnouncementStatusEnum}
     */
    private Integer status;

    /**
     * 标题
     */
    private String title;

}
