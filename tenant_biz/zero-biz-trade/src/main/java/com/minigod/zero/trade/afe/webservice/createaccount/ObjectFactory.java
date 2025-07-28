
package com.minigod.zero.trade.afe.webservice.createaccount;

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

    private final static QName _RunArgs0_QNAME = new QName("http://webservice.g3sb.afe.com", "args0");
    private final static QName _RunResponseReturn_QNAME = new QName("http://webservice.g3sb.afe.com", "return");
    private final static QName _QueryParametersArgs1_QNAME = new QName("http://webservice.g3sb.afe.com", "args1");
    private final static QName _QueryParametersArgs2_QNAME = new QName("http://webservice.g3sb.afe.com", "args2");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afe.g3sb.webservice
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Run }
     *
     */
    public Run createRun() {
        return new Run();
    }

    /**
     * Create an instance of {@link RunResponse }
     *
     */
    public RunResponse createRunResponse() {
        return new RunResponse();
    }

    /**
     * Create an instance of {@link QueryEmailAddress }
     *
     */
    public QueryEmailAddress createQueryEmailAddress() {
        return new QueryEmailAddress();
    }

    /**
     * Create an instance of {@link QueryEmailAddressResponse }
     *
     */
    public QueryEmailAddressResponse createQueryEmailAddressResponse() {
        return new QueryEmailAddressResponse();
    }

    /**
     * Create an instance of {@link QueryCommissionScheme }
     *
     */
    public QueryCommissionScheme createQueryCommissionScheme() {
        return new QueryCommissionScheme();
    }

    /**
     * Create an instance of {@link QueryCommissionSchemeResponse }
     *
     */
    public QueryCommissionSchemeResponse createQueryCommissionSchemeResponse() {
        return new QueryCommissionSchemeResponse();
    }

    /**
     * Create an instance of {@link InsertOpenAccountMainRecord }
     *
     */
    public InsertOpenAccountMainRecord createInsertOpenAccountMainRecord() {
        return new InsertOpenAccountMainRecord();
    }

    /**
     * Create an instance of {@link InsertOpenAccountMainRecordResponse }
     *
     */
    public InsertOpenAccountMainRecordResponse createInsertOpenAccountMainRecordResponse() {
        return new InsertOpenAccountMainRecordResponse();
    }

    /**
     * Create an instance of {@link QueryMultilingualMessage }
     *
     */
    public QueryMultilingualMessage createQueryMultilingualMessage() {
        return new QueryMultilingualMessage();
    }

    /**
     * Create an instance of {@link QueryMultilingualMessageResponse }
     *
     */
    public QueryMultilingualMessageResponse createQueryMultilingualMessageResponse() {
        return new QueryMultilingualMessageResponse();
    }

    /**
     * Create an instance of {@link QueryIDNumber }
     *
     */
    public QueryIDNumber createQueryIDNumber() {
        return new QueryIDNumber();
    }

    /**
     * Create an instance of {@link QueryIDNumberResponse }
     *
     */
    public QueryIDNumberResponse createQueryIDNumberResponse() {
        return new QueryIDNumberResponse();
    }

    /**
     * Create an instance of {@link QueryPhoneNumber }
     *
     */
    public QueryPhoneNumber createQueryPhoneNumber() {
        return new QueryPhoneNumber();
    }

    /**
     * Create an instance of {@link QueryPhoneNumberResponse }
     *
     */
    public QueryPhoneNumberResponse createQueryPhoneNumberResponse() {
        return new QueryPhoneNumberResponse();
    }

    /**
     * Create an instance of {@link QueryParameters }
     *
     */
    public QueryParameters createQueryParameters() {
        return new QueryParameters();
    }

    /**
     * Create an instance of {@link QueryParametersResponse }
     *
     */
    public QueryParametersResponse createQueryParametersResponse() {
        return new QueryParametersResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = Run.class)
    public JAXBElement<String> createRunArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, Run.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = RunResponse.class)
    public JAXBElement<String> createRunResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, RunResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = QueryEmailAddress.class)
    public JAXBElement<String> createQueryEmailAddressArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, QueryEmailAddress.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = QueryEmailAddressResponse.class)
    public JAXBElement<String> createQueryEmailAddressResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, QueryEmailAddressResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = QueryCommissionScheme.class)
    public JAXBElement<String> createQueryCommissionSchemeArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, QueryCommissionScheme.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = QueryCommissionSchemeResponse.class)
    public JAXBElement<String> createQueryCommissionSchemeResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, QueryCommissionSchemeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = InsertOpenAccountMainRecord.class)
    public JAXBElement<String> createInsertOpenAccountMainRecordArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, InsertOpenAccountMainRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = InsertOpenAccountMainRecordResponse.class)
    public JAXBElement<String> createInsertOpenAccountMainRecordResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, InsertOpenAccountMainRecordResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = QueryMultilingualMessageResponse.class)
    public JAXBElement<String> createQueryMultilingualMessageResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, QueryMultilingualMessageResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = QueryIDNumber.class)
    public JAXBElement<String> createQueryIDNumberArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, QueryIDNumber.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = QueryIDNumberResponse.class)
    public JAXBElement<String> createQueryIDNumberResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, QueryIDNumberResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = QueryPhoneNumber.class)
    public JAXBElement<String> createQueryPhoneNumberArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, QueryPhoneNumber.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = QueryPhoneNumberResponse.class)
    public JAXBElement<String> createQueryPhoneNumberResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, QueryPhoneNumberResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args0", scope = QueryParameters.class)
    public JAXBElement<String> createQueryParametersArgs0(String value) {
        return new JAXBElement<String>(_RunArgs0_QNAME, String.class, QueryParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args1", scope = QueryParameters.class)
    public JAXBElement<String> createQueryParametersArgs1(String value) {
        return new JAXBElement<String>(_QueryParametersArgs1_QNAME, String.class, QueryParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "args2", scope = QueryParameters.class)
    public JAXBElement<String> createQueryParametersArgs2(String value) {
        return new JAXBElement<String>(_QueryParametersArgs2_QNAME, String.class, QueryParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webservice.g3sb.afe.com", name = "return", scope = QueryParametersResponse.class)
    public JAXBElement<String> createQueryParametersResponseReturn(String value) {
        return new JAXBElement<String>(_RunResponseReturn_QNAME, String.class, QueryParametersResponse.class, value);
    }

}
