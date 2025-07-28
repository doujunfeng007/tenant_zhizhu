package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: HistoryRecordVo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/1
 * @Version 1.0
 */
@Data
public class HistoryRecordVO {
    private MoneySumVO moneySum;
    private List moneyList;
}
