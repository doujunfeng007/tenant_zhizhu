package com.minigod.zero.biz.common.vo.mkt;

import lombok.Data;

import java.io.Serializable;


@Data
public class SponsorsProjectsVo implements Serializable {

    private String stkName;
    private String stkCode;
    private String grayPriceChg; //暗盘涨跌幅
    private String firstDayChg; //首次涨跌幅
    private String listedDate; //上市日期
}
