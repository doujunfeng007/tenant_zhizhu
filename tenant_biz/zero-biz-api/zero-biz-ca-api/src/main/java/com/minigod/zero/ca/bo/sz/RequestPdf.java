package com.minigod.zero.ca.bo.sz;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: RequestPdf
 * @Description:
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class RequestPdf {
    @JSONField(name = "getPDF")
    private GetPDF getPDF;
}
