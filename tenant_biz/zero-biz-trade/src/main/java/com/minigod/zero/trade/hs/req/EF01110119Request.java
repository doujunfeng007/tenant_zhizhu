package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 取帐号限制
 */
public class EF01110119Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String op_branch_no;
    private String operator_no;
    private String user_type;
    private String op_password;
    private String op_station;
    private String op_entrust_way;
    private String menu_id;
    private String function_id;
    private String branch_no;
    private String audit_action;
    private String client_id;
    private String bank_no;
    private String fund_card;
    private String main_flag;
    private String client_group;
    private String room_code;
    private String asset_prop;
    private String organ_flag;
    private String organ_audit_date;
    private String fare_kind_str;
    private String en_entrust_way;
    private String client_rights;
    private String restriction;
    private String open_trades;
    private String profit_flag;
    private String discount_rate;
    private String product_flag;
    private String square_flag;
    private String remark;
    private String bank_batch;
    private String bank_AC;
    private String max_exposure;
    private String expiry_date_exp;
    private String credit_limit;
    private String expiry_date;
    private String holder_status_temp;
    private String save_keeping;
    private String contract_date;
    private String client_marked;
    private String credit_ratio;
    private String day_trade_limit;
    private String op_remark;
    private String fund_account;
    private String license_str;

    public String getOp_branch_no() {
        return op_branch_no;
    }

    public void setOp_branch_no(String op_branch_no) {
        this.op_branch_no = op_branch_no;
    }

    public String getOperator_no() {
        return operator_no;
    }

    public void setOperator_no(String operator_no) {
        this.operator_no = operator_no;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getOp_password() {
        return op_password;
    }

    public void setOp_password(String op_password) {
        this.op_password = op_password;
    }

    public String getOp_station() {
        return op_station;
    }

    public void setOp_station(String op_station) {
        this.op_station = op_station;
    }

    public String getOp_entrust_way() {
        return op_entrust_way;
    }

    public void setOp_entrust_way(String op_entrust_way) {
        this.op_entrust_way = op_entrust_way;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getFunction_id() {
        return function_id;
    }

    public void setFunction_id(String function_id) {
        this.function_id = function_id;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getAudit_action() {
        return audit_action;
    }

    public void setAudit_action(String audit_action) {
        this.audit_action = audit_action;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getFund_card() {
        return fund_card;
    }

    public void setFund_card(String fund_card) {
        this.fund_card = fund_card;
    }

    public String getMain_flag() {
        return main_flag;
    }

    public void setMain_flag(String main_flag) {
        this.main_flag = main_flag;
    }

    public String getClient_group() {
        return client_group;
    }

    public void setClient_group(String client_group) {
        this.client_group = client_group;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public String getAsset_prop() {
        return asset_prop;
    }

    public void setAsset_prop(String asset_prop) {
        this.asset_prop = asset_prop;
    }

    public String getOrgan_flag() {
        return organ_flag;
    }

    public void setOrgan_flag(String organ_flag) {
        this.organ_flag = organ_flag;
    }

    public String getOrgan_audit_date() {
        return organ_audit_date;
    }

    public void setOrgan_audit_date(String organ_audit_date) {
        this.organ_audit_date = organ_audit_date;
    }

    public String getFare_kind_str() {
        return fare_kind_str;
    }

    public void setFare_kind_str(String fare_kind_str) {
        this.fare_kind_str = fare_kind_str;
    }

    public String getEn_entrust_way() {
        return en_entrust_way;
    }

    public void setEn_entrust_way(String en_entrust_way) {
        this.en_entrust_way = en_entrust_way;
    }

    public String getClient_rights() {
        return client_rights;
    }

    public void setClient_rights(String client_rights) {
        this.client_rights = client_rights;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public String getOpen_trades() {
        return open_trades;
    }

    public void setOpen_trades(String open_trades) {
        this.open_trades = open_trades;
    }

    public String getProfit_flag() {
        return profit_flag;
    }

    public void setProfit_flag(String profit_flag) {
        this.profit_flag = profit_flag;
    }

    public String getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(String discount_rate) {
        this.discount_rate = discount_rate;
    }

    public String getProduct_flag() {
        return product_flag;
    }

    public void setProduct_flag(String product_flag) {
        this.product_flag = product_flag;
    }

    public String getSquare_flag() {
        return square_flag;
    }

    public void setSquare_flag(String square_flag) {
        this.square_flag = square_flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBank_batch() {
        return bank_batch;
    }

    public void setBank_batch(String bank_batch) {
        this.bank_batch = bank_batch;
    }

    public String getBank_AC() {
        return bank_AC;
    }

    public void setBank_AC(String bank_AC) {
        this.bank_AC = bank_AC;
    }

    public String getMax_exposure() {
        return max_exposure;
    }

    public void setMax_exposure(String max_exposure) {
        this.max_exposure = max_exposure;
    }

    public String getExpiry_date_exp() {
        return expiry_date_exp;
    }

    public void setExpiry_date_exp(String expiry_date_exp) {
        this.expiry_date_exp = expiry_date_exp;
    }

    public String getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(String credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getHolder_status_temp() {
        return holder_status_temp;
    }

    public void setHolder_status_temp(String holder_status_temp) {
        this.holder_status_temp = holder_status_temp;
    }

    public String getSave_keeping() {
        return save_keeping;
    }

    public void setSave_keeping(String save_keeping) {
        this.save_keeping = save_keeping;
    }

    public String getContract_date() {
        return contract_date;
    }

    public void setContract_date(String contract_date) {
        this.contract_date = contract_date;
    }

    public String getClient_marked() {
        return client_marked;
    }

    public void setClient_marked(String client_marked) {
        this.client_marked = client_marked;
    }

    public String getCredit_ratio() {
        return credit_ratio;
    }

    public void setCredit_ratio(String credit_ratio) {
        this.credit_ratio = credit_ratio;
    }

    public String getDay_trade_limit() {
        return day_trade_limit;
    }

    public void setDay_trade_limit(String day_trade_limit) {
        this.day_trade_limit = day_trade_limit;
    }

    public String getOp_remark() {
        return op_remark;
    }

    public void setOp_remark(String op_remark) {
        this.op_remark = op_remark;
    }

    public String getFund_account() {
        return fund_account;
    }

    public void setFund_account(String fund_account) {
        this.fund_account = fund_account;
    }

    public String getLicense_str() {
        return license_str;
    }

    public void setLicense_str(String license_str) {
        this.license_str = license_str;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
