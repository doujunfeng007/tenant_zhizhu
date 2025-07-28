package com.minigod.zero.bpm.vo.request;

import lombok.Data;

/**
 * @ClassName: DepositBankListVo
 * @Description: 入金银行列表入参
 * @Author chenyu
 * @Date 2021/7/6
 * @Version 1.0
 */
@Data
public class DepositBankListVo {
    private Integer bankType;//银行区域 2香港卡 3大陆卡 1美国卡
    private String bankName;
}
