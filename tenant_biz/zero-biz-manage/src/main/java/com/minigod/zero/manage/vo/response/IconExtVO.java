package com.minigod.zero.manage.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/4/12 17:24
 * @version: v1.0
 */
@Data
public class IconExtVO implements Serializable {
    private Integer id;//sys_role_icon主键ID
    private Integer roleId;//角色编号(用户类型)
    private Integer iconId;//icon编号
    private boolean isDisplay;//是否展示
    private String  name;//icon名称
    private String functionCode; //功能代码
    private Integer grade; //排序
    private String urlPage; //页面链接地址
    private String  functionName;//功能名称
    private String  urlSmall;//小图标url
    private String  urlBig;//大图标url
    private String  urlAndroid;//安卓图标
    private String urlIosBlack;//icon黑图案(IOS)
    private String urlIosWhite;//icon白图案(IOS)
    private String urlAndroidBlack;//icon黑图案(安卓)
    private String urlAndroidWhite;//icon白图案(安卓)
    private String  functionType;//功能类型
    private String  pageUrl; //功能URL地址
    private String  functionRight; //用户权限
    private boolean ipFilter;//是否IP控制
}
