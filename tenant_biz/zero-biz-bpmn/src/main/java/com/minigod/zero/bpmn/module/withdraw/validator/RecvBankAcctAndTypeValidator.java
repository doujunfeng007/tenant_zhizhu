package com.minigod.zero.bpmn.module.withdraw.validator;

import cn.hutool.core.lang.Validator;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 银行账号和银行账号类型校验
 *
 * @author chenyu
 * @title RecvBankAcctAndTypeValidator
 * @date 2023-05-17 21:16
 * @description
 */
public class RecvBankAcctAndTypeValidator implements ConstraintValidator<RecvBankAcctAndTypeValid, List<String>>{

    @Override
    public boolean isValid(List<String> objectList, ConstraintValidatorContext context) {
        if(objectList != null && objectList.size() == 2){
           String bankAcct = objectList.get(0);
           String bankAcctType = objectList.get(1);
           if(StringUtil.isNotBlank(bankAcctType) && SystemCommonEnum.BankAcctType.FPS_MAIL.getCode().equals(bankAcctType)) {
               // 验证邮箱
               return Validator.isEmail(bankAcct);
           }else if(StringUtil.isNotBlank(bankAcctType) && SystemCommonEnum.BankAcctType.FPS_HKID.getCode().equals(bankAcctType)){
               return ValidatorUtils.isHkIdCard(bankAcct);
           }else if(StringUtil.isNotBlank(bankAcctType) && SystemCommonEnum.BankAcctType.FPS_MOBILE.getCode().equals(bankAcctType)){
               return ValidatorUtils.isMobile(bankAcct);
           }else{
               return ValidatorUtils.isBankCard(bankAcct);
           }
        }
        return true;
    }
}
