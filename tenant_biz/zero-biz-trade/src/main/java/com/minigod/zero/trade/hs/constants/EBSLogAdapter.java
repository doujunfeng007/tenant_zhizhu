package com.minigod.zero.trade.hs.constants;

import com.hundsun.t2sdk.impl.util.AbstractLogAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sunline on 2016/4/20 10:26.
 * sunline
 */
@Slf4j
public class EBSLogAdapter extends AbstractLogAdapter {
    public void export(String paramString) {
        log.debug(paramString);
    }
}
