package com.minigod.zero.data.vo;

import jcifs.smb1.smb1.SmbFile;
import lombok.Data;

@Data
public class SmbFileVO {

    private String id;

    private String pId;

    private String name;

    private SmbFile file;

    private String proxyPath;
}
