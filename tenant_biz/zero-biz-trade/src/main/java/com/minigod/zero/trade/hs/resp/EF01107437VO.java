package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 7437 取代销基金基本信息
 */
public class EF01107437VO implements Serializable {
    private String exchangeType; //char[4] 交易类别
    private String stockCode; //char[12] 证券代码
    private String settMode; //char 交收方式
    private String subStockType; //char 证券子类别
    private String issuserNo; //char[20] 基金发行商
    private String brokerNo; //int 基金公司/券商编号
    private String custodian; //char[8] 托管商
    private String refPriceDate; //int 参考价格日期
    //    private String netdistribution; //double 最新派息
//    private String volatilityfc; //double 三年年度化回报
    private String company; //char[255] 基金公司
    private String cpfc1m; //double 累计表现 基金货币1month
    //    private String cpfc1y; //double 累计表现 基金货币1year
//    private String cpfc3m; //double 累计表现 基金货币3month
//    private String cutofftime; //int 截單時間
    private String lancheddate; //int 发行日期
    private String lanchedprice; //double 发行价
    /*    private String managementfee; //char[255] 基金管理費
        private String performancefee; //char[255] 基金表現費
        private String riskrating; //char[4] 風險評級*/
//    private String sharpratiofc; //double 三年夏普比率
//    private String softclose; //double 基金是否暫時不可以申購
    private String confirmTime; //int 确认时间
    private String moneyType; //char 币种类别
    private String lastPrice; //double 最新价
    private String closePrice; //double 昨收价
    //    private String stockType; //char 代码类别
    private String stockName; //char[255] 代码名称
    private String isinCode; //char[20] 国际标识码
    private String stockFullname; //char[255] 代码英文名
    private String stockNamegb; //char[255] 代码名称（GBK）
    private String stockNamebig5; //char[255] 代码名称（BIG5）
    private String interestRateYear;//七日年化收益率
    private String wanFenProfit; // 万份收益
    private String fundType;//基金类型

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getSettMode() {
        return settMode;
    }

    public void setSettMode(String settMode) {
        this.settMode = settMode;
    }

    public String getSubStockType() {
        return subStockType;
    }

    public void setSubStockType(String subStockType) {
        this.subStockType = subStockType;
    }

    public String getIssuserNo() {
        return issuserNo;
    }

    public void setIssuserNo(String issuserNo) {
        this.issuserNo = issuserNo;
    }

    public String getBrokerNo() {
        return brokerNo;
    }

    public void setBrokerNo(String brokerNo) {
        this.brokerNo = brokerNo;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getRefPriceDate() {
        return refPriceDate;
    }

    public void setRefPriceDate(String refPriceDate) {
        this.refPriceDate = refPriceDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCpfc1m() {
        return cpfc1m;
    }

    public void setCpfc1m(String cpfc1m) {
        this.cpfc1m = cpfc1m;
    }

    public String getLancheddate() {
        return lancheddate;
    }

    public void setLancheddate(String lancheddate) {
        this.lancheddate = lancheddate;
    }

    public String getLanchedprice() {
        return lanchedprice;
    }

    public void setLanchedprice(String lanchedprice) {
        this.lanchedprice = lanchedprice;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getStockFullname() {
        return stockFullname;
    }

    public void setStockFullname(String stockFullname) {
        this.stockFullname = stockFullname;
    }

    public String getStockNamegb() {
        return stockNamegb;
    }

    public void setStockNamegb(String stockNamegb) {
        this.stockNamegb = stockNamegb;
    }

    public String getStockNamebig5() {
        return stockNamebig5;
    }

    public void setStockNamebig5(String stockNamebig5) {
        this.stockNamebig5 = stockNamebig5;
    }

    public String getInterestRateYear() {
        return interestRateYear;
    }

    public void setInterestRateYear(String interestRateYear) {
        this.interestRateYear = interestRateYear;
    }

    public String getWanFenProfit() {
        return wanFenProfit;
    }

    public void setWanFenProfit(String wanFenProfit) {
        this.wanFenProfit = wanFenProfit;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
}
