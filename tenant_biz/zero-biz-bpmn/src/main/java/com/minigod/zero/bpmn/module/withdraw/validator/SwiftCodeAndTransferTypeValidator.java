package com.minigod.zero.bpmn.module.withdraw.validator;

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
public class SwiftCodeAndTransferTypeValidator implements ConstraintValidator<SwiftCodeAndTransferTypeValid, List<String>>{

    // 为空提示消息
    private String emptyMessage;
    // 格式错误提示消息
    private String formatMessage;
    // 长度错误提示消息
    private String lengthMessage;

    // 最大长度
    private int min;

    // 最大长度
    private int max;

    @Override
    public void initialize(SwiftCodeAndTransferTypeValid constraintAnnotation) {
        this.emptyMessage = constraintAnnotation.emptyMessage();
        this.formatMessage = constraintAnnotation.formatMessage();
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(List<String> objectList, ConstraintValidatorContext context) {
        String swiftCode = objectList.get(0);
        String transferType = objectList.get(1);
        if(StringUtil.isNotBlank(transferType)){
            Integer iTransferType = Integer.valueOf(transferType);
            // 取款方式为 电汇至香港以外银行
            if(SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType().equals(iTransferType)
               || SystemCommonEnum.TransferTypeEnum.HK.getType().equals(iTransferType)
               || SystemCommonEnum.TransferTypeEnum.HK_LOCAL.getType().equals(iTransferType)){
                context.disableDefaultConstraintViolation();
                if(StringUtil.isBlank(swiftCode)){
                    context.buildConstraintViolationWithTemplate(emptyMessage).addConstraintViolation();
                    return false;
                }
                if(swiftCode.length() < min || swiftCode.length() > max){
                    context.buildConstraintViolationWithTemplate(lengthMessage).addConstraintViolation();
                    return false;
                }
                // 校验格式 字符数字
                if(!ValidatorUtils.isLetterDigit(swiftCode)){
                    context.buildConstraintViolationWithTemplate(formatMessage).addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
