package com.minigod.zero.dbs.util;

import cn.hutool.core.util.XmlUtil;
import com.minigod.zero.dbs.bo.CamtItemReportResponseVO;
import com.minigod.zero.dbs.bo.CamtReportResponseVO;
import com.minigod.zero.dbs.enums.CamtReportTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbsBankCamtXmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbsBankCamtXmlUtils.class);

    /**
     * Camt053日报文XML解析
     *
     * @param xml
     * @return
     */
    public static CamtReportResponseVO parseCamtXmlToEntity(String xml, int reportType) {

        CamtReportResponseVO camt53ReportResponseVO = new CamtReportResponseVO();

        try {

            Document document = XmlUtil.readXML(xml);
            Object msgId = XmlUtil.getByXPath("//Document/Header/MsgId", document, XPathConstants.STRING);
            Object enqStatus = XmlUtil.getByXPath("//Document/TxnEnqResponse/EnqStatus", document, XPathConstants.STRING);
            Object enqRejectCode = XmlUtil.getByXPath("//Document/TxnEnqResponse/EnqRejectCode", document, XPathConstants.STRING);
            Object enqStatusDescription = XmlUtil.getByXPath("//Document/TxnEnqResponse/EnqStatusDescription", document, XPathConstants.STRING);
            Object accountNo = XmlUtil.getByXPath("//Document/TxnEnqResponse/AcctInfo/AccountNo", document, XPathConstants.STRING);
            Object accountCcy = XmlUtil.getByXPath("//Document/TxnEnqResponse/AcctInfo/AccountCcy", document, XPathConstants.STRING);
            Object bizDate = XmlUtil.getByXPath("//Document/TxnEnqResponse/BizDate", document, XPathConstants.STRING);
            Object messageType = XmlUtil.getByXPath("//Document/TxnEnqResponse/MessageType", document, XPathConstants.STRING);

            // 赋值
            camt53ReportResponseVO.setMsgId(msgId == null ? null : String.valueOf(msgId));
            camt53ReportResponseVO.setEnqStatus(enqStatus == null ? null : String.valueOf(enqStatus));
            camt53ReportResponseVO.setEnqRejectCode(enqRejectCode == null ? null : String.valueOf(enqRejectCode));
            camt53ReportResponseVO.setEnqStatusDescription(enqStatusDescription == null ? null : String.valueOf(enqStatusDescription));
            camt53ReportResponseVO.setAccountNo(accountNo == null ? null : String.valueOf(accountNo));
            camt53ReportResponseVO.setAccountCcy(accountCcy == null ? null : String.valueOf(accountCcy));
            camt53ReportResponseVO.setBizDate(bizDate == null ? null : String.valueOf(bizDate));
            camt53ReportResponseVO.setMessageType(messageType == null ? null : String.valueOf(messageType));

            // 解析明细
            Object statements = XmlUtil.getByXPath("//Document/TxnEnqResponse/Statements/Statement", document, XPathConstants.NODESET);
            NodeList nodeList = null;
            if (null != statements) {
                nodeList = (NodeList) statements;
            }

            if (null != nodeList && nodeList.getLength() > 0) {

                List<CamtItemReportResponseVO> itemReportList = new ArrayList<CamtItemReportResponseVO>(nodeList.getLength());
                for (int i = 0; i < nodeList.getLength(); i++) {

                    Node node = nodeList.item(i);
                    String text = node.getTextContent();
                    if(StringUtils.isEmpty(text)){
                        continue;
                    }

                    // 特殊处理 <![CDATA[ [[>
                    text = text.replaceAll("\\[\\[", "]]");
                    // 去掉命令空间
                    if(reportType == CamtReportTypeEnum.CAMT052.getCode()){
                        text = matcherReplaceAll(text, "xmlns=\"(.+)\">\\s*<BkToCstmrAcctRpt>", "");
                    }else if(reportType == CamtReportTypeEnum.CAMT053.getCode()){
                        text = matcherReplaceAll(text, "xmlns=\"(.+)\">\\s*<BkToCstmrStmt>", "");
                    }

                    // 转化为Dom对象
                    Document document2 = XmlUtil.readXML(text);
                    // 交易统计
                    // 交易数量
                    Object bkToCstmrStmtNode = null;
                    String bkToCstmrStmtNodeName = null;
                    if(reportType == CamtReportTypeEnum.CAMT052.getCode()){
                        bkToCstmrStmtNodeName = "BkToCstmrAcctRpt/Rpt";
                        bkToCstmrStmtNode = XmlUtil.getByXPath("//Document/BkToCstmrAcctRpt", document2, XPathConstants.NODE);
                    }else if(reportType == CamtReportTypeEnum.CAMT053.getCode()){
                        bkToCstmrStmtNodeName = "BkToCstmrStmt/Stmt";
                        bkToCstmrStmtNode = XmlUtil.getByXPath("//Document/BkToCstmrStmt", document2, XPathConstants.NODE);
                    }

                    Object nbOfNtries = XmlUtil.getByXPath("//" + bkToCstmrStmtNodeName + "/TxsSummry/TtlNtries/NbOfNtries", bkToCstmrStmtNode, XPathConstants.STRING);
                    // 总交易量
                    Object txsSummrySum = XmlUtil.getByXPath("//" + bkToCstmrStmtNodeName + "/TxsSummry/TtlNtries/Sum", bkToCstmrStmtNode, XPathConstants.STRING);
                    // 总交易金额
                    Object ttlNetNtryAmt = XmlUtil.getByXPath("//" + bkToCstmrStmtNodeName + "/TxsSummry/TtlNtries/TtlNetNtryAmt", bkToCstmrStmtNode, XPathConstants.STRING);
                    // 总交易方向
                    Object txsCdtDbtInd = XmlUtil.getByXPath("//" + bkToCstmrStmtNodeName + "/TxsSummry/TtlNtries/CdtDbtInd", bkToCstmrStmtNode, XPathConstants.STRING);

                    // 赋值
                    camt53ReportResponseVO.setNbOfNtries(nbOfNtries == null ? null : String.valueOf(nbOfNtries));
                    camt53ReportResponseVO.setTxsSummrySum(txsSummrySum == null ? null : String.valueOf(txsSummrySum));
                    camt53ReportResponseVO.setTtlNetNtryAmt(ttlNetNtryAmt == null ? null : String.valueOf(ttlNetNtryAmt));
                    camt53ReportResponseVO.setCdtDbtInd(txsCdtDbtInd == null ? null : String.valueOf(txsCdtDbtInd));

                    // 交易明细
                    Object NtryList = XmlUtil.getByXPath("//Document/" + bkToCstmrStmtNodeName + "/Ntry", document2, XPathConstants.NODESET);
                    NodeList nodeList2 = null;
                    if(null != NtryList){
                        nodeList2 = (NodeList) NtryList;
                    }

                    if(null == nodeList2){
                        continue;
                    }

                    for (int j = 0; j < nodeList2.getLength(); j++) {

                        // 交易明细
                        CamtItemReportResponseVO itemReportVO = new CamtItemReportResponseVO();

                        Node ntryNode2 = nodeList2.item(j);
                        Object cdtDbtInd = XmlUtil.getByXPath("CdtDbtInd", ntryNode2, XPathConstants.STRING);
                        Object status = XmlUtil.getByXPath("Sts", ntryNode2, XPathConstants.STRING);
                        Object bookingDate = XmlUtil.getByXPath("BookgDt/DtTm", ntryNode2, XPathConstants.STRING);
                        Object valueDate = XmlUtil.getByXPath("ValDt/Dt", ntryNode2, XPathConstants.STRING);
                        Object acctSvcrRef = XmlUtil.getByXPath("AcctSvcrRef", ntryNode2, XPathConstants.STRING);

                        Object amt = XmlUtil.getByXPath("Amt", ntryNode2, XPathConstants.NODE);
                        String ccy = null;
                        String amtAmount = null;
                        if (null != amt) {
                            Node amtNode = (Node) amt;
                            ccy = amtNode.getAttributes().getNamedItem("Ccy").getNodeValue();
                            amtAmount = amtNode.getTextContent();
                        }
                        // 交易详情
                        // 交易流水
                        Object refsNode = XmlUtil.getByXPath("NtryDtls/TxDtls/Refs", ntryNode2, XPathConstants.NODE);
                        Object endToEndId = XmlUtil.getByXPath("EndToEndId", refsNode, XPathConstants.STRING);
                        Object mndtId = XmlUtil.getByXPath("MndtId", refsNode, XPathConstants.STRING);
                        Object chqNb = XmlUtil.getByXPath("ChqNb", refsNode, XPathConstants.STRING);

                        // 换汇信息
                        Object instructedAmount = null;
                        Object instructedCcy = null;
                        Object srcCcy = null;
                        Object trgtCcy = null;
                        Object xchgRate = null;
                        Object ctrctId = null;

                        Object amtDtlsNode = XmlUtil.getByXPath("NtryDtls/AmtDtls", ntryNode2, XPathConstants.NODE);
                        if(null != amtDtlsNode){
                            Object instructedAmountNodeObj = XmlUtil.getByXPath("InstdAmt/Amt", amtDtlsNode, XPathConstants.NODE);
                            if(null != instructedAmountNodeObj){
                                Node instructedAmountNode = (Node)instructedAmountNodeObj;
                                instructedAmount = instructedAmountNode.getTextContent();
                                instructedCcy = instructedAmountNode.getAttributes().getNamedItem("Ccy").getNodeValue();
                            }
                            srcCcy = XmlUtil.getByXPath("CcyXchg/SrcCcy", amtDtlsNode, XPathConstants.STRING);
                            trgtCcy = XmlUtil.getByXPath("CcyXchg/TrgtCcy", amtDtlsNode, XPathConstants.STRING);
                            xchgRate = XmlUtil.getByXPath("CcyXchg/XchgRate", amtDtlsNode, XPathConstants.STRING);
                            ctrctId = XmlUtil.getByXPath("CcyXchg/CtrctId", amtDtlsNode, XPathConstants.STRING);
                        }

                        // 交易双方账户
                        Object rltdPties = XmlUtil.getByXPath("NtryDtls/TxDtls/RltdPties", ntryNode2, XPathConstants.NODE);
                        // debtor账户
                        Object debtorNm = null;
                        Object debtorCtry = null;
                        Object dbtrAcctIdIban = null;
                        Object dbtrAcctIdOtherId = null;
                        Node dbtrNode = (Node) XmlUtil.getByXPath("Dbtr", rltdPties, XPathConstants.NODE);
                        if(null != dbtrNode){
                            debtorNm = XmlUtil.getByXPath("Nm", dbtrNode, XPathConstants.STRING);
                            debtorCtry = XmlUtil.getByXPath("PstlAdr/Ctry", dbtrNode, XPathConstants.STRING);

                            Node dbtrAcctNode = (Node) XmlUtil.getByXPath("DbtrAcct", rltdPties, XPathConstants.NODE);
                            if(null != dbtrAcctNode){
                                dbtrAcctIdIban = XmlUtil.getByXPath("Id/IBAN", dbtrAcctNode, XPathConstants.STRING);
                                dbtrAcctIdOtherId = XmlUtil.getByXPath("Id/Othr/Id", dbtrAcctNode, XPathConstants.STRING);
                            }
                        }

                        // Creditor账户
                        Object creditorNm = null;
                        Object creditorCtry = null;
                        Object cdtrAcctIdIban = null;
                        Object cdtrAcctIdOtherId = null;
                        Node cdtrNode = (Node) XmlUtil.getByXPath("Cdtr", rltdPties, XPathConstants.NODE);
                        if(null != cdtrNode){
                            creditorNm = XmlUtil.getByXPath("Nm", cdtrNode, XPathConstants.STRING);
                            creditorCtry = XmlUtil.getByXPath("PstlAdr/Ctry", cdtrNode, XPathConstants.STRING);

                            Node cdtrAcctNode = (Node) XmlUtil.getByXPath("CdtrAcct", rltdPties, XPathConstants.NODE);
                            if(null != cdtrAcctNode){
                                cdtrAcctIdIban = XmlUtil.getByXPath("Id/IBAN", cdtrAcctNode, XPathConstants.STRING);
                                cdtrAcctIdOtherId = XmlUtil.getByXPath("Id/Othr/Id", cdtrAcctNode, XPathConstants.STRING);
                            }
                        }

                        // 附加实体信息
                        Object addtlNtryInf = XmlUtil.getByXPath("AddtlNtryInf", ntryNode2, XPathConstants.STRING);

                        // 添加明细
                        itemReportVO.setCreditDebitIndicator(cdtDbtInd == null ? null : String.valueOf(cdtDbtInd));
                        itemReportVO.setStatus(status == null ? null : String.valueOf(status));
                        itemReportVO.setBookingDate(bookingDate == null ? null : String.valueOf(bookingDate));
                        itemReportVO.setBookingDate(valueDate == null ? null : String.valueOf(valueDate));
                        itemReportVO.setAccountServicerReference(acctSvcrRef == null ? null : String.valueOf(acctSvcrRef));

                        itemReportVO.setAmount(amtAmount == null ? null : String.valueOf(amtAmount));
                        itemReportVO.setCcy(ccy == null ? null : String.valueOf(ccy));
                        itemReportVO.setEndToEndIdentification(endToEndId == null ? null : String.valueOf(endToEndId));
                        itemReportVO.setMandateIdentification(mndtId == null ? null : String.valueOf(mndtId));
                        itemReportVO.setChequeNumber(chqNb == null ? null : String.valueOf(chqNb));

                        // 换汇信息
                        itemReportVO.setInstructedAmount(instructedAmount == null ? null : String.valueOf(instructedAmount));
                        itemReportVO.setInstructedCcy(instructedCcy == null ? null : String.valueOf(instructedCcy));
                        itemReportVO.setSourceCurrency(srcCcy == null ? null : String.valueOf(srcCcy));
                        itemReportVO.setTargetCurrency(trgtCcy == null ? null : String.valueOf(trgtCcy));
                        itemReportVO.setExchangeRate(xchgRate == null ? null : String.valueOf(xchgRate));
                        itemReportVO.setContractIdentification(ctrctId == null ? null : String.valueOf(ctrctId));

                        // 交易双方账户
                        itemReportVO.setDebtorName(debtorNm == null ? null : String.valueOf(debtorNm));
                        itemReportVO.setDebtorCountry(debtorCtry == null ? null : String.valueOf(debtorCtry));
                        itemReportVO.setDebtorIbanNO(dbtrAcctIdIban == null ? null : String.valueOf(dbtrAcctIdIban));
                        itemReportVO.setDebtorAccountNo(dbtrAcctIdOtherId == null ? null : String.valueOf(dbtrAcctIdOtherId));

                        itemReportVO.setCreditorName(creditorNm == null ? null : String.valueOf(creditorNm));
                        itemReportVO.setCreditorCountry(creditorCtry == null ? null : String.valueOf(creditorCtry));
                        itemReportVO.setCreditorIbanNO(cdtrAcctIdIban == null ? null : String.valueOf(cdtrAcctIdIban));
                        itemReportVO.setCreditorAccountNo(cdtrAcctIdOtherId == null ? null : String.valueOf(cdtrAcctIdOtherId));

                        // 附加信息
                        itemReportVO.setAdditionalEntryInformation(addtlNtryInf == null ? null : String.valueOf(addtlNtryInf));

                        // 添加到List
                        itemReportList.add(itemReportVO);
                    }
                }

                // 设置交易明细
                camt53ReportResponseVO.setItems(itemReportList);
            }

        } catch (Exception e) {
            camt53ReportResponseVO = null;
            LOGGER.error("DbsBankCamtXmlUtils.parseCamt53XmlToEntity error, data: {}", xml, e);
        }

        return camt53ReportResponseVO;
    }

    private static String matcherReplaceAll(String data, String pattern, String replace) {
        StringBuffer buffer = new StringBuffer();
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(data);
        String content = data;
        while (m.find()) {
            content = content.replaceAll(m.group(1), replace);
        }
        return content;
    }


}
