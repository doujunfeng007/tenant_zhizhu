package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 用户实名认证表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_realname_verify")
@ApiModel(value = "AcctRealnameVerify对象", description = "用户实名认证表")
public class AcctRealnameVerifyEntity implements Serializable {

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
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idcard;
    /**
     * 绑定序号 0表示当前使用记录(有效状态) 其余数字为历史记录
     */
    @ApiModelProperty(value = "绑定序号 0表示当前使用记录(有效状态) 其余数字为历史记录")
    private Integer sequence;
    /**
     * 实名失败认证次数
     */
    @ApiModelProperty(value = "实名失败认证次数")
    private Integer verifieErrCount;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 乐观锁版本号
     */
    @ApiModelProperty(value = "乐观锁版本号")
    private Integer lockVersion;

}
