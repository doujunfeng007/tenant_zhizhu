package com.minigod.zero.bpmn.module.withdraw.validator;

/**
 * 银行账户校验
 *
 * @author chenyu
 * @title RecvBankAcctAndTypeValid
 * @date 2023-05-17 21:17
 * @description
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ PARAMETER, FIELD, METHOD })
@Constraint(validatedBy = RecvBankAcctAndTypeValidator.class)
public @interface RecvBankAcctAndTypeValid {

    String message() default "收款银行账号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
