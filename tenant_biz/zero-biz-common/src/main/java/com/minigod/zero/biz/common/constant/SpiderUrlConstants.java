package com.minigod.zero.biz.common.constant;

import com.minigod.zero.core.tool.enums.EMarket;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author sunline on 2016/9/1 16:57.
 * sunline
 */
public class SpiderUrlConstants {

    public static final class QQ{
        //大行研报
        public static final String HK_DHYB = "http://stock.qq.com/l/hk/ggxw/list20150520152324.htm";
        //要问
        public static final String AB_YW = "http://stock.qq.com/";
        //直博
        public static final String HK_ZB   = "http://stock.qq.com/l/hk/list20150520151617.htm";
        //机构研报
        public static final String HK_JGYB = "http://stock.qq.com/l/hk/ggyjbg/list20150520152355.htm";
        //港股专栏
        public static final String HK_GGZL = "http://stock.qq.com/l/hk/ggyjbg/list20150520152355.htm";
        //论证咨询
        public static final String HK_LZZX = "http://stock.qq.com/l/hk/qzjm/list20150520152430.htm";
        //美股新闻
        public static final String US_MGXW = "http://stock.qq.com/l/usstock/huanqiucaijing/list20150520144736.htm";
        //中国概念股
        public static final String US_GNG = "http://stock.qq.com/l/usstock/xwzx/list20150520145033.htm";
        //分析评论
        public static final String US_FXPL = "http://stock.qq.com/l/usstock/meigupinglun/list20150520145103.htm";
        //盘面报道
        public static final String US_PMBD = "http://stock.qq.com/l/usstock/panmianbaodao/list20150520145407.htm";
        //滚动新闻
        public static final String US_GDXW = "http://stock.qq.com/l/usstock/usgdyw/list20150520144638.htm";
        //美股课堂
        public static final String US_MGKT = "http://stock.qq.com/l/usstock/us_stock_market_classroom/list20150520145519.htm";

        public static final Map<String,String> urlMarketMap = new HashMap<>();

        static{
            urlMarketMap.put(HK_DHYB, EMarket.HK.name());
            urlMarketMap.put(HK_ZB, EMarket.HK.name());
            urlMarketMap.put(HK_JGYB, EMarket.HK.name());
            urlMarketMap.put(HK_GGZL, EMarket.HK.name());
            urlMarketMap.put(HK_LZZX, EMarket.HK.name());

            urlMarketMap.put(US_MGXW, EMarket.US.name());
            urlMarketMap.put(US_GNG, EMarket.US.name());
            urlMarketMap.put(US_FXPL, EMarket.US.name());
            urlMarketMap.put(US_PMBD, EMarket.US.name());
            urlMarketMap.put(US_GDXW, EMarket.US.name());
            urlMarketMap.put(US_MGKT, EMarket.US.name());
        }

        public static String getMktCodeByUrl(String url){
            if(StringUtils.isNotEmpty(url)){
                return urlMarketMap.get(url);
            }
            return "";
        }

    }

    public static final class EastMoney{
        //港股要闻
        public static final String HK_YW= "http://hk.eastmoney.com/news/cggdd.html";
    }
}
