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
 *         &lt;element ref="{}SUCCESS"/&gt;
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
    "success"
})
@XmlRootElement(name = "RETURNCODE")
public class RETURNCODE {

    @XmlElement(name = "SUCCESS", required = true)
    protected SUCCESS success;

    /**
     * 获取success属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SUCCESS }
     *     
     */
    public SUCCESS getSUCCESS() {
        return success;
    }

    /**
     * 设置success属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SUCCESS }
     *     
     */
    public void setSUCCESS(SUCCESS value) {
        this.success = value;
    }

}
