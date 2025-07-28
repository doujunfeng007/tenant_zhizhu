package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

public class EF01110149Fare implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text;
    private String fare;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

}
