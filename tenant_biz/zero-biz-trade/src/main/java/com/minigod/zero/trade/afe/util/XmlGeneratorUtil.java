package com.minigod.zero.trade.afe.util;

import com.minigod.zero.trade.afe.req.DepositAmountReq;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@Slf4j
public class XmlGeneratorUtil {

	/**
	 *
	 * @param bean 类
	 * @param beforeName 前缀
	 * @param afterName 后缀
	 * @param isCase 字段名是否大小写  true默认大写  false 小写
	 * @return
	 */
	public static String toXml(Object bean, String beforeName,String afterName, Boolean isCase) {
		StringBuilder xml = new StringBuilder(beforeName);
		Class<?> beanClass = bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true); // 使得私有属性也可以访问
			try {
				Object fieldValue = field.get(bean);
				if (fieldValue == null) {
					continue;
				}
				String fieldName = field.getName();
				if (isCase != null) {
					if (isCase) {
						fieldName = fieldName.toUpperCase();
					} else {
						fieldName = fieldName.toLowerCase();
					}
				}

				xml.append("<").append(fieldName).append(">");
				xml.append(fieldValue.toString());
				xml.append("</").append(fieldName).append(">");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		xml.append(afterName);
		return xml.toString();
	}

	/**
	 *
	 * @param bean 类
	 * @param beforeName 前缀
	 * @param afterName 后缀
	 * @param isCase 字段名是否大小写  true默认大写  false 首字母大写
	 * @return
	 */
	public static String toXmlCase(Object bean, String beforeName,String afterName, Boolean isCase) {
		StringBuilder xml = new StringBuilder(beforeName);
		Class<?> beanClass = bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true); // 使得私有属性也可以访问
			try {
				Object fieldValue = field.get(bean);
				if (fieldValue == null) {
					continue;
				}
				String fieldName = field.getName();
				if (isCase != null) {
					if (!isCase) {
						fieldName = fieldName.toUpperCase();
					} else {
						char firstChar = Character.toUpperCase(fieldName.charAt(0));
						if (fieldName.length() > 1) {
							fieldName = firstChar + fieldName.substring(1);
						} else {
							fieldName = String.valueOf(firstChar);
						}
					}
				}

				xml.append("<").append(fieldName).append(">");
				xml.append(fieldValue.toString());
				xml.append("</").append(fieldName).append(">");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		xml.append(afterName);
		return xml.toString();
	}

	public static Map<String, Object> parseXml(String xml){
		Document document= null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			log.error("xml解析异常："+e.getMessage());
		}
		Element root = document.getRootElement();
		return parseElement(root);
	}

	private static Map<String, Object> parseElement(Element element) {
		Map<String, Object> result = new HashMap<>();
		Iterator<Element> it = element.elementIterator();
		while (it.hasNext()) {
			Element e = it.next();
			if (e.elementIterator().hasNext()) {
				result.put(e.getName(), parseElement(e)); // 递归解析子元素
			} else {
				result.put(e.getName(), e.getText());
			}
		}
		return result;
	}

	/*public static void main(String[] args) {
		DepositAmountReq bean = new DepositAmountReq();
		bean.setAmount("100");
		bean.setReceiptFileName("test");
		String xml = toXml(bean, "<REQUEST TYPE=\"CreateCashWithdrawal\" ID=\" Unique request ID \">","</REQUEST>", true);
		System.out.println(xml);
	}*/
}

