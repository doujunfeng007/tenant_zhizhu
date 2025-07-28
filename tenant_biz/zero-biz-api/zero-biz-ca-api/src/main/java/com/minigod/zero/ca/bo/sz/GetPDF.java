package com.minigod.zero.ca.bo.sz;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: GetPDF
 * @Description:
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class GetPDF {
    @JSONField(name = "userName")
    private String userName;
    @JSONField(name = "idCode")
    private String idCode;
    @JSONField(name = "certDn")
    private String certDN;
    @JSONField(name = "certSn")
    private String certSn;
    @JSONField(name = "signImg")
    private String signImg;
    @JSONField(name = "locations")
    private List<Location> locations;
    @JSONField(name = "xDpi")
    private String xdpi;
    @JSONField(name = "yDpi")
    private String ydpi;

}
