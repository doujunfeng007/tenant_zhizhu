package com.minigod.zero.trade.vo.resp;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: IFundAccountVO
 * @Description:
 * @Author chenyu
 * @Date 2024/3/2
 * @Version 1.0
 */
@Data
public class IFundAccountVO {
    private String accountId;
    private Integer type;
    private String chineseName;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String phoneCountry;
    private String idType;
    private String idNumber;
    private String issueCountry;
    private String idExpiryDate;
    private String birthday;
    private String nationality;
    private String countryOfBirth;
    private String company;
    private String employedCompany;
    private String position;
    private String jobStatus;
    private String email;
    private String taxCountry;
    private String taxModifyDate;
    private String taxNumber;
    private String residentialAddress;
    private String countryOfResidence;
    private String correspondenceAddress;
    private String preferredCommunication;
    private String annualIncome;
    private String netAsset;
    private String investmentObjective;
    private String education;
    private String piFlag;
    private String marginStatus;
    private String sourceOfIncome;
    private String sourceOfIncomeRemark;
    private String accountHolderName;
    private String bankAccountNumber;
    private String extAccountId;
    private String riskScore;
    private String riskUpdateTime;
    private String closedTime;
    private String status;
    private String createTime;
    private String updateTime;
    private List<SubAccountVo> subAccounts;
}


