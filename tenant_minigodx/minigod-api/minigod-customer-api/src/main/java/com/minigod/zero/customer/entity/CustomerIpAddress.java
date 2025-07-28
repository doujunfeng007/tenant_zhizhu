package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ip的真实地址信息
 * @TableName customer_ip_address
 */
public class CustomerIpAddress implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * ip地址
     */
    private String realIp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 国家中文名称
     */
    private String countryCnName;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 地区中文名称
     */
    private String regionCnName;

    /**
     * 地区code
     */
    private String regionCode;

    /**
     * 地区id
     */
    private String regionId;

    /**
     * 逻辑删除[0-否 1-是]
     */
    private Integer isDeleted;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建部门
     */
    private Long createDept;

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 请求ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 请求ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * ip地址
     */
    public String getRealIp() {
        return realIp;
    }

    /**
     * ip地址
     */
    public void setRealIp(String realIp) {
        this.realIp = realIp;
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
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 更新人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 国家中文名称
     */
    public String getCountryCnName() {
        return countryCnName;
    }

    /**
     * 国家中文名称
     */
    public void setCountryCnName(String countryCnName) {
        this.countryCnName = countryCnName;
    }

    /**
     * 国家代码
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 国家代码
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 地区中文名称
     */
    public String getRegionCnName() {
        return regionCnName;
    }

    /**
     * 地区中文名称
     */
    public void setRegionCnName(String regionCnName) {
        this.regionCnName = regionCnName;
    }

    /**
     * 地区code
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * 地区code
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * 地区id
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * 地区id
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     * 逻辑删除[0-否 1-是]
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 逻辑删除[0-否 1-是]
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 创建部门
     */
    public Long getCreateDept() {
        return createDept;
    }

    /**
     * 创建部门
     */
    public void setCreateDept(Long createDept) {
        this.createDept = createDept;
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
        CustomerIpAddress other = (CustomerIpAddress) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getRealIp() == null ? other.getRealIp() == null : this.getRealIp().equals(other.getRealIp()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCountryCnName() == null ? other.getCountryCnName() == null : this.getCountryCnName().equals(other.getCountryCnName()))
            && (this.getCountryCode() == null ? other.getCountryCode() == null : this.getCountryCode().equals(other.getCountryCode()))
            && (this.getRegionCnName() == null ? other.getRegionCnName() == null : this.getRegionCnName().equals(other.getRegionCnName()))
            && (this.getRegionCode() == null ? other.getRegionCode() == null : this.getRegionCode().equals(other.getRegionCode()))
            && (this.getRegionId() == null ? other.getRegionId() == null : this.getRegionId().equals(other.getRegionId()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getRealIp() == null) ? 0 : getRealIp().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCountryCnName() == null) ? 0 : getCountryCnName().hashCode());
        result = prime * result + ((getCountryCode() == null) ? 0 : getCountryCode().hashCode());
        result = prime * result + ((getRegionCnName() == null) ? 0 : getRegionCnName().hashCode());
        result = prime * result + ((getRegionCode() == null) ? 0 : getRegionCode().hashCode());
        result = prime * result + ((getRegionId() == null) ? 0 : getRegionId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ip=").append(ip);
        sb.append(", realIp=").append(realIp);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", countryCnName=").append(countryCnName);
        sb.append(", countryCode=").append(countryCode);
        sb.append(", regionCnName=").append(regionCnName);
        sb.append(", regionCode=").append(regionCode);
        sb.append(", regionId=").append(regionId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", status=").append(status);
        sb.append(", createDept=").append(createDept);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
