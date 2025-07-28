package com.minigod.zero.bpm.helper;

import com.minigod.zero.bpm.vo.OpenAccountAppointmentProtocol;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author sunline
 * @createDate 2017/2/22
 * @description
 */
@Slf4j
public class OpenAccountAppointmentHelper {

    /**
     * 开户预约接口数据完整性校验
     *
     * @param openAccountInfo
     * @return
     */
    public static boolean openAccountAppointmentDataValidate(OpenAccountAppointmentProtocol openAccountInfo) {

        if (null == openAccountInfo.getFundAccountType()) {
            log.info("【开户预约接口数据完整性校验】：请填写账户类型");
            return false;
        }

        if (null == openAccountInfo.getOpenAccountType()) {
            log.info("【开户预约接口数据完整性校验】：开户类型为空");
            return false;
        }

        if (null == openAccountInfo.getOpenAccountAccessWay()) {
            log.info("【开户预约接口数据完整性校验】：开户接入方式为空");
            return false;
        }
//        if (2 == openAccountInfo.getOpenAccountAccessWay()) {
//            if (null == openAccountInfo.getIdentitySimilarityPercent()) {
//
//                log.info("【开户预约接口数据完整性校验】：识别精度为空");
//                return false;
//            }
//            if (null == openAccountInfo.getIsPassIdentityAuthentication()) {
//                log.info("【开户预约接口数据完整性校验】：是否通过验证为空");
//                return false;
//            }
//        }

        if (null == openAccountInfo.getFundAccountType()) {
            log.info("【开户预约接口数据完整性校验】：请填写账户类型");
            return false;
        }

        if (null == openAccountInfo.getSourceChannelId() || StringUtils.isBlank(openAccountInfo.getSourceChannelId())) {
            log.info("【开户预约接口数据完整性校验】：请填写渠道号");
            return false;
        }
        if (null == openAccountInfo.getUserId()) {
            log.info("【开户预约接口数据完整性校验】：请填写用户号");
            return false;
        }
        if (null == openAccountInfo.getClientNameSpell() || StringUtils.isBlank(openAccountInfo.getClientNameSpell())) {
            log.info("【开户预约接口数据完整性校验】：请填写用户英文名");
            return false;
        }

        if (null == openAccountInfo.getGivenNameSpell() || StringUtils.isBlank(openAccountInfo.getGivenNameSpell())) {
            log.info("【开户预约接口数据完整性校验】：请填写用户英文名字");
            return false;
        }

        if (null == openAccountInfo.getFamilyNameSpell() || StringUtils.isBlank(openAccountInfo.getFamilyNameSpell())) {
            log.info("【开户预约接口数据完整性校验】：请填写用户英文姓氏");
            return false;
        }

        if (null == openAccountInfo.getIdKind()) {
            log.info("【开户预约接口数据完整性校验】：请填写身份证类型");
            return false;
        }

        if (1 == openAccountInfo.getIdKind()) {
            if (null == openAccountInfo.getClientName() || StringUtils.isBlank(openAccountInfo.getClientName())) {
                log.info("【开户预约接口数据完整性校验】：请填写用户中文名");
                return false;
            }

            if (null == openAccountInfo.getGivenName() || StringUtils.isBlank(openAccountInfo.getGivenName())) {
                log.info("【开户预约接口数据完整性校验】：请填写用户中文名字");
                return false;
            }

            if (null == openAccountInfo.getFamilyName() || StringUtils.isBlank(openAccountInfo.getFamilyName())) {
                log.info("【开户预约接口数据完整性校验】：请填写用户中文姓氏");
                return false;
            }

            if (null == openAccountInfo.getIdCardAddress() || StringUtils.isBlank(openAccountInfo.getIdCardAddress())) {
                log.info("【开户预约接口数据完整性校验】：请填写证件地址");
                return false;
            }
        }

        if (1 == openAccountInfo.getIdKind()) {
            if (2 == openAccountInfo.getOpenAccountAccessWay()) {
                if (null == openAccountInfo.getIdCardValidDateStart() || StringUtils.isBlank(openAccountInfo.getIdCardValidDateStart())) {
                    log.info("【开户预约接口数据完整性校验】：请填写证件生效日期");
                    return false;
                }
                if (null == openAccountInfo.getIdCardValidDateEnd() || StringUtils.isBlank(openAccountInfo.getIdCardValidDateEnd())) {
                    log.info("【开户预约接口数据完整性校验】：请填写证件失效日期");
                    return false;
                }
            }
        }

        if (3 == openAccountInfo.getIdKind()) {
            if (2 == openAccountInfo.getOpenAccountAccessWay()) {
                if (null == openAccountInfo.getIdCardValidDateEnd() || StringUtils.isBlank(openAccountInfo.getIdCardValidDateEnd())) {
                    log.info("【开户预约接口数据完整性校验】：请填写证件失效日期");
                    return false;
                }
            }
        }


        if (null == openAccountInfo.getNationality() || StringUtils.isBlank(openAccountInfo.getNationality())) {
            log.info("【开户预约接口数据完整性校验】：请填写国籍");
            return false;
        }

//        if (0==openAccountInfo.getBankType()) {
//            if ("0".equals(openAccountInfo.getNationality())) {
//                if (StringUtils.isBlank(openAccountInfo.getClientName())) {
//                    log.info("【开户预约接口数据完整性校验】：请填写用户中文名");
//                   return false;
//                }
//            }
//        }

        if (null == openAccountInfo.getIdNo() || StringUtils.isBlank(openAccountInfo.getIdNo())) {
            log.info("【开户预约接口数据完整性校验】：请填写身份证号码");
            return false;
        }
        if (null == openAccountInfo.getSex()) {
            log.info("【开户预约接口数据完整性校验】：请填写性别");
            return false;
        }
        if (null == openAccountInfo.getEmail() || StringUtils.isBlank(openAccountInfo.getEmail())) {
            log.info("【开户预约接口数据完整性校验】：请填写用邮箱");
            return false;
        }

        if (null == openAccountInfo.getPhoneNumber() || StringUtils.isBlank(openAccountInfo.getPhoneNumber())) {
            log.info("【开户预约接口数据完整性校验】：请填写手机号码");
            return false;
        }

        if (null == openAccountInfo.getBirthday() || StringUtils.isBlank(openAccountInfo.getBirthday())) {
            log.info("【开户预约接口数据完整性校验】：请填写生日");
            return false;
        }

        if (null == openAccountInfo.getBankType()) {
            log.info("【开户预约接口数据完整性校验】：请填写银行类型");
            return false;
        }

//        if (null == openAccountInfo.getBankId()) {
//            log.info("【开户预约接口数据完整性校验】：请填写银行编号");
//            return false;
//        }
//        if (null == openAccountInfo.getBankNo() || StringUtils.isBlank(openAccountInfo.getBankNo())) {
//            log.info("【开户预约接口数据完整性校验】：请填写银行卡号");
//            return false;
//        }
//
//        //香港账户 = 0  非香港账户 = 1
//        if ("0".equals(openAccountInfo.getBankType())) {
//            if (null == openAccountInfo.getBankAccountName() || StringUtils.isBlank(openAccountInfo.getBankAccountName())) {
//                log.info("【开户预约接口数据完整性校验】：请填写银行户名");
//               return false;
//            }
//        }

        if (1 == openAccountInfo.getBankType()) {
//            if (null == openAccountInfo.getBankId()) {
//                log.info("【开户预约接口数据完整性校验】：请填写银行编号");
//           return false;
//            }
            if (null == openAccountInfo.getBankNo() || StringUtils.isBlank(openAccountInfo.getBankNo())) {
                log.info("【开户预约接口数据完整性校验】：请填写银行卡号");
                return false;
            }
        }


       /* if (null == openAccountInfo.getPropertyType() || openAccountInfo.getPropertyType().size() < 1) {
            log.info("【开户预约接口数据完整性校验】：请填写财产种类");
            return false;
        }*/

        /*if (StringUtils.isBlank(openAccountInfo.getWealthSource())) {
            log.info("【开户预约接口数据完整性校验】：请填写财富来源");
            return false;
        }*/
        if (null == openAccountInfo.getOtherDisclosure() || openAccountInfo.getOtherDisclosure().size() < 1) {
            log.info("【开户预约接口数据完整性校验】：请填写其他信息披露");
            return false;
        }

        if (null == openAccountInfo.getTaxationInfo() || openAccountInfo.getTaxationInfo().size() < 1) {
            log.info("【开户预约接口数据完整性校验】：请填写税务信息");
            return false;
        }
        if (null == openAccountInfo.getIsAllowProvidePrivacy()) {
            log.info("【开户预约接口数据完整性校验】：请填写私隐信息");
            return false;
        }
        if (StringUtils.isEmpty(openAccountInfo.getFamilyAddress())) {
            log.info("【开户预约接口数据完整性校验】：请填写住宅住址");
            return false;
        }

        if (StringUtils.isBlank(openAccountInfo.getContactAddress())) {
            log.info("【开户预约接口数据完整性校验】：请填写通讯地址");
            return false;
        }


        if (null == openAccountInfo.getProfessionCode()) {
            log.info("【开户预约接口数据完整性校验】：请填写就业信息");
            return false;
        }

        if (1 == openAccountInfo.getProfessionCode() || 2 == openAccountInfo.getProfessionCode()) {
            if (null == openAccountInfo.getCompanyName() || StringUtils.isBlank(openAccountInfo.getCompanyName())) {
                log.info("【开户预约接口数据完整性校验】：请填写公司名称");
                return false;
            }
//            if (null == openAccountInfo.getCompanyAddress() || StringUtils.isBlank(openAccountInfo.getCompanyAddress())) {
//                log.info("【开户预约接口数据完整性校验】：请填写公司地址");
//                return false;
//            }

            if (null == openAccountInfo.getProfessionType()) {
                log.info("【开户预约接口数据完整性校验】：请填写所属行业");
                return false;
            }

//            if (1 == openAccountInfo.getProfessionCode()) {
//                if (null == openAccountInfo.getJobPosition() || StringUtils.isBlank(openAccountInfo.getJobPosition())) {
//                    log.info("【开户预约接口数据完整性校验】：请填写职位");
//                    return false;
//                }
//            }
        }

        if (null == openAccountInfo.getCapitalSource() || CollectionUtils.isEmpty(openAccountInfo.getCapitalSource())) {
            log.info("【开户预约接口数据完整性校验】：请填写收入来源");
            return false;
        }
        if (null == openAccountInfo.getWealthSource() || CollectionUtils.isEmpty(openAccountInfo.getWealthSource())) {
            log.info("【开户预约接口数据完整性校验】：请填写财富来源");
            return false;
        }

//        if (null == openAccountInfo.getProfessionType()) {
//            if (null == openAccountInfo.getJobPosition() || StringUtils.isBlank(openAccountInfo.getJobPosition())) {
//                log.info("【开户预约接口数据完整性校验】：请填写所属行业");
//             return false;
//            }
//        }

        if (null == openAccountInfo.getAnnualIncome()) {
            log.info("【开户预约接口数据完整性校验】：请填写年收入");
            return false;
        }
        if(null == openAccountInfo.getNetCapital()) {
            log.info("【开户预约接口数据完整性校验】：请填写净资产");
            return false;
        }

        if (null == openAccountInfo.getInvestTarget() || CollectionUtils.isEmpty(openAccountInfo.getInvestTarget())) {
            log.info("【开户预约接口数据完整性校验】：请填写投资目标");
            return false;
        }

        if (openAccountInfo.getInvestTarget().contains("4")) {
            if (null == openAccountInfo.getInvestTargetOther() || StringUtils.isBlank(openAccountInfo.getInvestTargetOther())) {
                log.info("【开户预约接口数据完整性校验】：请填写其他投资目标");
                return false;
            }
        }

        if (null == openAccountInfo.getStocksInvestmentExperienceType()) {
            log.info("【开户预约接口数据完整性校验】：请填写股票投资经验");
            return false;
        }

        if(null == openAccountInfo.getFundInvestmentExperienceType()) {
            log.info("【开户预约接口数据完整性校验】：请填写基金投资经验");
            return false;
        }

        if (null == openAccountInfo.getWarrantsInvestmentExperienceType()) {
            log.info("【开户预约接口数据完整性校验】：请填写认证股权投资经验");
            return false;
        }
        if (null == openAccountInfo.getFuturesInvestmentExperienceType()) {
            log.info("【开户预约接口数据完整性校验】：请填写期货投资经验");
            return false;
        }
        if (null == openAccountInfo.getIsKnowDerivativeProducts()) {
            log.info("【开户预约接口数据完整性校验】：请填写是否了解衍生品性质和风险");
            return false;
        }

        if (1 == openAccountInfo.getIsKnowDerivativeProducts()) {
            if (null != openAccountInfo.getDerivativeProductsStudyType() || null != openAccountInfo.getFinancingInstitutionWorkExperienceType()
                    || null != openAccountInfo.getIsTradedDerivativeProducts()) {
                if (null != openAccountInfo.getDerivativeProductsStudyType() && 7 == openAccountInfo.getDerivativeProductsStudyType()) {
                    if (null == openAccountInfo.getDerivativeProductsStudyTypeOther() || StringUtils.isBlank(openAccountInfo.getDerivativeProductsStudyTypeOther())) {
                        log.info("【开户预约接口数据完整性校验】：请填写衍生产品其它学习方式");
                        return false;
                    }
                }
                if (null != openAccountInfo.getFinancingInstitutionWorkExperienceType() && 4 == openAccountInfo.getFinancingInstitutionWorkExperienceType()) {
                    if (null == openAccountInfo.getFinancingInstitutionWorkExperienceTypeOther() || StringUtils.isBlank(openAccountInfo.getFinancingInstitutionWorkExperienceTypeOther())) {
                        log.info("【开户预约接口数据完整性校验】：请填写在金融机构其它工作经验类型");
                        return false;
                    }
                }
            } else {
                log.info("【开户预约接口数据完整性校验】：请填写衍生品相关内容");
                return false;
            }

        }

        if (null == openAccountInfo.getAddressType()) {
            log.info("【开户预约接口数据完整性校验】：请填写地址类型");
            return false;
        }

//        if (null == openAccountInfo.getAcceptRisk()) {
//            log.info("【开户预约接口数据完整性校验】：请填写风险接受程度");
//            return false;
//        }

        if (null == openAccountInfo.getFatca()) {
            log.info("【开户预约接口数据完整性校验】：请填写FATCA信息");
            return false;
        }

        if (null == openAccountInfo.getNorthTrade()) {
            log.info("【开户预约接口数据完整性校验】：请填写北向交易信息");
            return false;
        }

        if (null == openAccountInfo.getIssueCountry() || StringUtils.isBlank(openAccountInfo.getIssueCountry())) {
            log.info("【开户预约接口数据完整性校验】：请填写证件签发地");
            return false;
        }

        if (null == openAccountInfo.getBirthPlace() || StringUtils.isBlank(openAccountInfo.getBirthPlace())) {
            log.info("【开户预约接口数据完整性校验】：请填写出生地");
            return false;
        }
        return true;
    }
}
