package com.minigod.minigodinformation.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.disclosure.dto.AnnouncementListDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/19 18:19
 * @Version: 1.0
 */
@Data
public class ClientInfAnnListDTO {

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
