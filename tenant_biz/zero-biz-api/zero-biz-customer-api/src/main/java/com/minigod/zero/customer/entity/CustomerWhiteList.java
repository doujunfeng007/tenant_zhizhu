package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName customer_white_list
 */
public class CustomerWhiteList implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 客户id
     */
    private Long custId;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     *
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long creator;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 0有效，1无效
     */
    private Integer status;

    /**
     * 0正常，1删除
     */
    private Integer idDeleted;

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
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
     * 客户id
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 客户id
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 理财账号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 理财账号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     *
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人id
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * 创建人id
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * 创建人名称
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * 创建人名称
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * 0有效，1无效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0有效，1无效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 0正常，1删除
     */
    public Integer getIdDeleted() {
        return idDeleted;
    }

    /**
     * 0正常，1删除
     */
    public void setIdDeleted(Integer idDeleted) {
        this.idDeleted = idDeleted;
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
        CustomerWhiteList other = (CustomerWhiteList) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreatorName() == null ? other.getCreatorName() == null : this.getCreatorName().equals(other.getCreatorName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIdDeleted() == null ? other.getIdDeleted() == null : this.getIdDeleted().equals(other.getIdDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreatorName() == null) ? 0 : getCreatorName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIdDeleted() == null) ? 0 : getIdDeleted().hashCode());
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
        sb.append(", custId=").append(custId);
        sb.append(", accountId=").append(accountId);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator=").append(creator);
        sb.append(", creatorName=").append(creatorName);
        sb.append(", status=").append(status);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
