package com.minigod.minigodinformation.dto;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
public class ClientExchangeAnnListDTO {


    /**
     * 标题
     */
    private String title;

    private String startTime;

    private String endTime;

    /**
     * 状态;1.已保存  2待审核  3已驳回  4已审核  5已发布
     *  {@link AnnouncementStatusEnum}
     */
    @JsonIgnore
    private List<Integer> statusList;
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
