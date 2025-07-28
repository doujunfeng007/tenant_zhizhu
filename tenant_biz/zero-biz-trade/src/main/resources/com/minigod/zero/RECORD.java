//
// 此文件是由 Eclipse Implementation of JAXB v3.0.2 生成的
// 请访问 https://eclipse-ee4j.github.io/jaxb-ri 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2024.06.25 时间 07:02:47 PM CST 
//


package com.minigod.zero;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}MARKETID"/&gt;
 *         &lt;element ref="{}INSTRUMENTID"/&gt;
 *         &lt;element ref="{}MARGINPERCENTAGE"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "marketid",
    "instrumentid",
    "marginpercentage"
})
@XmlRootElement(name = "RECORD")
public class RECORD {

    @XmlElement(name = "MARKETID", required = true)
    protected String marketid;
    @XmlElement(name = "INSTRUMENTID", required = true)
    protected String instrumentid;
    @XmlElement(name = "MARGINPERCENTAGE", required = true)
    protected MARGINPERCENTAGE marginpercentage;

    /**
     * 获取marketid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARKETID() {
        return marketid;
    }

    /**
     * 设置marketid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARKETID(String value) {
        this.marketid = value;
    }

    /**
     * 获取instrumentid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTRUMENTID() {
        return instrumentid;
    }

    /**
     * 设置instrumentid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTRUMENTID(String value) {
        this.instrumentid = value;
    }

    /**
     * 获取marginpercentage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MARGINPERCENTAGE }
     *     
     */
    public MARGINPERCENTAGE getMARGINPERCENTAGE() {
        return marginpercentage;
    }

    /**
     * 设置marginpercentage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MARGINPERCENTAGE }
     *     
     */
    public void setMARGINPERCENTAGE(MARGINPERCENTAGE value) {
        this.marginpercentage = value;
    }

}
