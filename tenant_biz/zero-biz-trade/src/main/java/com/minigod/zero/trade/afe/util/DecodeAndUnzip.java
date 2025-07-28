package com.minigod.zero.trade.afe.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DecodeAndUnzip {

    public static String decodeAndUnzip(String encodedString) throws IOException {
        // Base64 解码
        byte[] compressedBytes = Base64.getDecoder().decode(encodedString);

        // 使用 ZipInputStream 解压缩
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedBytes);
        ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);

        ZipEntry zipEntry = zipInputStream.getNextEntry();
        if (zipEntry == null) {
            throw new IOException("No ZIP entry found in the input stream.");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = zipInputStream.read(buffer)) > 0) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        zipInputStream.closeEntry();
        zipInputStream.close();

        // 将解压缩的字节转换为 XML 字符串
        byte[] xmlBytes = byteArrayOutputStream.toByteArray();
        String xmlString = new String(xmlBytes, "UTF-8");

        return xmlString;
    }

    /*public static void main(String[] args) {
        try {
            // 假设这是你的加密后内容（Base64编码的压缩数据）
            //String encodedString = "UEsDBBQACAgIAB1c5EwAAAAAAAAAAAAAAAAHAAAAYWRhLnR4dLMJcg0MdQ0OUQipLEi1VQosTS2qdExOzi/NK/HMS8svyk0syczPU1LwdLFVMjIwtDAwMzA0AAJDJTsbR2dn/1C/EE8XO0MjYxNTM3MLSxt9hKCNPtRsOwBQSwcIE9WZZ1gAAABlAAAAUEsBAhQAFAAICAgAHVzkTBPVmWdYAAAAZQAAAAcAAAAAAAAAAAAAAAAAAAAAAGFkYS50eHRQSwUGAAAAAAEAAQA1AAAAjQAAAAAA";
			*//*String encodedString =  "UEsDBBQACAAIAAldDU0AAAAAAAAAAAAAAAAHAAAAYWRhLnR4dF1Sy27CMBD8FYtzwXFCHqDFarANtZSXYqcqR1oQQgIiFfr/3dDGQfhie2Y069k11MpUZWEU0XIx8j2WeJHHPFxsxAHJJrPdLspackiFKJvCasmZH0zDKE5mQAcQRKbVM+0wEI2xpdw80Q7s3e2mUnj9cM7/ABRprni6UqTYnveEAb0DIFOrytVS1/aNs1kSjj029kKgjzhoWTT5UtUcgw3FHYq8NqZRVZYKxd+1sgTNO8ED3Is6Y46t8u6VmFPdcUilxLYZxuu2PRP2Qlantv0mU4zTM73G5+vALMny53jaHS8Hp/CdIkCX7Y5gaEcGjpxyB+LR5OY/TBJH4TTw8WEDBipPdWYsPjHHcfDrbXvbn/eX2+shuH5Ovtoz0CcJ0H7sdPgHtqkLUUrMaRohsDIHq3NFuhktRt0RDfJq1PUn/psEYcE8jOd+NEkCD2inwQj1uumqGIr2zoo+FqD91+S/UEsHCGB4+I9hAQAApQIAAFBLAQIUABQACAAIAAldDU1gePiPYQEAAKUCAAAHAAAAAAAAAAAAAAAAAAAAAABhZGEudHh0UEsFBgAAAAABAAEANQAAAJYBAAAAAA==";
            String encodedString2 = "UEsDBBQACAgIAE+HjFgAAAAAAAAAAAAAAAALAAAAbWVzc2FnZS54bWyzCXINDHUNDlEIqSxItVUKLE0tqnRMTs4vzSvxzEvLL8pNLMnMz1NS8HSxVTIyMLQwMDMwNAACQyU7G0dnZ/9QvxBPFztDI2MTUzNzC0sbfYSgjT7UbDsAUEsHCBPVmWdYAAAAZQAAAFBLAQIUABQACAgIAE+HjFgT1ZlnWAAAAGUAAAALAAAAAAAAAAAAAAAAAAAAAABtZXNzYWdlLnhtbFBLBQYAAAAAAQABADkAAACRAAAAAAA=";
            String encodedString3 = "UEsDBBQACAgIAAyIjFgAAAAAAAAAAAAAAAALAAAAcmVxdWVzdC54bWyzCXINDHUNDlEIqSxItVUKLE0tqnRMTs4vzSvxzEvLL8pNLMnMz1NS8HSxVTIyMLQwMDMwNAACQyU7G0dnZ/9QvxBPFztDI2MTUzNzC0sbfYSgjT7UbDsAUEsHCBPVmWdYAAAAZQAAAFBLAQIUABQACAgIAAyIjFgT1ZlnWAAAAGUAAAALAAAAAAAAAAAAAAAAAAAAAAByZXF1ZXN0LnhtbFBLBQYAAAAAAQABADkAAACRAAAAAAA=";
            String encodedString3 = "UEsDBBQACAgIADNwllgAAAAAAAAAAAAAAAAIAAAAbnVsbC50eHSzCXINDvD3C3a14+WyCXINCQ3yc/Z3AfNcg4L8g+JD/bz9/MP9QAIhnr6uCiGRAa62SiBmcIijb4CSnZGBkYmugYmukZGCoYmVgaGVsaWeuam5jT5IDUibY5B7qK+rX0iwnY0+gg2U0MewQR/VBfpIjgMAUEsHCFMDqeFzAAAAqQAAAFBLAQIUABQACAgIADNwllhTA6nhcwAAAKkAAAAIAAAAAAAAAAAAAAAAAAAAAABudWxsLnR4dFBLBQYAAAAAAQABADYAAACpAAAAAAA=";

            // 解码和解压缩
            String xmlString = decodeAndUnzip(encodedString);
            String xmlString2 = decodeAndUnzip(encodedString2);
            String xmlString3 = decodeAndUnzip(encodedString3);

            // 输出原始的 XML
            System.out.println(xmlString);
            System.out.println(xmlString2);
            System.out.println(xmlString3);*//*


			String encodedString4 = "UEsDBBQACAgIADNwllgAAAAAAAAAAAAAAAAIAAAAbnVsbC50eHSzCXINDvD3C3a14+WyCXINCQ3yc/Z3AfNcg4L8g+JD/bz9/MP9QAIhnr6uCiGRAa62SiBmcIijb4CSnZGBkYmugYmukZGCoYmVgaGVsaWeuam5jT5IDUibY5B7qK+rX0iwnY0+gg2U0MewQR/VBfpIjgMAUEsHCFMDqeFzAAAAqQAAAFBLAQIUABQACAgIADNwllhTA6nhcwAAAKkAAAAIAAAAAAAAAAAAAAAAAAAAAABudWxsLnR4dFBLBQYAAAAAAQABADYAAACpAAAAAAA=";
			 encodedString4 = "UEsDBBQACAAIAHeR+0wAAAAAAAAAAAAAAAAHAAAAYWRhLnR4dF2SUW+CMBSF/0rD87QtiKK5NoO2uiYChpZlPrppjIlKMt3" +
				 "/38WNSuSF5jsn5/beW6i0XZeF1cSoeRAynrAx4wy/MBCAYr1y7V+WlRKQSlnWhTNK8DAaxeNJMgX6gCBXRj/LnoGsrSvV5kn2EIo01yJdaFJ" +
				 "sz3vCgd7BHeuPhxD+CYhApU6Xi8xU7k3waRIPGB+wGGifg1FFnWe6Eoz16nqKurG21utVKrV4N9oRjG8NPdyZ2mCBU2L3Sty77hxSpXBilou" +
				 "qac6Ev5DFqWm+yQhn1CmdJxTLyGYk+zmedsfLwTtC74gwZbsj2LYXIy+OhId4tLn9byaZjONRFOLFHgx0npqVdXjFHDchrrftbX/eX26vh+j" +
				 "6OfxqzkCfLEC7jdPHE3B1VchSYZ+2lhIrC3Am18Rt1noetEcMyNdBO5/J3yYIj2bxZBaOh0nEgLYebKFa1m0VSzHeR9F+Adq9SvELUEsHCCS" +
				 "gNFRhAQAAoAIAAFBLAQIUABQACAAIAHeR+0wkoDRUYQEAAKACAAAHAAAAAAAAAAAAAAAAAAAAAABhZGEudHh0UEsFBgAAAAABAAEANQAAAJY" +
				 "BAAAAAA==";

			encodedString4 = "UEsDBBQACAAIAGqO+0wAAAAAAAAAAAAAAAAHAAAAYWRhLnR4dLMJcg0MdQ0OUQipLEi1VQosTS2qdExOzi/NK/HMS8svyk0" +
				"syczPU1LwdLFVMjIwtDAwMzA0AAJDJTsbR2dn/1C/EE8XO0MjYxNTM3MLSxt9hKCNPtRsOwBQSwcIE9WZZ1gAAABlAAAAUEsBAhQAFAAIAAg" +
				"Aao77TBPVmWdYAAAAZQAAAAcAAAAAAAAAAAAAAAAAAAAAAGFkYS50eHRQSwUGAAAAAAEAAQA1AAAAjQAAAAAA";
			encodedString4 = "OSm65volbE5pqnLmmjH0bmbjl3Kiz3+OMjUzgYZv+CWE015QL0vMkueiPRfKyuCPJJy7AdPMPn5O" +
				"KAIb29ImPbGLYBxPjMJi7pWKch6qTqjDxsEu4aK7lzRbTqi/K6qTWNxDyepMIB7IN4fv10TPypZi" +
				"5SXCfb1zs1CNVGA2zWg=";

			encodedString4 = "UEsDBBQACAAIAHeR+0wAAAAAAAAAAAAAAAAHAAAAYWRhLnR4dF2SUW+CMBSF/0rD87QtiKK5NoO2uiYChpZlPrppjIlKMt3" +
				"/38WNSuSF5jsn5/beW6i0XZeF1cSoeRAynrAx4wy/MBCAYr1y7V+WlRKQSlnWhTNK8DAaxeNJMgX6gCBXRj/LnoGsrSvV5kn2EIo01yJdaFJ" +
				"sz3vCgd7BHeuPhxD+CYhApU6Xi8xU7k3waRIPGB+wGGifg1FFnWe6Eoz16nqKurG21utVKrV4N9oRjG8NPdyZ2mCBU2L3Sty77hxSpXBilou" +
				"qac6Ev5DFqWm+yQhn1CmdJxTLyGYk+zmedsfLwTtC74gwZbsj2LYXIy+OhId4tLn9byaZjONRFOLFHgx0npqVdXjFHDchrrftbX/eX26vh+j" +
				"6OfxqzkCfLEC7jdPHE3B1VchSYZ+2lhIrC3Am18Rt1noetEcMyNdBO5/J3yYIj2bxZBaOh0nEgLYebKFa1m0VSzHeR9F+Adq9SvELUEsHCCS" +
				"gNFRhAQAAoAIAAFBLAQIUABQACAAIAHeR+0wkoDRUYQEAAKACAAAHAAAAAAAAAAAAAAAAAAAAAABhZGEudHh0UEsFBgAAAAABAAEANQAAAJY" +
				"BAAAAAA==";
			String xmlString4 = decodeAndUnzip(encodedString4);

			System.out.println(xmlString4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
