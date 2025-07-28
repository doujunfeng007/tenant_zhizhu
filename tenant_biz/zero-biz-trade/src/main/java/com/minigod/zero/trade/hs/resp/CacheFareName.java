package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.Map;

public class CacheFareName implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, String> fareNames;

    public Map<String, String> getFareNames() {
        return fareNames;
    }

    public void setFareNames(Map<String, String> fareNames) {
        this.fareNames = fareNames;
    }
}
