package com.minigod.zero.trade.hs.constants;

/**
 * Created by sunline on 2016/5/28 15:18.
 * sunline
 */
public enum ERealStatus {
    FILLED("0"),REJECTED("2"),CONFIRM("4");
    private String flag;
    private ERealStatus(String flag){
        this.flag = flag;
    }

    @Override
    public String toString() {
        return flag;
    }
}
