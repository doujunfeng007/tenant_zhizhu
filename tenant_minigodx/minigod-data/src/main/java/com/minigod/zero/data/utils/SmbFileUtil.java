package com.minigod.zero.data.utils;

import com.minigod.zero.data.vo.SmbFileVO;
import jcifs.smb1.smb1.NtlmPasswordAuthentication;
import jcifs.smb1.smb1.SmbException;
import jcifs.smb1.smb1.SmbFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class SmbFileUtil {
    private static Logger logger = LoggerFactory.getLogger(SmbFileUtil.class);

    public NtlmPasswordAuthentication authentication;
    public boolean isAuth;
    public String prefix;

    public SmbFileUtil(boolean isAuth, String address, String publicDir) {
        this.isAuth = isAuth;
        this.prefix = "smb://" + address + "/" + publicDir;
    }


    public byte[] getByte(String pathName) throws IOException {
        byte[] buffer = null;
        if (!pathName.endsWith("/")) {
            SmbFile file = getFile(pathName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream ins = file.getInputStream();
            if (file.isFile()) {
                IOUtils.copyLarge(ins, bos);
            }
            buffer = bos.toByteArray();
            ins.close();
            bos.close();
        }
        return buffer;
    }

    public void login(String host,String userName, String passWord) {
        logger.info("smb login:{},{},{}",host,userName,passWord);

        this.authentication = new NtlmPasswordAuthentication(host, userName, passWord);
    }

    public SmbFile getFile(String pathName) throws MalformedURLException {
        if (isAuth) {
            logger.info("smb server:{}",this.prefix + pathName);
            return new SmbFile(this.prefix + pathName, authentication);
        } else {
            return new SmbFile(this.prefix + pathName);
        }
    }

    public List<SmbFileVO> getFileVOList(String pathName) throws MalformedURLException, SmbException {
        List<SmbFileVO> fileVOList = new ArrayList<SmbFileVO>();

        SmbFile rootFile = getFile(pathName);
        SmbFileVO rootFileVO = new SmbFileVO();
        rootFileVO.setName(StringUtils.remove(rootFile.getName(), "/"));
        rootFileVO.setFile(rootFile);
        rootFileVO.setProxyPath("/");
        SmbFile[] list = rootFile.listFiles();
        logger.info("SmbFile[] list:{}",list);
        logger.info("SmbFile[] list:{}",list.length);
        for (SmbFile file : list) { // 遍历顶级目录
            if (file.isDirectory() || file.isHidden()) {
                continue;
            }
            logger.info("smb file",file);
            SmbFileVO topFileVO = new SmbFileVO();
            topFileVO.setName(StringUtils.remove(file.getName(), "/"));
            topFileVO.setPId(rootFileVO.getId());
            topFileVO.setFile(file);
            topFileVO.setProxyPath(rootFileVO.getProxyPath() + StringUtils.remove(file.getName(), "/"));
            fileVOList.add(topFileVO);
        }
        return fileVOList;
    }

}
