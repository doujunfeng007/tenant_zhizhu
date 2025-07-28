package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户edda申请图片信息表
 * @TableName client_edda_info_image
 */
public class ClientEddaInfoImage implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 预约流水号
     */
    private String applicationId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 存储文件名
     */
    private String fileStorageName;

    /**
     * 文件拓展名
     */
    private String extName;

    /**
     * 图片位置类型[0=未知 1=身份证正面照 2=身份证反面照片]
     */
    private Integer imageLocationType;

    /**
     * 指定存储点路径
     */
    private String storagePath;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 创建科室
     */
    private Long createDept;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 预约流水号
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 预约流水号
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 存储文件名
     */
    public String getFileStorageName() {
        return fileStorageName;
    }

    /**
     * 存储文件名
     */
    public void setFileStorageName(String fileStorageName) {
        this.fileStorageName = fileStorageName;
    }

    /**
     * 文件拓展名
     */
    public String getExtName() {
        return extName;
    }

    /**
     * 文件拓展名
     */
    public void setExtName(String extName) {
        this.extName = extName;
    }

    /**
     * 图片位置类型[0=未知 1=身份证正面照 2=身份证反面照片]
     */
    public Integer getImageLocationType() {
        return imageLocationType;
    }

    /**
     * 图片位置类型[0=未知 1=身份证正面照 2=身份证反面照片]
     */
    public void setImageLocationType(Integer imageLocationType) {
        this.imageLocationType = imageLocationType;
    }

    /**
     * 指定存储点路径
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * 指定存储点路径
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
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
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 租户 ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户 ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 创建科室
     */
    public Long getCreateDept() {
        return createDept;
    }

    /**
     * 创建科室
     */
    public void setCreateDept(Long createDept) {
        this.createDept = createDept;
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
     * 是否删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除
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
        ClientEddaInfoImage other = (ClientEddaInfoImage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFileStorageName() == null ? other.getFileStorageName() == null : this.getFileStorageName().equals(other.getFileStorageName()))
            && (this.getExtName() == null ? other.getExtName() == null : this.getExtName().equals(other.getExtName()))
            && (this.getImageLocationType() == null ? other.getImageLocationType() == null : this.getImageLocationType().equals(other.getImageLocationType()))
            && (this.getStoragePath() == null ? other.getStoragePath() == null : this.getStoragePath().equals(other.getStoragePath()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileStorageName() == null) ? 0 : getFileStorageName().hashCode());
        result = prime * result + ((getExtName() == null) ? 0 : getExtName().hashCode());
        result = prime * result + ((getImageLocationType() == null) ? 0 : getImageLocationType().hashCode());
        result = prime * result + ((getStoragePath() == null) ? 0 : getStoragePath().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
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
        sb.append(", applicationId=").append(applicationId);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileStorageName=").append(fileStorageName);
        sb.append(", extName=").append(extName);
        sb.append(", imageLocationType=").append(imageLocationType);
        sb.append(", storagePath=").append(storagePath);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createDept=").append(createDept);
        sb.append(", status=").append(status);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
