package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息表
 * @TableName customer_info
 */
public class CustomerInfo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 客户昵称
     */
    private String nickName;

    /**
     * 客户签名
     */
    private String signature;

    /**
     * 客户性别(1男，0女)
     */
    private Integer gender;

    /**
     * 客户图像
     */
    private String custIcon;

    /**
     * 推荐渠道
     */
    private String custChannel;

    /**
     * 客户密码
     */
    private String password;

    /**
     * 推荐人ID,邀请该客户的客户ID
     */
    private Long invCustId;

    /**
     * 客户类型：0-游客 1-普通个户 2-机构账户
     */
    private Integer custType;

    /**
     * 乐观锁版本号
     */
    private Integer lockVersion;

    /**
     * 手机号
     */
    private String cellPhone;

    /**
     * 国际区号
     */
    private String areaCode;

    /**
     * 注册来源：1-web 2-ios 3-aos 4-pc 5-h5
     */
    private Integer regSourceType;

    /**
     * 注册渠道：0=未知 1=互联网 2=线下开户
     */
    private Integer regChannel;

    /**
     * 注册邮箱
     */
    private String cellEmail;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 客户状态 0-停用,1-正常,2-锁定,3-注销
     */
    private Integer status;

    /**
     * 注销时间
     */
    private Date cancelTime;

    /**
     * 关联客户ID
     */
    private Long bindCust;

    /**
     * 是否已删除：1-已删除
     */
    private Integer isDeleted;

    /**
     *
     */
    private Long createDept;

    /**
     * 用户pi等级，1-Common 2-Important 3-VIP 4-PI 5-Capital
     */
    private Integer piLevel;

    /**
     * 风险等级
     */
    private Integer riskLevel;

    /**
     *
     */
    private Integer piRiskLevel;

    /**
     * 是否做过衍生品答题0未做，1做了
     */
    private Integer derivative;

    /**
     * 风险测评过期时间
     */
    private Date riskExpiryDate;

    /**
     * 密码修改时间
     */
    private Date pwdUpdTime;

    /**
     * 密码提醒状态
     */
    private Integer pwdWarnStatus;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 租户id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户id
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 客户昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 客户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 客户签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 客户签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 客户性别(1男，0女)
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 客户性别(1男，0女)
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 客户图像
     */
    public String getCustIcon() {
        return custIcon;
    }

    /**
     * 客户图像
     */
    public void setCustIcon(String custIcon) {
        this.custIcon = custIcon;
    }

    /**
     * 推荐渠道
     */
    public String getCustChannel() {
        return custChannel;
    }

    /**
     * 推荐渠道
     */
    public void setCustChannel(String custChannel) {
        this.custChannel = custChannel;
    }

    /**
     * 客户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 客户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 推荐人ID,邀请该客户的客户ID
     */
    public Long getInvCustId() {
        return invCustId;
    }

    /**
     * 推荐人ID,邀请该客户的客户ID
     */
    public void setInvCustId(Long invCustId) {
        this.invCustId = invCustId;
    }

    /**
     * 客户类型：0-游客 1-普通个户 2-机构账户
     */
    public Integer getCustType() {
        return custType;
    }

    /**
     * 客户类型：0-游客 1-普通个户 2-机构账户
     */
    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    /**
     * 乐观锁版本号
     */
    public Integer getLockVersion() {
        return lockVersion;
    }

    /**
     * 乐观锁版本号
     */
    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
    }

    /**
     * 手机号
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * 手机号
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * 国际区号
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 国际区号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 注册来源：1-web 2-ios 3-aos 4-pc 5-h5
     */
    public Integer getRegSourceType() {
        return regSourceType;
    }

    /**
     * 注册来源：1-web 2-ios 3-aos 4-pc 5-h5
     */
    public void setRegSourceType(Integer regSourceType) {
        this.regSourceType = regSourceType;
    }

    /**
     * 注册渠道：0=未知 1=互联网 2=线下开户
     */
    public Integer getRegChannel() {
        return regChannel;
    }

    /**
     * 注册渠道：0=未知 1=互联网 2=线下开户
     */
    public void setRegChannel(Integer regChannel) {
        this.regChannel = regChannel;
    }

    /**
     * 注册邮箱
     */
    public String getCellEmail() {
        return cellEmail;
    }

    /**
     * 注册邮箱
     */
    public void setCellEmail(String cellEmail) {
        this.cellEmail = cellEmail;
    }

    /**
     * 创建人
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 修改人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 客户状态 0-停用,1-正常,2-锁定,3-注销
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 客户状态 0-停用,1-正常,2-锁定,3-注销
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 注销时间
     */
    public Date getCancelTime() {
        return cancelTime;
    }

    /**
     * 注销时间
     */
    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * 关联客户ID
     */
    public Long getBindCust() {
        return bindCust;
    }

    /**
     * 关联客户ID
     */
    public void setBindCust(Long bindCust) {
        this.bindCust = bindCust;
    }

    /**
     * 是否已删除：1-已删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否已删除：1-已删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     *
     */
    public Long getCreateDept() {
        return createDept;
    }

    /**
     *
     */
    public void setCreateDept(Long createDept) {
        this.createDept = createDept;
    }

    /**
     * 用户pi等级，1-Common 2-Important 3-VIP 4-PI 5-Capital
     */
    public Integer getPiLevel() {
        return piLevel;
    }

    /**
     * 用户pi等级，1-Common 2-Important 3-VIP 4-PI 5-Capital
     */
    public void setPiLevel(Integer piLevel) {
        this.piLevel = piLevel;
    }

    /**
     * 风险等级
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     *
     */
    public Integer getPiRiskLevel() {
        return piRiskLevel;
    }

    /**
     *
     */
    public void setPiRiskLevel(Integer piRiskLevel) {
        this.piRiskLevel = piRiskLevel;
    }

    /**
     * 是否做过衍生品答题0未做，1做了
     */
    public Integer getDerivative() {
        return derivative;
    }

    /**
     * 是否做过衍生品答题0未做，1做了
     */
    public void setDerivative(Integer derivative) {
        this.derivative = derivative;
    }

    /**
     * 风险测评过期时间
     */
    public Date getRiskExpiryDate() {
        return riskExpiryDate;
    }

    /**
     * 风险测评过期时间
     */
    public void setRiskExpiryDate(Date riskExpiryDate) {
        this.riskExpiryDate = riskExpiryDate;
    }

    /**
     * 密码修改时间
     */
    public Date getPwdUpdTime() {
        return pwdUpdTime;
    }

    /**
     * 密码修改时间
     */
    public void setPwdUpdTime(Date pwdUpdTime) {
        this.pwdUpdTime = pwdUpdTime;
    }

    /**
     * 密码提醒状态
     */
    public Integer getPwdWarnStatus() {
        return pwdWarnStatus;
    }

    /**
     * 密码提醒状态
     */
    public void setPwdWarnStatus(Integer pwdWarnStatus) {
        this.pwdWarnStatus = pwdWarnStatus;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CustomerInfo other = (CustomerInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getCustIcon() == null ? other.getCustIcon() == null : this.getCustIcon().equals(other.getCustIcon()))
            && (this.getCustChannel() == null ? other.getCustChannel() == null : this.getCustChannel().equals(other.getCustChannel()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getInvCustId() == null ? other.getInvCustId() == null : this.getInvCustId().equals(other.getInvCustId()))
            && (this.getCustType() == null ? other.getCustType() == null : this.getCustType().equals(other.getCustType()))
            && (this.getLockVersion() == null ? other.getLockVersion() == null : this.getLockVersion().equals(other.getLockVersion()))
            && (this.getCellPhone() == null ? other.getCellPhone() == null : this.getCellPhone().equals(other.getCellPhone()))
            && (this.getAreaCode() == null ? other.getAreaCode() == null : this.getAreaCode().equals(other.getAreaCode()))
            && (this.getRegSourceType() == null ? other.getRegSourceType() == null : this.getRegSourceType().equals(other.getRegSourceType()))
            && (this.getRegChannel() == null ? other.getRegChannel() == null : this.getRegChannel().equals(other.getRegChannel()))
            && (this.getCellEmail() == null ? other.getCellEmail() == null : this.getCellEmail().equals(other.getCellEmail()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCancelTime() == null ? other.getCancelTime() == null : this.getCancelTime().equals(other.getCancelTime()))
            && (this.getBindCust() == null ? other.getBindCust() == null : this.getBindCust().equals(other.getBindCust()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getPiLevel() == null ? other.getPiLevel() == null : this.getPiLevel().equals(other.getPiLevel()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getPiRiskLevel() == null ? other.getPiRiskLevel() == null : this.getPiRiskLevel().equals(other.getPiRiskLevel()))
            && (this.getDerivative() == null ? other.getDerivative() == null : this.getDerivative().equals(other.getDerivative()))
            && (this.getRiskExpiryDate() == null ? other.getRiskExpiryDate() == null : this.getRiskExpiryDate().equals(other.getRiskExpiryDate()))
            && (this.getPwdUpdTime() == null ? other.getPwdUpdTime() == null : this.getPwdUpdTime().equals(other.getPwdUpdTime()))
            && (this.getPwdWarnStatus() == null ? other.getPwdWarnStatus() == null : this.getPwdWarnStatus().equals(other.getPwdWarnStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getCustIcon() == null) ? 0 : getCustIcon().hashCode());
        result = prime * result + ((getCustChannel() == null) ? 0 : getCustChannel().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getInvCustId() == null) ? 0 : getInvCustId().hashCode());
        result = prime * result + ((getCustType() == null) ? 0 : getCustType().hashCode());
        result = prime * result + ((getLockVersion() == null) ? 0 : getLockVersion().hashCode());
        result = prime * result + ((getCellPhone() == null) ? 0 : getCellPhone().hashCode());
        result = prime * result + ((getAreaCode() == null) ? 0 : getAreaCode().hashCode());
        result = prime * result + ((getRegSourceType() == null) ? 0 : getRegSourceType().hashCode());
        result = prime * result + ((getRegChannel() == null) ? 0 : getRegChannel().hashCode());
        result = prime * result + ((getCellEmail() == null) ? 0 : getCellEmail().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCancelTime() == null) ? 0 : getCancelTime().hashCode());
        result = prime * result + ((getBindCust() == null) ? 0 : getBindCust().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        result = prime * result + ((getPiLevel() == null) ? 0 : getPiLevel().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getPiRiskLevel() == null) ? 0 : getPiRiskLevel().hashCode());
        result = prime * result + ((getDerivative() == null) ? 0 : getDerivative().hashCode());
        result = prime * result + ((getRiskExpiryDate() == null) ? 0 : getRiskExpiryDate().hashCode());
        result = prime * result + ((getPwdUpdTime() == null) ? 0 : getPwdUpdTime().hashCode());
        result = prime * result + ((getPwdWarnStatus() == null) ? 0 : getPwdWarnStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", nickName=").append(nickName);
        sb.append(", signature=").append(signature);
        sb.append(", gender=").append(gender);
        sb.append(", custIcon=").append(custIcon);
        sb.append(", custChannel=").append(custChannel);
        sb.append(", password=").append(password);
        sb.append(", invCustId=").append(invCustId);
        sb.append(", custType=").append(custType);
        sb.append(", lockVersion=").append(lockVersion);
        sb.append(", cellPhone=").append(cellPhone);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", regSourceType=").append(regSourceType);
        sb.append(", regChannel=").append(regChannel);
        sb.append(", cellEmail=").append(cellEmail);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", bindCust=").append(bindCust);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createDept=").append(createDept);
        sb.append(", piLevel=").append(piLevel);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", piRiskLevel=").append(piRiskLevel);
        sb.append(", derivative=").append(derivative);
        sb.append(", riskExpiryDate=").append(riskExpiryDate);
        sb.append(", pwdUpdTime=").append(pwdUpdTime);
        sb.append(", pwdWarnStatus=").append(pwdWarnStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
