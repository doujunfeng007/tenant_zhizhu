package com.minigod.zero.trade.tiger.req;

import lombok.Data;

import java.util.List;

@Data
public class CreateAccountReq {

    private String clientUid;

    private boolean w8Status;

    private String baseCurrency;

    private Integer accountIdSuffix;

    private Integer alias;

    private Number withholdingTaxRate;

    private String externalId;
    /**
     * 账户能力 CASH MARGN
     */
    private String accountCapabilities;
    private String masterAccountId;
    private String taxResidenceCountry;
    private String residenceCountry;
    private String taxIdentificationNumber;
    private String realName;
    private String realNameEn;
    private String birthday;
    private String country;
    private List<TradeAuthority> tradeAuthorities;
//    private Long userId;
    private String category;
    // 内部类，用于表示 tradeAuthorities 列表中的每个元素
    public static class TradeAuthority {
        private String product;
        private String market;

        // Getters 和 Setters 方法
        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getMarket() {
            return market;
        }
        public void setMarket(String market) {
            this.market = market;
        }
    }
}
