package com.minigod.minigodinformation.vo;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BackAnnouncementListVO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

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
    /**
     * 产品list
     */
    private List<AnnouncementProductVO> productList;

    private List<String> files;

    /**
     * 状态;1.已保存  2待审核  3已驳回  4已审核  5已发布
     *  {@link AnnouncementStatusEnum}
     */
    private Integer status;
    /**
     * 任务id
     */
    private String taskId;

    /**
     * 预约号
     */
    private String applicationId;

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

}
