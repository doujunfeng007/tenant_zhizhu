package com.minigod.zero.platform.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息平台配置参数
 * @TableName zero_message_properties
 */
public class PlatformMessageProperties implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 租户
     */
    private String tenantId;

    /**
     * 第三方名称
     */
    private String channelName;

    /**
     *
     */
    private String appId;

    /**
     *
     */
    private String appKey;

    /**
     * 1短信渠道，2邮件渠道，3推送渠道
     */
    private Integer channelType;

    /**
     * 发送方
     */
    private String sender;

    /**
     * 接口域名
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 区域
     */
    private String regionId;

    /**
     * 创建时间
     */
    private Date createTime;

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
     * 租户
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 第三方名称
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 第三方名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     *
     */
    public String getAppId() {
        return appId;
    }

    /**
     *
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     *
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     *
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 1短信渠道，2邮件渠道，3推送渠道
     */
    public Integer getChannelType() {
        return channelType;
    }

    /**
     * 1短信渠道，2邮件渠道，3推送渠道
     */
    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    /**
     * 发送方
     */
    public String getSender() {
        return sender;
    }

    /**
     * 发送方
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * 接口域名
     */
    public String getHost() {
        return host;
    }

    /**
     * 接口域名
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 端口
     */
    public String getPort() {
        return port;
    }

    /**
     * 端口
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 区域
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * 区域
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
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
        PlatformMessageProperties other = (PlatformMessageProperties) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getChannelName() == null ? other.getChannelName() == null : this.getChannelName().equals(other.getChannelName()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getAppKey() == null ? other.getAppKey() == null : this.getAppKey().equals(other.getAppKey()))
            && (this.getChannelType() == null ? other.getChannelType() == null : this.getChannelType().equals(other.getChannelType()))
            && (this.getSender() == null ? other.getSender() == null : this.getSender().equals(other.getSender()))
            && (this.getHost() == null ? other.getHost() == null : this.getHost().equals(other.getHost()))
            && (this.getPort() == null ? other.getPort() == null : this.getPort().equals(other.getPort()))
            && (this.getRegionId() == null ? other.getRegionId() == null : this.getRegionId().equals(other.getRegionId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getChannelName() == null) ? 0 : getChannelName().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getAppKey() == null) ? 0 : getAppKey().hashCode());
        result = prime * result + ((getChannelType() == null) ? 0 : getChannelType().hashCode());
        result = prime * result + ((getSender() == null) ? 0 : getSender().hashCode());
        result = prime * result + ((getHost() == null) ? 0 : getHost().hashCode());
        result = prime * result + ((getPort() == null) ? 0 : getPort().hashCode());
        result = prime * result + ((getRegionId() == null) ? 0 : getRegionId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
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
        sb.append(", channelName=").append(channelName);
        sb.append(", appId=").append(appId);
        sb.append(", appKey=").append(appKey);
        sb.append(", channelType=").append(channelType);
        sb.append(", sender=").append(sender);
        sb.append(", host=").append(host);
        sb.append(", port=").append(port);
        sb.append(", regionId=").append(regionId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
