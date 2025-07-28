package com.minigod.zero.trade.hs.constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sunline on 2016/4/23 18:02.
 * 功能描述信息
 * sunline
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GrmFunctionEntity {
    Class requestVo();
    Class responseVo();
}
