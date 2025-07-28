package com.minigod.zero.trade.afe.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipAndBase64Encode {

    /*public static void main(String[] args) {
		String xmlString = "<REQUEST TYPE=\"CreateCashDeposit\" ID=\" Unique request ID \">" +
			"<ACCOUNTID> Account ID in G3SB </ACCOUNTID>" +
			"<CURRENCYID> HKD </CURRENCYID>" +
			"<SETTLEINTERFACEID> Interface ID returned in section 1.1.8 </SETTLEINTERFACEID>" +
			"<AMOUNT> Amount </AMOUNT>" +
			"<TRANSACTIONREFERENCE> Unique transaction reference (max. 25 characters) </TRANSACTIONREFERENCE>" +
			"<INTERNALREMARK> Internal remark for user reference </INTERNALREMARK>" +
			"<RECEIPTFILE/>" +
			"<RECEIPTFILENAME/>" +
			"<LANGUAGEID>en</LANGUAGEID>" +
			"</REQUEST>";

		xmlString ="<REQUEST Type=\"QueryAccountInformation\" ID=\"2018060100001\">\n" +
			"<ACCOUNTID>123456789</ACCOUNTID>\n" +
			"</REQUEST>";

		xmlString = "<REQUEST Type=\"QueryAccountInformation\" ID=\"2018060100001\"><ACCOUNTID>123456789</ACCOUNTID></REQUEST>";
        // XML字符串
		xmlPkzipBase64(xmlString);
        try {
            zipAndBase64Encode(xmlString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

	private static void xmlPkzipBase64(String xmlString) {


		// 将XML字符串转换为字节数组
		byte[] xmlBytes = xmlString.getBytes();

		// 压缩XML字节数组
		byte[] compressedBytes = compress(xmlBytes);

		// 对压缩后的字节数组进行Base64编码
		String encodedString = Base64.getEncoder().encodeToString(compressedBytes);

		// 输出编码后的字符串
		System.out.println(encodedString);
	}

	private static byte[] compress(byte[] bytesToCompress) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry zipEntry = new ZipEntry("request.xml");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(bytesToCompress);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

	public static String zipAndBase64Encode(String xmlString) throws IOException {
		// Convert XML string to byte array
		byte[] xmlBytes = xmlString.getBytes("UTF-8");

		// Compress the XML bytes using PKZIP standard
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
		ZipEntry zipEntry = new ZipEntry("message.xml");
		zipOutputStream.putNextEntry(zipEntry);
		zipOutputStream.write(xmlBytes);
		zipOutputStream.closeEntry();
		zipOutputStream.close();
		byte[] compressedBytes = byteArrayOutputStream.toByteArray();

		// Encode the compressed bytes with Base64
		String encodedString = Base64.getEncoder().encodeToString(compressedBytes);

		return encodedString;
	}

}
