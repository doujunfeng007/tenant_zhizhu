package com.minigod.zero.trade.hs.constants;

/**
 * Created by sunline on 2016/5/28 15:18.
 * sunline
 */
public enum ERealType {
    ENTRUST("0"),QUERY("1"),CANCEL("2");
    private String flag;
    private ERealType(String flag){
        this.flag = flag;
    }

    @Override
    public String toString() {
        return flag;
    }
}
