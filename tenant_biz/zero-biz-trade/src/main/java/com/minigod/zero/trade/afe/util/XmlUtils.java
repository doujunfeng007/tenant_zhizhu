package com.minigod.zero.trade.afe.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML与JavaBean互转工具类
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    private XmlUtils() {
    }

    /**
     * JavaBean 转换为 XML
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String beanToXml(T t) {
        String xml = null;
        StringWriter stringWriter = null;
        try {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            stringWriter = new StringWriter();
            marshaller.marshal(t, stringWriter);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            xml = stringWriter.toString();
        } catch (Exception e) {
            logger.error("beanToXml error", e);
        }
        if (stringWriter != null) {
            stringWriter.flush();
            try {
                stringWriter.close();
            } catch (IOException e) {
                logger.error("close stringWriter error", e);
            }
        }
        return xml;
    }

    /**
     * XML 转转为 JavaBean
     *
     * @param xml
     * @param clazz
     * @param <T>
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        T t = null;
        StringReader stringReader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            stringReader = new StringReader(StringUtils.trim(xml));
            t = (T) unmarshaller.unmarshal(stringReader);
        } catch (JAXBException e) {
            logger.error("xmlToBean error", e);
        }
        if (stringReader != null) {
            stringReader.close();
        }
        return t;
    }



}
