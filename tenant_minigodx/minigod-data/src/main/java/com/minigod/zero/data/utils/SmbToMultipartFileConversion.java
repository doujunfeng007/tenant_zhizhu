package com.minigod.zero.data.utils;

import jcifs.smb1.smb1.SmbFile;
import jcifs.smb1.smb1.SmbFileInputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: wengzejie
 * @createTime: 2025/01/02 18:50
 * @description:
 */
public class SmbToMultipartFileConversion {
	public static CommonsMultipartFile convertSmbFileToMultipartFile(SmbFile smbFile) throws IOException {
		String fileName = smbFile.getName();
		InputStream inputStream = new SmbFileInputStream(smbFile);
		// 创建 DiskFileItem 实例
		FileItem fileItem = new DiskFileItem(fileName, "application/octet-stream", false, fileName, (int) smbFile.length(), null);
		// 将文件内容写入 FileItem
		fileItem.getOutputStream().write(inputStream.readAllBytes());
		inputStream.close();
		// 创建 CommonsMultipartFile 实例
		CommonsMultipartFile multipartFile = new org.springframework.web.multipart.commons.CommonsMultipartFile(fileItem);
		return multipartFile;
	}
}
