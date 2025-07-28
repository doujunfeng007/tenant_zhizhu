package com.minigod.zero.bpmn.module.withdraw.validator;

import com.minigod.zero.bpmn.module.account.constants.RegexpConstants;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import java.util.Set;

/**
 * Validator 校验框架工具
 *
 * @author zsdp
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtils {

    // 香港身份证
    public static String REGEX_HKCARD = "^[A-Z]{1,2}[0-9]{6}[0-9A]$";
    // 香港电话号码
    public static String REGEX_HK_MOBILE = "\\+[0-9]{1,3}-[0-9]{1,29}";
    // 仅仅包含字母和数字
    public static String LETTER_DIGIT_REGEX = "^[a-z0-9A-Z]+$";

    private static final Validator validator;
    static {
        try (ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
            .configure()
            // 快速失败
            .failFast(true)
            .buildValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public static <T> Set<ConstraintViolation<T>> validateFailFast(T bean, Class<?>... groups) {
        return validator.validate(bean, groups);
    }

    /**
     * 香港身份证格式 未校验有效性
     * @param idCard
     * @return
     */
    public static boolean isHkIdCard(String idCard){
        if(StringUtil.isNotBlank(idCard)){
            return idCard.matches(REGEX_HKCARD);
        }
        return false;
    }

    /**
     * 香港 FPS 手机号码
     * Example: +852-12345678
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone){
        if(StringUtil.isNotBlank(phone)){
            return phone.matches(REGEX_HK_MOBILE);
        }
        return false;
    }

    /**
     * 判断字符串是否仅含有数字和字母
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        if(StringUtil.isNotBlank(str)){
            return str.matches(LETTER_DIGIT_REGEX);
        }
        return false;
    }

    /**
     * 判断字符串是否仅含有数字和字母
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if(StringUtil.isNotBlank(str)){
            return str.matches(RegexpConstants.REGEX_NUMBER);
        }
        return false;
    }

    /**
     * 判断是否为银行卡
     *
     * @param str
     * @return
     */
    public static boolean isBankCard(String str) {
        if(StringUtil.isNotBlank(str)){
            return str.matches(RegexpConstants.REGEX_ENG_NUMBER_DASH);
        }
        return false;
    }


    /**
     * 判断是否为邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if(StringUtil.isNotBlank(str)){
            return str.matches(RegexpConstants.REGEX_EMAIL);
        }
        return false;
    }
}
