package com.minigod.zero.biz.common.vo.mkt;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 递表公司
 */

@Data
public class ApplicationListingVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long total;
    private List<ApplicationListing> list;

	@Data
    public static class ApplicationListing {
        private String applicationDate; //递表日期
        private String institutionName; //机构名称
        private String sponsors; //保荐人
        private String majorBusiness; //主要业务
        private String applicationLink; //招股书链接
        private String passDate; //聆讯时间
        private String selectionType;//版块
    }
}
