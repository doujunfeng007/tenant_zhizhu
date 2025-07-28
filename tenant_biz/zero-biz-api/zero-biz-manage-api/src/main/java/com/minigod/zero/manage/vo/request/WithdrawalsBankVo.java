package com.minigod.zero.manage.vo.request;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2019/4/24 17:28
 * @version: v1.0
 */
@Data
public class WithdrawalsBankVo {

    private Integer bankType;
    private String startDate;
    private String endDate;
    private Integer isShow = -1;

}
