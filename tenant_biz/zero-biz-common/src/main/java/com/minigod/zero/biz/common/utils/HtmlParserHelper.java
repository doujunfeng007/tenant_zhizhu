package com.minigod.zero.biz.common.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.htmlparser.*;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by sunline on 2016/8/23 14:04.
 * sunline
 */
public class HtmlParserHelper {

	public static final Logger logger = LoggerFactory.getLogger(HtmlParserHelper.class);

    public static class StringRow {
    	Map<Integer, String> colValues = new HashMap<>();
    	/**
    	 * 返回colIndex列的数据
    	 * @param colIndex zero-based
    	 * @return
    	 */
    	public String getCol(Integer colIndex) {
    		return colValues.get(colIndex);
    	}
    	public void setCol(Integer colIndex, String val) {
    		colValues.put(colIndex, val);
    	}
    }

    /**
     * 定位带指定属性的表格，读取表格内容
     * @param html
     * @param offsetRows
     * @param tailRows
     * @param charSet
     * @param tableFilter
     * @return
     * @throws Exception
     */
    public static List<StringRow> readHtmlTable(String html,
                                                            int offsetRows, int tailRows, String charSet,
                                                            final Map<String, String> tableFilter) throws Exception {
        List<StringRow> rtnList = new ArrayList<>();
    	html = html.replaceAll("<tbody>", "").replaceAll("</tbody>", "").replaceAll("<b>","").replaceAll("</b>","");
    	//
        logger.debug("开始解析Html数据表结构");
        Parser parser = new Parser();
        parser.setEncoding(charSet);
        parser.setInputHTML(html);
        NodeList tables = parser.parse(new NodeFilter() {
            @Override
            public boolean accept(Node node) {
                boolean isValid = false;
                if (node instanceof TableTag) {
                    TableTag tableTag = (TableTag) node;
                    if (MapUtils.isNotEmpty(tableFilter)) {
                        Set<Map.Entry<String, String>> entrySet = tableFilter.entrySet();
                        Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                        Map.Entry<String, String> entry;
                        while (it.hasNext()) {
                            entry = it.next();
                            if (tableTag.getAttribute(entry.getKey()) != null)
                                if ((tableTag.getAttribute(entry.getKey()).equals(entry.getValue())))
                                    isValid = true;
                        }
                    } else {
                        isValid = true;
                    }
                }
                return isValid;
            }
        });

        for (int i = 0; i < tables.size(); i++) {
            TableTag table = (TableTag) tables.elementAt(i);
            TableRow[] rows = table.getRows();
            int columnLen = rows[0].getColumnCount();
            if (columnLen <= 0) {
//            	columnLen = rows[0].getColumns().length;
            	columnLen = rows[0].getChildCount();
            }

            // 遍历表格的行
            for (int rowId = offsetRows; rowId < rows.length - tailRows; rowId++) {
                TableRow row = rows[rowId];
                TableColumn[] columns = row.getColumns();
                int colSize = columnLen;

                // 封装一行的值
                StringRow strRow = new StringRow();
                for (int col = 0; col < colSize; col++) {
                	TableColumn column = columns[col];
                	String colText = HtmlParserHelper.getText(column);
                	strRow.setCol(col, colText);
                }
                rtnList.add(strRow);
            }
        }
        logger.debug("解析Html数据表结构完成，总共获取数据条数：" + rtnList.size());
        return rtnList;
    }

    /**
     * 定位带指定属性的表格，读取表格内容
     * v2:
     * 头部兼容th标签
     * 兼容有合并的行
     * @param html
     * @param offsetRows
     * @param tailRows
     * @param charSet
     * @param tableFilter
     * @return
     * @throws Exception
     */
    public static List<StringRow> readHtmlTableV2(String html,
                                                            int offsetRows, int tailRows, String charSet,
                                                            final Map<String, String> tableFilter) throws Exception {
        List<StringRow> rtnList = new ArrayList<>();
    	html = html.replaceAll("<tbody>", "").replaceAll("</tbody>", "").replaceAll("<b>","").replaceAll("</b>","");
    	//
        logger.debug("开始解析Html数据表结构");
        Parser parser = new Parser();
        parser.setEncoding(charSet);
        parser.setInputHTML(html);
        NodeList tables = parser.parse(new NodeFilter() {
            @Override
            public boolean accept(Node node) {
                boolean isValid = false;
                if (node instanceof TableTag) {
                    TableTag tableTag = (TableTag) node;
                    if (MapUtils.isNotEmpty(tableFilter)) {
                        Set<Map.Entry<String, String>> entrySet = tableFilter.entrySet();
                        Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                        Map.Entry<String, String> entry;
                        while (it.hasNext()) {
                            entry = it.next();
                            if (tableTag.getAttribute(entry.getKey()) != null)
                                if ((tableTag.getAttribute(entry.getKey()).equals(entry.getValue())))
                                    isValid = true;
                        }
                    } else {
                        isValid = true;
                    }
                }
                return isValid;
            }
        });

        for (int i = 0; i < tables.size(); i++) {
            TableTag table = (TableTag) tables.elementAt(i);
            TableRow[] rows = table.getRows();
            int columnLen = rows[0].getColumnCount();
            if (columnLen <= 0) {
            	columnLen = countChildNode(rows[0], "th");
            }

            // 遍历表格的行
            for (int rowId = offsetRows; rowId < rows.length - tailRows; rowId++) {
                TableRow row = rows[rowId];
                TableColumn[] columns = row.getColumns();
                int colSize = columnLen;

                // 封装一行的值
                StringRow strRow = new StringRow();
                for (int col = 0; col < colSize && col < columns.length; col++) {
                	TableColumn column = columns[col];
                	String colText = HtmlParserHelper.getText(column);
                	strRow.setCol(col, colText);
                }
                rtnList.add(strRow);
            }
        }
        logger.debug("解析Html数据表结构完成，总共获取数据条数：" + rtnList.size());
        return rtnList;
    }

    private static int countChildNode(CompositeTag parent, String childTag) {
    	childTag = childTag.toUpperCase();
    	int count = 0;
    	for (int i = 0; i < parent.getChildCount(); i++) {
    		Node node = parent.getChild(i);
    		if (node instanceof TagNode) {
    			String tagName = ((TagNode)node).getTagName();
    			if (tagName != null) {
    				tagName = tagName.toUpperCase();
    				if (tagName.equals(childTag)) {
    					count++;
    				}
    			}
    		}
    	}
		return count;
	}

	public static <T extends TagNode>  T removeStyleAttrForSingleNode(T tagNode){
        if(tagNode instanceof TagNode){
            tagNode.removeAttribute("style");
        }
        return tagNode;
    }

    public static <T extends TagNode> T removeCssClassAttrForSingleNode(T tagNode){
        if(tagNode instanceof TagNode){
            tagNode.removeAttribute("class");
        }
        return tagNode;
    }


    public static <T extends TagNode> T removeAttriesForSingleNode(T tagNode, Set<String> exceptAttries){
        if(null != tagNode && tagNode instanceof TagNode) {
            Vector attries = tagNode.getAttributesEx();
            Attribute attribute;
            String name ;
            for(int i = 1 ; i < attries.size();i++){
                attribute = (Attribute)attries.get(i);
                name = attribute.getName();
                if(null != name){
                    if(null != exceptAttries && !exceptAttries.contains(name)){
                        tagNode.removeAttribute(name);
                    }
                }
            }
        }
        return tagNode;
    }

    public static <T extends CompositeTag> String formatContent(T tagNode) {
        StringBuilder strBuilder = new StringBuilder();
        NodeList newNodeList = new NodeList();
        Set<String> keepAttries = new HashSet<>();
        keepAttries.add("src");
        keepAttries.add("href");
        if(null != tagNode){
            NodeList nodeList = tagNode.getChildren();
            if(null != nodeList){
                Node node;
                for(int i = 0 ; i < nodeList.size(); i ++){
                    node = nodeList.elementAt(i);
                    if(node instanceof CompositeTag){
                        formatContent((CompositeTag) node);
                    }
                    if(node instanceof TagNode){
                        if(!(node instanceof StyleTag) && !(node instanceof ScriptTag)){
                            node = removeAttriesForSingleNode((TagNode)node,keepAttries);
                            strBuilder.append(node.toHtml());
                        }
                    }
                    else{
                        strBuilder.append(node.toHtml());
                    }
                }
            }

        }
        return strBuilder.toString();
    }

    public static NodeList getNodes(String strUrl, String charSet, NodeFilter filter){
        if (StringUtils.isNotEmpty(strUrl)) {
            try {
                //创建HttpClientBuilder
                HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
                //HttpClient
                CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
                InputStream is = UrlReader.readUrl(closeableHttpClient, strUrl);
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(is, charSet));
                StringBuffer strBuff = new StringBuffer();
                String line;
                while ((line = buffReader.readLine()) != null) {
                    strBuff.append(line.replaceAll("<tbody>", "").replaceAll("</tbody>", "").replaceAll("<b>","").replaceAll("</b>",""));
                }
                logger.debug("读取HTML数据完成，开始解析Html数据表结构");
                Parser parser = new Parser();
                parser.setEncoding(charSet);
                parser.setInputHTML(strBuff.toString());
                closeableHttpClient.close();
                return parser.extractAllNodesThatMatch(filter);
            }catch (Exception e) {
                logger.error("读取URL失败！",e);
            }
        }
        return null;
    }

    public static Parser getParser(String strUrl, String charSet){
        if (StringUtils.isNotEmpty(strUrl)) {
            try {
                //创建HttpClientBuilder
                HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
                //HttpClient
                CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
                InputStream is = UrlReader.readNewsUrl(closeableHttpClient, strUrl);
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(is, charSet));
                StringBuffer strBuff = new StringBuffer();
                String line;
                while ((line = buffReader.readLine()) != null) {
                    strBuff.append(line.replaceAll("<tbody>", "").replaceAll("</tbody>", "").replaceAll("<b>","").replaceAll("</b>",""));
                }
                logger.debug("读取HTML数据完成，开始解析Html数据表结构");
                Parser parser = new Parser();
                parser.setEncoding(charSet);
                parser.setInputHTML(strBuff.toString());
                closeableHttpClient.close();
                return parser;
            }catch (Exception e) {
                logger.error("读取URL失败！"+strUrl,e);
            }
        }
        return null;
    }

    public static NodeList getNodeList(Parser parser, final Map<String,String> filterS , final Class<? extends TagNode> tagClazz){
        if (parser != null) {
            try {
                return parser.parse(new NodeFilter() {
                    @Override
                    public boolean accept(Node node) {
                        if (node.getClass().getSimpleName().equals(tagClazz.getSimpleName())) {
                            TagNode tagNode = (TagNode) node;

                            if (MapUtils.isNotEmpty(filterS)) {
                                Set<Map.Entry<String, String>> entrySet = filterS.entrySet();
                                Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                                Map.Entry<String, String> entry;
                                while (it.hasNext()) {
                                    entry = it.next();
                                    if (tagNode.getAttribute(entry.getKey()) != null)
                                        if (entry.getValue().equals(tagNode.getAttribute(entry.getKey())))
                                            return true;
                                }
                            } else {
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }catch (Exception e) {
                logger.error("读取URL失败！",e);
            }
        }
        return null;
    }

    public static String getContent(NodeList nodeList, Set<String> keepAttries){
        StringBuilder stringBuilder = new StringBuilder();
        if(null != nodeList){
            Node node;
            NodeList childNodeList;
            String tagName;
            for(int i = 0; i < nodeList.size(); i ++){
                node = nodeList.elementAt(i);
                childNodeList = node.getChildren();
                if(null == childNodeList ){
                    if(node instanceof TagNode){
                        node =  removeAttriesForSingleNode((TagNode)node,keepAttries);
                    }
                    stringBuilder.append(node.toHtml());
                }else{
                    if(node instanceof Tag){
                        tagName = ((Tag) node).getTagName();
                        stringBuilder.append("<").append(tagName).append(">").append(getContent(node.getChildren(), keepAttries))
                                .append("</").append(tagName).append(">");
                    }else{
                        stringBuilder.append(getContent(node.getChildren(), keepAttries));
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static <T extends CompositeTag> T removeScriptTag(T compositeTagNode){
        if(null != compositeTagNode){
            if(compositeTagNode instanceof ScriptTag){
                return null;
            }
            else{
                NodeList nodeList = compositeTagNode.getChildren();
                if(null != nodeList){
                    Node node;
                    for(int i = 0 ; i < nodeList.size(); i++){
                        node = nodeList.elementAt(i);
                        if(node instanceof CompositeTag){
                            if(removeScriptTag((CompositeTag)node) == null){
                                compositeTagNode.removeChild(i);
                            }
                        }
                    }
                }
            }
        }
        return compositeTagNode;
    }

    public static <T extends TagNode> T getFirstNode(Parser parser, final Map<String,String> filterS , final Class<T> tagClazz){
        T node = null;
        if (null != parser) {
            try {
                NodeList nodeList =  parser.parse(new NodeFilter() {
                    @Override
                    public boolean accept(Node node) {
                        if (node.getClass().getSimpleName().equals(tagClazz.getSimpleName())) {
                            TagNode tagNode = (TagNode) node;
                            if (MapUtils.isNotEmpty(filterS)) {
                                Set<Map.Entry<String, String>> entrySet = filterS.entrySet();
                                Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                                Map.Entry<String, String> entry;
                                while (it.hasNext()) {
                                    entry = it.next();
                                    if (tagNode.getAttribute(entry.getKey()) != null)
                                        if (entry.getValue().equals(tagNode.getAttribute(entry.getKey())))
                                            return true;
                                }
                            } else {
                                return true;
                            }
                        }
                        return false;
                    }
                });
                if(nodeList != null){
                    node = (T) nodeList.elementAt(0);
                }
            }catch (Exception e) {
                logger.error("获取Tag失败！",e);
            }
        }
        return node;
    }

    public static  String getText(Node node){
        StringBuilder sb = new StringBuilder();
        if(null != node){
            if (node instanceof TextNode) {
                sb.append(node.getText().replaceAll("&nbsp;", "").replaceAll("&amp;", "&"));
            }else{
                NodeList list = node.getChildren();
                if(null != list){
                    for(int i = 0 ;i < list.size(); i ++){
                        sb.append(getText(list.elementAt(i)));
                    }
                }

            }
        }
        return sb.toString();
    }

    public static <T extends Node> List<T> getNode(Node parentNode, final Class<T> childNode, Map<String,String> filterMap, List<T> rtnTags){
        if (parentNode.getClass().getSimpleName().equals(childNode.getSimpleName()) && isAccept(parentNode,filterMap)) {
            rtnTags.add((T)parentNode);
        }else{
            NodeList list = parentNode.getChildren();
            if(null != list){
                for(int i = 0 ;i < list.size(); i ++){
                    getNode(list.elementAt(i), childNode, filterMap,rtnTags);
                }
            }
        }
        return rtnTags;
    }

    public static <T extends Node> T getFirstNode(Node parentNode, final Class<T> childNode, Map<String,String> filterMap){
        List<T> list = new ArrayList<>();
        list = getNode(parentNode,childNode,filterMap,list);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    private static boolean isAccept(Node node, Map<String,String> filterMap){
        boolean isValid = false;
        if (MapUtils.isNotEmpty(filterMap)) {
            if( node instanceof TagNode){
                TagNode tagNode = (TagNode) node;

                Set<Map.Entry<String, String>> entrySet = filterMap.entrySet();
                Iterator<Map.Entry<String, String>> it = entrySet.iterator();
                Map.Entry<String, String> entry;
                while (it.hasNext()) {
                    entry = it.next();
                    if (tagNode.getAttribute(entry.getKey()) != null && entry.getValue().equals(tagNode.getAttribute(entry.getKey())))
                        isValid = true;
                    else{
                        isValid = false;
                        break;
                    }
                }
            } else {
                isValid = false;
            }
        }else{
            isValid = true;
        }
        return isValid;
    }
}
