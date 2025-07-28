package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.ReportLogEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * APP日志下载 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportLogVO extends ReportLogEntity {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "上报开始时间")
	private Date startTime;
	@ApiModelProperty(value = "上报结束时间")
	private Date endTime;

	private String createUserName;
	private String updateUserName;

	private String base64File;
	private String fileExt; //文件扩展名
	private MultipartFile file;

	private int pageNum = 1;
	private int pageSize = 20;

}
