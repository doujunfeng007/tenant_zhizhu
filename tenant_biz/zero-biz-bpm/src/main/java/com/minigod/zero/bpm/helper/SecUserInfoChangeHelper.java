package com.minigod.zero.bpm.helper;


import com.google.common.collect.Maps;
import com.minigod.zero.bpm.vo.OpenAccountOtherDisclosureProtocol;
import com.minigod.zero.bpm.vo.OpenAccountPropertyTypeProtocol;
import com.minigod.zero.bpm.vo.OpenAccountTaxationInfoProtocol;
import com.minigod.zero.bpm.vo.response.SecUserInfoChangeResp;
import com.minigod.zero.bpm.vo.response.SecuritiesUserInfoFullResp;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SecUserInfoChangeHelper {
    private static final int SEC_USER_INFO_CHANGE_STEP_NAME = 1;
    private static final int SEC_USER_INFO_CHANGE_STEP_ADDRESS = 2;
    private static final int SEC_USER_INFO_CHANGE_STEP_PROPERTY = 3;
    private static final int SEC_USER_INFO_CHANGE_STEP_EXPERIENCE = 4;
    private static final int SEC_USER_INFO_CHANGE_STEP_TAX = 5;
    private static final int SEC_USER_INFO_CHANGE_STEP_IDENTITY = 6;
    private static final int SEC_USER_INFO_CHANGE_STEP_FAMILY_ADDRESS = 7;
    private static final int SEC_USER_INFO_CHANGE_STEP_CONTACT_ADDRESS = 8;
    /**
     * 本人不是香港证券及期货事务监察委员会的持牌人或注册人
     */
    public static final int DISCLOSURE_CODE_3 = 3;
    /**
     * 您是否为智珠证券有限公司（下称「智珠证券」）董事、员工或持牌代表。
     */
    public static final int DISCLOSURE_CODE_11 = 11;
    /**
     * 您是否为「智珠证券」董事、员工或持牌代表之亲属（例如：父母、配偶、兄弟、姊妹、子女等）。
     */
    public static final int DISCLOSURE_CODE_12 = 12;
    /**
     * 您是否为香港证券及期货事务监察委员会获发牌或注册人士的雇员。
     */
    public static final int DISCLOSURE_CODE_13 = 13;
    /**
     * 您是否为任何证券或期货交易所的注册人士或参与者，不受任何政府或监管机构所规管或监管。
     */
    public static final int DISCLOSURE_CODE_14 = 14;
    /**
     * 您是否为任何公司之高级人员或董事或控制可在任何交易所╱市场买卖其股份的公司。
     */
    public static final int DISCLOSURE_CODE_15 = 15;

    public static final int DISCLOSURE_CODE_16 = 16;
    public static final int DISCLOSURE_CODE_17 = 17;
    public static final int DISCLOSURE_CODE_18 = 18;
    public static final int DISCLOSURE_CODE_19 = 19;
    public static final int DISCLOSURE_CODE_20 = 20;

    public static final int DISCLOSURE_CODE_22 = 22;
    public static final int DISCLOSURE_CODE_23 = 23;
    public static final int DISCLOSURE_CODE_24 = 24;
    public static final int DISCLOSURE_CODE_25 = 25;


    // 根据不同修改步骤，组装不同的数据返回
    public static SecUserInfoChangeResp getChangeVO(Integer step, SecuritiesUserInfoFullResp securitiesUserFullInfo) {
        SecUserInfoChangeResp changeVO = new SecUserInfoChangeResp();

        // 根据不同的step包装不同的数据返回
        if (SEC_USER_INFO_CHANGE_STEP_NAME == step) {
            // 中文/拼音/英文名
            changeVO.setFamilyName(securitiesUserFullInfo.getFamilyName());
            changeVO.setGivenName(securitiesUserFullInfo.getGivenName());
            changeVO.setFamilyNameSpell(securitiesUserFullInfo.getFamilyNameSpell());
            changeVO.setGivenNameSpell(securitiesUserFullInfo.getGivenNameSpell());
            changeVO.setIdKind(securitiesUserFullInfo.getIdKind());
            changeVO.setIdCardValue(securitiesUserFullInfo.getIdNo());
            changeVO.setBirthday(securitiesUserFullInfo.getBirthday());
            changeVO.setOpenAccountType(securitiesUserFullInfo.getOpenAccountType());
            changeVO.setBankType(securitiesUserFullInfo.getBankType());
            changeVO.setIdCardValidDateEnd(securitiesUserFullInfo.getIdCardValidDateEnd());
            changeVO.setIdCardAddress(securitiesUserFullInfo.getIdCardAddress());
        }

        if (SEC_USER_INFO_CHANGE_STEP_FAMILY_ADDRESS == step) {
            changeVO.setFamilyAddress(securitiesUserFullInfo.getFamilyAddress());
            // 税务信息,需要根据地址做验证。
            List<OpenAccountTaxationInfoProtocol> taxationInfo = securitiesUserFullInfo.getTaxationInfo();
            if (taxationInfo != null) {
                for (int i = 0; i < taxationInfo.size(); i++) {
                    OpenAccountTaxationInfoProtocol tax = taxationInfo.get(i);
                    if (i == 0) {
                        changeVO.setDefaultCountry(tax.getTaxCountry());
                        changeVO.setDefaultPrivacyNum(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 1) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy1("can");
                        } else {
                            changeVO.setOfferPrivacy1("cannot");
                            changeVO.setNoOfferPrivacy1(tax.getReasonType());
                            changeVO.setReasonDesc1(tax.getReasonDesc());
                        }
                        changeVO.setCountry1(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum1(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 2) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy2("can");
                        } else {
                            changeVO.setOfferPrivacy2("cannot");
                            changeVO.setNoOfferPrivacy2(tax.getReasonType());
                            changeVO.setReasonDesc2(tax.getReasonDesc());
                        }
                        changeVO.setCountry2(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum2(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 3) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy3("can");
                        } else {
                            changeVO.setOfferPrivacy3("cannot");
                            changeVO.setNoOfferPrivacy3(tax.getReasonType());
                            changeVO.setReasonDesc3(tax.getReasonDesc());
                        }
                        changeVO.setCountry3(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum3(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 4) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy4("can");
                        } else {
                            changeVO.setOfferPrivacy4("cannot");
                            changeVO.setNoOfferPrivacy4(tax.getReasonType());
                            changeVO.setReasonDesc4(tax.getReasonDesc());
                        }
                        changeVO.setCountry4(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum4(tax.getTaxNumber());
                        continue;
                    }
                }
            }
        }

        if (SEC_USER_INFO_CHANGE_STEP_CONTACT_ADDRESS == step) {
            changeVO.setContactAddress(securitiesUserFullInfo.getContactAddress());
            // 税务信息,需要根据地址做验证。
            List<OpenAccountTaxationInfoProtocol> taxationInfo = securitiesUserFullInfo.getTaxationInfo();
            if (taxationInfo != null) {
                for (int i = 0; i < taxationInfo.size(); i++) {
                    OpenAccountTaxationInfoProtocol tax = taxationInfo.get(i);
                    if (i == 0) {
                        changeVO.setDefaultCountry(tax.getTaxCountry());
                        changeVO.setDefaultPrivacyNum(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 1) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy1("can");
                        } else {
                            changeVO.setOfferPrivacy1("cannot");
                            changeVO.setNoOfferPrivacy1(tax.getReasonType());
                            changeVO.setReasonDesc1(tax.getReasonDesc());
                        }
                        changeVO.setCountry1(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum1(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 2) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy2("can");
                        } else {
                            changeVO.setOfferPrivacy2("cannot");
                            changeVO.setNoOfferPrivacy2(tax.getReasonType());
                            changeVO.setReasonDesc2(tax.getReasonDesc());
                        }
                        changeVO.setCountry2(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum2(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 3) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy3("can");
                        } else {
                            changeVO.setOfferPrivacy3("cannot");
                            changeVO.setNoOfferPrivacy3(tax.getReasonType());
                            changeVO.setReasonDesc3(tax.getReasonDesc());
                        }
                        changeVO.setCountry3(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum3(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 4) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy4("can");
                        } else {
                            changeVO.setOfferPrivacy4("cannot");
                            changeVO.setNoOfferPrivacy4(tax.getReasonType());
                            changeVO.setReasonDesc4(tax.getReasonDesc());
                        }
                        changeVO.setCountry4(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum4(tax.getTaxNumber());
                        continue;
                    }
                }
            }
        }

        if (SEC_USER_INFO_CHANGE_STEP_ADDRESS == step) {
            // 地址信息
            changeVO.setFamilyAddress(securitiesUserFullInfo.getFamilyAddress());
            changeVO.setContactAddress(securitiesUserFullInfo.getContactAddress());

            // 税务信息,需要根据地址做验证。
            List<OpenAccountTaxationInfoProtocol> taxationInfo = securitiesUserFullInfo.getTaxationInfo();
            if (taxationInfo != null) {
                for (int i = 0; i < taxationInfo.size(); i++) {
                    OpenAccountTaxationInfoProtocol tax = taxationInfo.get(i);
                    if (i == 0) {
                        changeVO.setDefaultCountry(tax.getTaxCountry());
                        changeVO.setDefaultPrivacyNum(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 1) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy1("can");
                        } else {
                            changeVO.setOfferPrivacy1("cannot");
                            changeVO.setNoOfferPrivacy1(tax.getReasonType());
                            changeVO.setReasonDesc1(tax.getReasonDesc());
                        }
                        changeVO.setCountry1(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum1(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 2) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy2("can");
                        } else {
                            changeVO.setOfferPrivacy2("cannot");
                            changeVO.setNoOfferPrivacy2(tax.getReasonType());
                            changeVO.setReasonDesc2(tax.getReasonDesc());
                        }
                        changeVO.setCountry2(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum2(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 3) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy3("can");
                        } else {
                            changeVO.setOfferPrivacy3("cannot");
                            changeVO.setNoOfferPrivacy3(tax.getReasonType());
                            changeVO.setReasonDesc3(tax.getReasonDesc());
                        }
                        changeVO.setCountry3(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum3(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 4) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy4("can");
                        } else {
                            changeVO.setOfferPrivacy4("cannot");
                            changeVO.setNoOfferPrivacy4(tax.getReasonType());
                            changeVO.setReasonDesc4(tax.getReasonDesc());
                        }
                        changeVO.setCountry4(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum4(tax.getTaxNumber());
                        continue;
                    }
                }
            }


        }

        if (SEC_USER_INFO_CHANGE_STEP_PROPERTY == step) {
            changeVO.setBankType(securitiesUserFullInfo.getBankType());
            changeVO.setIdCardValue(securitiesUserFullInfo.getIdNo());
            changeVO.setBirthday(securitiesUserFullInfo.getBirthday());
            /*职业信息与财务状况*/
            // 职业类型
            changeVO.setNetCapital(securitiesUserFullInfo.getNetCapital());
            changeVO.setProfessionCode(securitiesUserFullInfo.getProfessionCode());
            changeVO.setProfessionType(securitiesUserFullInfo.getProfessionType());
            // 职业名称(自由职业才有这选项)
            changeVO.setFreelanceCode(securitiesUserFullInfo.getFreelanceCode());
            // 职业名称其他说明
            changeVO.setFreelanceOther(securitiesUserFullInfo.getFreelanceOther());
            // 公司名称
            changeVO.setCompanyName(securitiesUserFullInfo.getCompanyName());
            // 公司地址
            changeVO.setCompanyAddress(securitiesUserFullInfo.getCompanyAddress());
            // 所属行业
//            changeVO.setIndustryRange(securitiesUserFullInfo.getIndustryRange());
            changeVO.setProfessionType(securitiesUserFullInfo.getProfessionType());
            // 职位级别
            changeVO.setJobPosition(securitiesUserFullInfo.getJobPosition());
            // 收入来源
            changeVO.setCapitalSource(securitiesUserFullInfo.getCapitalSource());
            changeVO.setEmployYears(securitiesUserFullInfo.getEmployYears());

            // 财富来源
            changeVO.setWealthSource(securitiesUserFullInfo.getWealthSource());
            // 财务状况
            changeVO.setAnnualIncome(securitiesUserFullInfo.getAnnualIncome());
            // 财产种类
            if (null != securitiesUserFullInfo.getPropertyType()) {
                List<OpenAccountPropertyTypeProtocol> propertyTypeList = securitiesUserFullInfo.getPropertyType();
                for (OpenAccountPropertyTypeProtocol propertyType : propertyTypeList) {
                    if (1 == propertyType.getPropertyType()) {
                        changeVO.setBankCheck(true);
                        changeVO.setBankValue(propertyType.getPropertyAmount());
                        continue;
                    }
                    if (2 == propertyType.getPropertyType()) {
                        changeVO.setStockCheck(true);
                        changeVO.setStockValue(propertyType.getPropertyAmount());
                        continue;
                    }
                    if (3 == propertyType.getPropertyType()) {
                        changeVO.setRealCheck(true);
                        changeVO.setRealValue(propertyType.getPropertyAmount());
                        continue;
                    }
                }
            }

        }

        if (SEC_USER_INFO_CHANGE_STEP_EXPERIENCE == step) {
            changeVO.setBankType(securitiesUserFullInfo.getBankType());
            changeVO.setIdCardValue(securitiesUserFullInfo.getIdNo());
            changeVO.setBirthday(securitiesUserFullInfo.getBirthday());
            /*投资经验与目标*/
            changeVO.setInvestTarget(securitiesUserFullInfo.getInvestTarget());
            changeVO.setInvestTargetOther(securitiesUserFullInfo.getInvestTargetOther());
            changeVO.setStocksInvestmentExperienceType(securitiesUserFullInfo.getStocksInvestmentExperienceType());
            changeVO.setWarrantsInvestmentExperienceType(securitiesUserFullInfo.getWarrantsInvestmentExperienceType());
            changeVO.setFuturesInvestmentExperienceType(securitiesUserFullInfo.getFuturesInvestmentExperienceType());
            changeVO.setIsKnowDerivativeProducts(securitiesUserFullInfo.getIsKnowDerivativeProducts());
            changeVO.setIsTradedDerivativeProducts(securitiesUserFullInfo.getIsTradedDerivativeProducts());
            changeVO.setDerivativeProductsStudyType(securitiesUserFullInfo.getDerivativeProductsStudyType());
            changeVO.setDerivativeProductsStudyTypeOther(securitiesUserFullInfo.getDerivativeProductsStudyTypeOther());
            changeVO.setFinancingInstitutionWorkExperienceType(securitiesUserFullInfo.getFinancingInstitutionWorkExperienceType());
            changeVO.setFinancingInstitutionWorkExperienceTypeOther(securitiesUserFullInfo.getFinancingInstitutionWorkExperienceTypeOther());
            changeVO.setFundInvestmentExperienceType(securitiesUserFullInfo.getFundInvestmentExperienceType());
        }

        if (SEC_USER_INFO_CHANGE_STEP_TAX == step) {
            /*税务信息*/
            // 税务信息
            List<OpenAccountTaxationInfoProtocol> taxationInfo = securitiesUserFullInfo.getTaxationInfo();
            if (taxationInfo != null) {
                for (int i = 0; i < taxationInfo.size(); i++) {
                    OpenAccountTaxationInfoProtocol tax = taxationInfo.get(i);
                    if (i == 0) {
                        changeVO.setDefaultCountry(tax.getTaxCountry());
                        changeVO.setDefaultPrivacyNum(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 1) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy1("can");
                        } else {
                            changeVO.setOfferPrivacy1("cannot");
                            changeVO.setNoOfferPrivacy1(tax.getReasonType());
                            changeVO.setReasonDesc1(tax.getReasonDesc());
                        }
                        changeVO.setCountry1(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum1(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 2) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy2("can");
                        } else {
                            changeVO.setOfferPrivacy2("cannot");
                            changeVO.setNoOfferPrivacy2(tax.getReasonType());
                            changeVO.setReasonDesc2(tax.getReasonDesc());
                        }
                        changeVO.setCountry2(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum2(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 3) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy3("can");
                        } else {
                            changeVO.setOfferPrivacy3("cannot");
                            changeVO.setNoOfferPrivacy3(tax.getReasonType());
                            changeVO.setReasonDesc3(tax.getReasonDesc());
                        }
                        changeVO.setCountry3(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum3(tax.getTaxNumber());
                        continue;
                    }
                    if (i == 4) {
                        if (tax.getHasTaxNumber() == 1) {
                            changeVO.setOfferPrivacy4("can");
                        } else {
                            changeVO.setOfferPrivacy4("cannot");
                            changeVO.setNoOfferPrivacy4(tax.getReasonType());
                            changeVO.setReasonDesc4(tax.getReasonDesc());
                        }
                        changeVO.setCountry4(tax.getTaxCountry());
                        changeVO.setCanPrivacyNum4(tax.getTaxNumber());
                        continue;
                    }
                }
            }
            // fatca
            Map fatcaItem = Maps.newHashMap();
            if (securitiesUserFullInfo.getFatca() == null) {
                securitiesUserFullInfo.setFatca(0);
            }
            if (securitiesUserFullInfo.getFatca() == 0) {
                fatcaItem.put("code", 0);
                fatcaItem.put("label", "");
                fatcaItem.put("text", "我不是出生于美国/美国公民/美国居民/美国永久居民外国人(即所谓的美国绿卡持有人(不论到期日))");
                fatcaItem.put("title", "不是");
                fatcaItem.put("type", "argeement");
            }
            if (securitiesUserFullInfo.getFatca() == 1) {
                fatcaItem.put("code", 1);
                fatcaItem.put("label", "");
                fatcaItem.put("text", "我是出生于美国/美国公民/美国居民/美国永久居民外国人(即所谓的美国绿卡持有人(不论到期日))。");
                fatcaItem.put("title", "是");
                fatcaItem.put("type", "deny");
            }
            changeVO.setFatcaItem(fatcaItem);
            changeVO.setBankType(securitiesUserFullInfo.getBankType());
        }

        if (SEC_USER_INFO_CHANGE_STEP_IDENTITY == step) {
            // 判断是否保证金账户
            changeVO.setIsMarginAccount(false);
            if ("M".equals(securitiesUserFullInfo.getFundAccountType())) {
                changeVO.setIsMarginAccount(true);
            }
            /*身份资料申报*/
            List<OpenAccountOtherDisclosureProtocol> otherDisclosure = securitiesUserFullInfo.getOtherDisclosure();
            if (otherDisclosure != null) {
                for (int i = 0; i < otherDisclosure.size(); i++) {
                    OpenAccountOtherDisclosureProtocol disclosure = otherDisclosure.get(i);
                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_3) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCheckboxOhter33(false);
                        } else {
                            changeVO.setCheckboxOhter33(true);
                        }
                        continue;
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_11) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setJobTitle1(disclosure.getDisclosure1());
                            changeVO.setCheckboxOhter1(false);
                        } else {
                            changeVO.setCheckboxOhter1(true);
                        }
                        continue;
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_12) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setNameOther(disclosure.getDisclosure1());
                            changeVO.setRelationOther(disclosure.getDisclosure2());
                            changeVO.setCheckboxOhter2(false);
                        } else {
                            changeVO.setCheckboxOhter2(true);
                        }
                        continue;
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_13) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCompanyOther(disclosure.getDisclosure1());
                            changeVO.setCompanyJob(disclosure.getDisclosure2());
                            changeVO.setCheckboxOhter3(false);
                        } else {
                            changeVO.setCheckboxOhter3(true);
                        }
                        continue;
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_14) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setRegulator(disclosure.getDisclosure1());
                            changeVO.setOtherNation(disclosure.getDisclosure2());
                            changeVO.setCheckboxOhter4(false);
                        } else {
                            changeVO.setCheckboxOhter4(true);
                        }
                        continue;
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_15) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            if (!StringUtils.isEmpty(disclosure.getDisclosure1())) {
                                List split = Arrays.asList(disclosure.getDisclosure1().split(","));
                                int size = split.size();
                                if (size < 2) {
                                    changeVO.setCompanyInfo0_1(String.valueOf(split.get(0)));
                                }
                                if (size >= 2 && size < 3) {
                                    changeVO.setCompanyInfo0_1(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_1(String.valueOf(split.get(1)));
                                }
                                if (size >= 3 && size < 4) {
                                    changeVO.setCompanyInfo0_1(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_1(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_1(String.valueOf(split.get(2)));
                                }
                                if (size >= 4 && size < 5) {
                                    changeVO.setCompanyInfo0_1(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_1(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_1(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_1(String.valueOf(split.get(3)));
                                }
                                if (size == 5) {
                                    changeVO.setCompanyInfo0_1(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_1(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_1(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_1(String.valueOf(split.get(3)));
                                    changeVO.setCompanyInfo4_1(String.valueOf(split.get(4)));
                                }
                            }

                            if (!StringUtils.isEmpty(disclosure.getDisclosure2())) {
                                List split = Arrays.asList(disclosure.getDisclosure2().split(","));
                                int size = split.size();
                                if (size < 2) {
                                    changeVO.setCompanyInfo0_2(String.valueOf(split.get(0)));
                                }
                                if (size >= 2 && size < 3) {
                                    changeVO.setCompanyInfo0_2(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_2(String.valueOf(split.get(1)));
                                }
                                if (size >= 3 && size < 4) {
                                    changeVO.setCompanyInfo0_2(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_2(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_2(String.valueOf(split.get(2)));
                                }
                                if (size >= 4 && size < 5) {
                                    changeVO.setCompanyInfo0_2(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_2(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_2(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_2(String.valueOf(split.get(3)));
                                }
                                if (size == 5) {
                                    changeVO.setCompanyInfo0_2(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_2(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_2(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_2(String.valueOf(split.get(3)));
                                    changeVO.setCompanyInfo4_2(String.valueOf(split.get(4)));
                                }
                            }

                            if (!StringUtils.isEmpty(disclosure.getDisclosure3())) {
                                List split = Arrays.asList(disclosure.getDisclosure3().split(","));
                                int size = split.size();
                                if (size < 2) {
                                    changeVO.setCompanyInfo0_3(String.valueOf(split.get(0)));
                                }
                                if (size >= 2 && size < 3) {
                                    changeVO.setCompanyInfo0_3(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_3(String.valueOf(split.get(1)));
                                }
                                if (size >= 3 && size < 4) {
                                    changeVO.setCompanyInfo0_3(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_3(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_3(String.valueOf(split.get(2)));
                                }
                                if (size >= 4 && size < 5) {
                                    changeVO.setCompanyInfo0_3(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_3(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_3(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_3(String.valueOf(split.get(3)));
                                }
                                if (size == 5) {
                                    changeVO.setCompanyInfo0_3(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_3(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_3(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_3(String.valueOf(split.get(3)));
                                    changeVO.setCompanyInfo4_3(String.valueOf(split.get(4)));
                                }

                            }

                            if (!StringUtils.isEmpty(disclosure.getDisclosure4())) {
                                List split = Arrays.asList(disclosure.getDisclosure4().split(","));
                                int size = split.size();
                                if (size < 2) {
                                    changeVO.setCompanyInfo0_4(String.valueOf(split.get(0)));
                                }
                                if (size >= 2 && size < 3) {
                                    changeVO.setCompanyInfo0_4(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_4(String.valueOf(split.get(1)));
                                }
                                if (size >= 3 && size < 4) {
                                    changeVO.setCompanyInfo0_4(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_4(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_4(String.valueOf(split.get(2)));
                                }
                                if (size >= 4 && size < 5) {
                                    changeVO.setCompanyInfo0_4(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_4(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_4(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_4(String.valueOf(split.get(3)));
                                }
                                if (size == 5) {
                                    changeVO.setCompanyInfo0_4(String.valueOf(split.get(0)));
                                    changeVO.setCompanyInfo1_4(String.valueOf(split.get(1)));
                                    changeVO.setCompanyInfo2_4(String.valueOf(split.get(2)));
                                    changeVO.setCompanyInfo3_4(String.valueOf(split.get(3)));
                                    changeVO.setCompanyInfo4_4(String.valueOf(split.get(4)));
                                }
                            }

                            changeVO.setCheckboxOhter5(false);
                        } else {
                            changeVO.setCheckboxOhter5(true);
                        }
                        continue;
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_22) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCheckboxOhter7(false);
                        } else {
                            changeVO.setCheckboxOhter7(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_23) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCheckboxOhter8(false);
                        } else {
                            changeVO.setCheckboxOhter8(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_24) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCheckboxOhter9(false);
                        } else {
                            changeVO.setCheckboxOhter9(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_25) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCheckboxOhter10(false);
                        } else {
                            changeVO.setCheckboxOhter10(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_16) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setCheckboxOhter6(false);
                        } else {
                            changeVO.setCheckboxOhter6(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_17) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setBenefitOwner(false);
                        } else {
                            changeVO.setBenefitOwner(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_18) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setAdditionInfoCheckbox1(false);
                            changeVO.setAccountName1(disclosure.getDisclosure1());
                            changeVO.setAccountNum1(disclosure.getDisclosure2());
                        } else {
                            changeVO.setAdditionInfoCheckbox1(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_19) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setAdditionInfoCheckbox2(false);
                            changeVO.setAccountName2(disclosure.getDisclosure1());
                            changeVO.setAccountNum2(disclosure.getDisclosure2());
                        } else {
                            changeVO.setAdditionInfoCheckbox2(true);
                        }
                    }

                    if (disclosure.getDisclosureCode() == DISCLOSURE_CODE_20) {
                        if (disclosure.getDisclosureIsTrue() == 1) {
                            changeVO.setAdditionInfoCheckbox3(false);
                            changeVO.setAccountName3(disclosure.getDisclosure1());
                            changeVO.setAccountNum3(disclosure.getDisclosure2());
                        } else {
                            changeVO.setAdditionInfoCheckbox3(true);
                        }
                    }
                }
            }

        }
        return changeVO;
    }
}
