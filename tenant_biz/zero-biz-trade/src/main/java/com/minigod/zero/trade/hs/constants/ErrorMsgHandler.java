package com.minigod.zero.trade.hs.constants;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 恒生错误码信息
 */
@Slf4j
@Component
public class ErrorMsgHandler {
	public static Map<String, Error> ERROR_INFO = new HashMap<>();

//	@PostConstruct
//	public void init(){
//		log.info(">>>>>>>>>>>>载入恒生错误码文件");
//		SAXReader reader = new SAXReader();
//		try {
//			List<InputStream> inputStreams = new ArrayList<>();
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_0.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_1.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_2.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_3.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_4.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_5.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_6.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_7.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_8.xml"));
//			inputStreams.add(ResourceUtil.getStream("hs/Glossary_Error_FC.xml"));
//			for(InputStream inputStream : inputStreams){
//				Iterator it = reader.read(inputStream).getRootElement().elementIterator();
//				while(it.hasNext()){
//					Element element = (Element)it.next();
//					List<Attribute> elementAttrs = element.attributes();
//					String errorCode = StringUtils.EMPTY;
//					String sChinese = StringUtils.EMPTY;
//					String english = StringUtils.EMPTY;
//					String tChinese = StringUtils.EMPTY;
//					for(Attribute attr:elementAttrs){
//						switch (attr.getName()) {
//							case "Base":
//								errorCode = attr.getValue().trim();
//								break;
//							case "SimplifiedChinese":
//								sChinese = attr.getValue().trim();
//								break;
//							case "English":
//								english = attr.getValue().trim();
//								break;
//							case "TraditionalChinese":
//								tChinese = attr.getValue().trim();
//								break;
//							default:
//								break;
//						}
//					}
//					Error error = ERROR_INFO.get(errorCode);
//					if(error == null){
//						error = new Error(errorCode,sChinese,english,tChinese);
//						ERROR_INFO.put(errorCode,error);
//					}else{
//						if(!StringUtils.equals(error.getSChinese(),sChinese)){
//							error.setSChinese(error.getSChinese() + "[" + sChinese + "]");
//						}
//						if(!StringUtils.equals(error.getEnglish(),english)){
//							error.setEnglish(error.getEnglish() + "[" + english + "]");
//						}
//						if(!StringUtils.equals(error.getTChinese(),tChinese)){
//							error.setTChinese(error.getTChinese() + "[" + tChinese + "]");
//						}
//					}
//				}
//				try{
//					inputStream.close();
//				}catch (Exception e) {
//				    log.info("inputStream.close error");
//				}
//
//			}
//		} catch (Exception e) {
//			log.error("载入恒生错误码文件失败",e);
//		}
//	}

    /**
     * 获取错误码国际化提示语
	 * lang: zh-Hans（简体）zh-Hant（繁体）en（英文）
     */
    public static String getErrorMsg(String errorCode, String lang) {
		String msg = StringUtils.EMPTY;
		Error error = ERROR_INFO.get(errorCode);
		if(error == null){
			return msg;
		}
		if(StringUtils.isEmpty(lang)){
			lang = "zh-CN";
		}

		switch (lang){
			case "en":
				msg = error.getEnglish();
				break;
			case "zh-Hant":
				msg = error.getTChinese();
				break;
			default:
				msg = error.getSChinese();
				break;
		}
		return msg;
    }

	/**
	 * 恒生错误码信息
	 */
	@Data
	public class Error{
		/**
		 * 恒生错误码
		 */
		private String errorCode;

		/**
		 * 错误码简体描述
		 */
		private String sChinese;

		/**
		 * 错误码英文描述
		 */
		private String english;

		/**
		 * 错误码繁体描述
		 */
		private String tChinese;
		public Error(String errorCode,String sChinese,String english,String tChinese){
			this.errorCode = errorCode;
			this.sChinese = sChinese;
			this.english = english;
			this.tChinese = tChinese;
		}
	}
}
