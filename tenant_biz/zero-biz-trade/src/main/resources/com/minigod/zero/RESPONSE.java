//
// 此文件是由 Eclipse Implementation of JAXB v3.0.2 生成的
// 请访问 https://eclipse-ee4j.github.io/jaxb-ri 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2024.06.25 时间 07:02:47 PM CST 
//


package com.minigod.zero;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}RESULT"/&gt;
 *         &lt;element ref="{}RETURNCODE"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "result",
    "returncode"
})
@XmlRootElement(name = "RESPONSE")
public class RESPONSE {

    @XmlElement(name = "RESULT", required = true)
    protected RESULT result;
    @XmlElement(name = "RETURNCODE", required = true)
    protected RETURNCODE returncode;
    @XmlAttribute(name = "ID")
    protected String id;

    /**
     * 获取result属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RESULT }
     *     
     */
    public RESULT getRESULT() {
        return result;
    }

    /**
     * 设置result属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RESULT }
     *     
     */
    public void setRESULT(RESULT value) {
        this.result = value;
    }

    /**
     * 获取returncode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RETURNCODE }
     *     
     */
    public RETURNCODE getRETURNCODE() {
        return returncode;
    }

    /**
     * 设置returncode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RETURNCODE }
     *     
     */
    public void setRETURNCODE(RETURNCODE value) {
        this.returncode = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

}
