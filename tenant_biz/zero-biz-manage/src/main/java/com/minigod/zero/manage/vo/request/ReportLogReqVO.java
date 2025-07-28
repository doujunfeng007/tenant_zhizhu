package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.vo.ReportLogVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportLogReqVO implements Serializable {
	private ReportLogVO params;
}
