package com.minigod.zero.trade.hs.service;

import org.springframework.stereotype.Component;

/**
 * Created by sunline on 2016/6/13 17:20.
 * sunline
 */
@Component
public interface FieldFormater {
    String format(String extFieldCode,String value);
}
