package com.minigod.zero.bpmn.module.withdraw.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 国际编码验证
 *
 * @author chenyu
 * @title SwiftCodeAndTransferTypeValid
 * @date 2023-06-19 10:56
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ PARAMETER, FIELD, METHOD })
@Constraint(validatedBy = SwiftCodeAndTransferTypeValidator.class)
public @interface SwiftCodeAndTransferTypeValid {

    String message() default "";

    String emptyMessage() default "收款电汇代码(SwiftCode)不能为空";

    String formatMessage() default "收款电汇代码(SwiftCode)格式错误, 限字母数字";

    String lengthMessage() default "收款电汇代码(SwiftCode)长度错误，限8-11个字符";

    int max() default 11;

    int min() default 8;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
