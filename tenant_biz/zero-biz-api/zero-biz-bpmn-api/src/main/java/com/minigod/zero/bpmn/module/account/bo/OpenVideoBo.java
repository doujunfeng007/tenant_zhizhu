package com.minigod.zero.bpmn.module.account.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @ClassName: OpenVideoBo
 * @Description:
 * @Author chenyu
 * @Date 2024/2/1
 * @Version 1.0
 */
@Data
public class OpenVideoBo {
    private MultipartFile file; //
    private String location; // 类型
    private String type; // 文件类型
}
