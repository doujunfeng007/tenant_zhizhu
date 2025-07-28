package com.minigod.zero.trade.afe.webservice.generalservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.afe.g3sb.webservice package.
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

    private final static QName _MessageTransferArgs0_QNAME = new QName("http://webservice.g3sb.afe.com", "args0");
    private final static QName _MessageTransferArgs1_QNAME = new QName("http://webservice.g3sb.afe.com", "args1");
    private final static QName _MessageTransferArgs2_QNAME = new QName("http://webservice.g3sb.afe.com", "args2");
    private final static QName _MessageTransferArgs3_QNAME = new QName("http://webservice.g3sb.afe.com", "args3");
    private final static QName _MessageTransferResponseReturn_QNAME = new QName("http://webservice.g3sb.afe.com", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afe.g3sb.webservice
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessageTransfer }
     *
     */
    public MessageTransfer createMessageTransfer() {
        return new MessageTransfer();
    }

    /**
     * Create an instance of {@link MessageTransferResponse }
     *
     */
    public MessageTransferResponse createMessageTransferResponse() {
        return new MessageTransferResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = MessageTransfer.class)
    public JAXBElement<String> createMessageTransferArgs0(String value) {
        return new JAXBElement<String>(_MessageTransferArgs0_QNAME, String.class, MessageTransfer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args1", scope = MessageTransfer.class)
    public JAXBElement<String> createMessageTransferArgs1(String value) {
        return new JAXBElement<String>(_MessageTransferArgs1_QNAME, String.class, MessageTransfer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args2", scope = MessageTransfer.class)
    public JAXBElement<String> createMessageTransferArgs2(String value) {
        return new JAXBElement<String>(_MessageTransferArgs2_QNAME, String.class, MessageTransfer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args3", scope = MessageTransfer.class)
    public JAXBElement<String> createMessageTransferArgs3(String value) {
        return new JAXBElement<String>(_MessageTransferArgs3_QNAME, String.class, MessageTransfer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = MessageTransferResponse.class)
    public JAXBElement<String> createMessageTransferResponseReturn(String value) {
        return new JAXBElement<String>(_MessageTransferResponseReturn_QNAME, String.class, MessageTransferResponse.class, value);
    }

}
