package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName t_exch_rate
 */
public class TExchRate implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 源币种
     */
    private String srccurrency;

    /**
     * 目的币种
     */
    private String dstcurrency;

    /**
     * 汇率，即  目的币种=源币种X汇率
     */
    private BigDecimal rate;

    /**
     * 数据更新时间
     */
    private Date dataupdtime;

    /**
     * 记录更新时间
     */
    private Date recordupdtime;

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
     * 源币种
     */
    public String getSrccurrency() {
        return srccurrency;
    }

    /**
     * 源币种
     */
    public void setSrccurrency(String srccurrency) {
        this.srccurrency = srccurrency;
    }

    /**
     * 目的币种
     */
    public String getDstcurrency() {
        return dstcurrency;
    }

    /**
     * 目的币种
     */
    public void setDstcurrency(String dstcurrency) {
        this.dstcurrency = dstcurrency;
    }

    /**
     * 汇率，即  目的币种=源币种X汇率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 汇率，即  目的币种=源币种X汇率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 数据更新时间
     */
    public Date getDataupdtime() {
        return dataupdtime;
    }

    /**
     * 数据更新时间
     */
    public void setDataupdtime(Date dataupdtime) {
        this.dataupdtime = dataupdtime;
    }

    /**
     * 记录更新时间
     */
    public Date getRecordupdtime() {
        return recordupdtime;
    }

    /**
     * 记录更新时间
     */
    public void setRecordupdtime(Date recordupdtime) {
        this.recordupdtime = recordupdtime;
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
        TExchRate other = (TExchRate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSrccurrency() == null ? other.getSrccurrency() == null : this.getSrccurrency().equals(other.getSrccurrency()))
            && (this.getDstcurrency() == null ? other.getDstcurrency() == null : this.getDstcurrency().equals(other.getDstcurrency()))
            && (this.getRate() == null ? other.getRate() == null : this.getRate().equals(other.getRate()))
            && (this.getDataupdtime() == null ? other.getDataupdtime() == null : this.getDataupdtime().equals(other.getDataupdtime()))
            && (this.getRecordupdtime() == null ? other.getRecordupdtime() == null : this.getRecordupdtime().equals(other.getRecordupdtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSrccurrency() == null) ? 0 : getSrccurrency().hashCode());
        result = prime * result + ((getDstcurrency() == null) ? 0 : getDstcurrency().hashCode());
        result = prime * result + ((getRate() == null) ? 0 : getRate().hashCode());
        result = prime * result + ((getDataupdtime() == null) ? 0 : getDataupdtime().hashCode());
        result = prime * result + ((getRecordupdtime() == null) ? 0 : getRecordupdtime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", srccurrency=").append(srccurrency);
        sb.append(", dstcurrency=").append(dstcurrency);
        sb.append(", rate=").append(rate);
        sb.append(", dataupdtime=").append(dataupdtime);
        sb.append(", recordupdtime=").append(recordupdtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
