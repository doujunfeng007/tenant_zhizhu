package com.minigod.zero.biz.common.cache;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author chen
 * @ClassName DeliverableTableVO.java
 * @Description 可递表缓存数据VO
 * @createTime 2024年05月07日 17:28:00
 */
@Data
public class DeliverableTableVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<DeliverableTable> deliverableTableList;

    @Data
    public static class DeliverableTable implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 申请日期
         */
        private String applicationDate;

        /**
         * 板块 1 主板 2 创业板
         */
        private String boardName;

        /**
         * 状态 0 正在处理 1 通过聆讯
         */
        private String status;

        /**
         * 公司名称
         */
        private String companyName;

        /**
         * 股票代码
         */
        private String assetId;
    }
}
