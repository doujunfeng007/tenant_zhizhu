package com.minigod.minigodinformation.vo;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: com.minigod.zero.disclosure.vo.AnnouncementListVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/19 18:19
 * @Version: 1.0
 */
@Data
@Slf4j
public class ClientInfAnnListVO {

    private Integer id;

    /**
     * 1 交易所通知   2broker公告
     */
    private Integer announcementType;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布人名字
     */
    private String custName;
    /**
     * 产品list
     */
    private List<AnnouncementProductVO> productList;

    @JsonIgnore
    private List<String> files;

    private List<Map<String,Object>> pdfFiles;

    public List<Map<String,Object>> getPdfFiles() {
        this.pdfFiles = new ArrayList<>();
        try {
            if (!ObjectUtils.isEmpty(files)){
                files.forEach(file -> {
                    JSONObject jsonObject = JSON.parseObject(file);
                    pdfFiles.add(jsonObject);
                });
            }
        } catch (Exception e) {
            log.error("getPdfFiles error", e);
        }
        return pdfFiles;
    }



    /**
     * 标题
     */
    private String title;


    /**
     * 时间
     */
    private Date time;

}
