package com.minigod.minigodinformation.dto;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.disclosure.dto.AnnouncementListDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/19 18:19
 * @Version: 1.0
 */
@Data
public class ClientAnnouncementListDTO {
    /**
     * 公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告  7IPO公告
     * {@link AnnouncementTypeEnum}
     */
    private List<Integer> typeList;

    /**
     * 标题
     */
    private String title;


    /**
     * 发布人id /名称
     */
    private String custId;
    /**
     * 产品id /名称
     */
    private String productId;

    /**
     * 状态;1.已保存  2待审核  3已驳回  4已审核  5已发布
     *  {@link AnnouncementStatusEnum}
     */
    private List<Integer>  statusList;

    /**
     * 是否所有 true 所有人可见 / false   仅自己可见
     */
    @NotNull(message = "all不能为空")
    private Boolean all;
    /**
     * 创建人id
     */
    @JsonIgnore
    private String createId;



    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endTime;
    public void setStartTime(String startTime) {
        if (startTime!= null) {
            Date parsedDate = DateUtil.parse(startTime);
            startTime = DateUtil.format(DateUtil.beginOfDay(parsedDate), "yyyy-MM-dd HH:mm:ss");
        }
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        if (endTime!= null) {
            Date parsedDate = DateUtil.parse(endTime);
            endTime = DateUtil.format(DateUtil.endOfDay(parsedDate), "yyyy-MM-dd HH:mm:ss");
        }
        this.endTime = endTime;
    }
}
