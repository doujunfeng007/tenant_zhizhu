
package com.minigod.zero.trade.afe.webservice.createaccount;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


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
 *         &lt;element name="args0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "args0"
})
@XmlRootElement(name = "insertOpenAccountMainRecord")
public class InsertOpenAccountMainRecord {

    @XmlElementRef(name = "args0", namespace = "http://webservice.g3sb.afe.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> args0;

    /**
     * 获取args0属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getArgs0() {
        return args0;
    }

    /**
     * 设置args0属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setArgs0(JAXBElement<String> value) {
        this.args0 = value;
    }

}
