package com.minigod.zero.biz.common.enums;

/**
 * 邮件、短信、推送，消息相关枚举
 */
public enum MessageSendEnum {

    QUOT_BUY_NOTICE("marketPurchaseNotice" , "行情购买成功",3004),
    QUOT_EXPIRE_NOTICE("marketExpireNotice" , "行情到期提醒",3005)
    ;

    private String tplId;
    private String des;
    private Integer omTplId;

    MessageSendEnum(String tplId,String des,Integer omTplId){
        this.tplId = tplId;
        this.des=des;
        this.omTplId = omTplId;
    }
    public void setTplId(String tplId){
        this.tplId = tplId;
    }
    public String getTplId(){
        return this.tplId;
    }

    public void setDes(String des){
        this.des = des;
    }
    public String setDes(){
        return this.des;
    }

    public void setOmTplId(Integer omTplId){
        this.omTplId = omTplId;
    }
    public Integer getOmTplId(){
        return this.omTplId;
    }


    public static String getName(Integer omTplId) {
        for (MessageSendEnum c : MessageSendEnum.values()) {
            if (omTplId.intValue()==c.omTplId) {
                return c.tplId;
            }
        }
        return null;
    }
}
