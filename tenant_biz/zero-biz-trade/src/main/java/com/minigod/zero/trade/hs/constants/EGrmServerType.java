package com.minigod.zero.trade.hs.constants;

/**
 * Created by sunline on 2016/5/19 11:21.
 * sunline
 */
public enum EGrmServerType {
    OUTER_GATEWAY(0),INNER_CLIENT(1);
    private int typeId;

    private EGrmServerType(int typeId){
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
