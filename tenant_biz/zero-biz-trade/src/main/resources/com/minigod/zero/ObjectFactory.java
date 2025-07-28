//
// 此文件是由 Eclipse Implementation of JAXB v3.0.2 生成的
// 请访问 https://eclipse-ee4j.github.io/jaxb-ri 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2024.06.25 时间 07:02:47 PM CST 
//


package com.minigod.zero;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.minigod.zero package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MARKETID_QNAME = new QName("", "MARKETID");
    private final static QName _INSTRUMENTID_QNAME = new QName("", "INSTRUMENTID");
    private final static QName _ARGUMENTS_QNAME = new QName("", "ARGUMENTS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.minigod.zero
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MARGINPERCENTAGE }
     * 
     */
    public MARGINPERCENTAGE createMARGINPERCENTAGE() {
        return new MARGINPERCENTAGE();
    }

    /**
     * Create an instance of {@link RECORD }
     * 
     */
    public RECORD createRECORD() {
        return new RECORD();
    }

    /**
     * Create an instance of {@link TIME }
     * 
     */
    public TIME createTIME() {
        return new TIME();
    }

    /**
     * Create an instance of {@link SUCCESS }
     * 
     */
    public SUCCESS createSUCCESS() {
        return new SUCCESS();
    }

    /**
     * Create an instance of {@link RESULT }
     * 
     */
    public RESULT createRESULT() {
        return new RESULT();
    }

    /**
     * Create an instance of {@link RETURNCODE }
     * 
     */
    public RETURNCODE createRETURNCODE() {
        return new RETURNCODE();
    }

    /**
     * Create an instance of {@link RESPONSE }
     * 
     */
    public RESPONSE createRESPONSE() {
        return new RESPONSE();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "MARKETID")
    public JAXBElement<String> createMARKETID(String value) {
        return new JAXBElement<String>(_MARKETID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "INSTRUMENTID")
    public JAXBElement<String> createINSTRUMENTID(String value) {
        return new JAXBElement<String>(_INSTRUMENTID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "ARGUMENTS")
    public JAXBElement<String> createARGUMENTS(String value) {
        return new JAXBElement<String>(_ARGUMENTS_QNAME, String.class, null, value);
    }

}
