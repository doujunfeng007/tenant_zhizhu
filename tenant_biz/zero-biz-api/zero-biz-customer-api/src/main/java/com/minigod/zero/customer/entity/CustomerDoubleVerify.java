package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 二重认证信息
 * @TableName customer_double_verify
 */
public class CustomerDoubleVerify implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 客户ID
     */
    private Long custId;

    /**
     * 勾选过7天的过期时间
     */
    private Date lastDatetime;

    /**
     * 设备序号
     */
    private String equipmentNum;

    /**
     * 是否选择7天内不再提醒 0是没选中，1表示选中
     */
    private Integer selectedType;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建部门
     */
    private Long createDept;

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
     * 状态：0-无效/禁用 1-有效/启用
     */
    private Integer status;

    /**
     * 是否已删除：1-已删除
     */
    private Integer isDeleted;

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
     * 客户ID
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 客户ID
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 勾选过7天的过期时间
     */
    public Date getLastDatetime() {
        return lastDatetime;
    }

    /**
     * 勾选过7天的过期时间
     */
    public void setLastDatetime(Date lastDatetime) {
        this.lastDatetime = lastDatetime;
    }

    /**
     * 设备序号
     */
    public String getEquipmentNum() {
        return equipmentNum;
    }

    /**
     * 设备序号
     */
    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    /**
     * 是否选择7天内不再提醒 0是没选中，1表示选中
     */
    public Integer getSelectedType() {
        return selectedType;
    }

    /**
     * 是否选择7天内不再提醒 0是没选中，1表示选中
     */
    public void setSelectedType(Integer selectedType) {
        this.selectedType = selectedType;
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
     * 状态：0-无效/禁用 1-有效/启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态：0-无效/禁用 1-有效/启用
     */
    public void setStatus(Integer status) {
        this.status = status;
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
        CustomerDoubleVerify other = (CustomerDoubleVerify) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getLastDatetime() == null ? other.getLastDatetime() == null : this.getLastDatetime().equals(other.getLastDatetime()))
            && (this.getEquipmentNum() == null ? other.getEquipmentNum() == null : this.getEquipmentNum().equals(other.getEquipmentNum()))
            && (this.getSelectedType() == null ? other.getSelectedType() == null : this.getSelectedType().equals(other.getSelectedType()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getLastDatetime() == null) ? 0 : getLastDatetime().hashCode());
        result = prime * result + ((getEquipmentNum() == null) ? 0 : getEquipmentNum().hashCode());
        result = prime * result + ((getSelectedType() == null) ? 0 : getSelectedType().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", custId=").append(custId);
        sb.append(", lastDatetime=").append(lastDatetime);
        sb.append(", equipmentNum=").append(equipmentNum);
        sb.append(", selectedType=").append(selectedType);
        sb.append(", createUser=").append(createUser);
        sb.append(", createDept=").append(createDept);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
