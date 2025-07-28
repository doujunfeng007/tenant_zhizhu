package com.minigod.zero.ca.bo.sz;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: Location
 * @Description:
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class Location {
    @JSONField(name ="pageNo")
    private Integer pageNo;
    @JSONField(name ="x")
    private Integer x;
    @JSONField(name ="y")
    private Integer y;
}
