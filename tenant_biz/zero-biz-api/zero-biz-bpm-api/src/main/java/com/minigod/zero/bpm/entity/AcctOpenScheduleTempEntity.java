package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 前端开户进度缓存表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_open_schedule_temp")
@ApiModel(value = "AcctOpenScheduleTemp对象", description = "前端开户进度缓存表")
public class AcctOpenScheduleTempEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 步骤
     */
    @ApiModelProperty(value = "步骤")
    private Integer step;
    /**
     * 路由
     */
    @ApiModelProperty(value = "路由")
    private String route;
    /**
     * 进度
     */
    @ApiModelProperty(value = "进度")
    private Integer schedule;
    /**
     * 银行类型
     */
    @ApiModelProperty(value = "银行类型")
    private Integer bankType;
    /**
     * 账户类型
     */
    @ApiModelProperty(value = "账户类型")
    private Integer accountType;

}
