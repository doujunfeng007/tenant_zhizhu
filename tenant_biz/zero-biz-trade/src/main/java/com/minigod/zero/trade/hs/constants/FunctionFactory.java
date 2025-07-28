package com.minigod.zero.trade.hs.constants;

import cn.hutool.core.io.resource.ResourceUtil;
import com.minigod.zero.trade.hs.resp.FunctionEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sunline on 2016/4/20 10:24.
 * sunline
 */
@Slf4j
public class FunctionFactory {
    private static HashMap<Integer, FunctionEntity> funsMap = new HashMap();

    static {
        if (MapUtils.isEmpty(funsMap)) {
            log.info(">>>>>>>>>>>>首次调用载入功能配置文件");
            BufferedReader br = null;
            try {
				InputStream inputStream = ResourceUtil.getStream("hs/contents.txt");
				br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				String strLine = "";
				FunctionEntity func = new FunctionEntity();
				while ((strLine = br.readLine()) != null) {
					//System.out.println(strLine);
					if (!strLine.trim().equals("")) {
						if (strLine.startsWith("FunctionNumber")) {
							if ((func.getMiFuncNo() != 0) && (func.getAsRequestField() != null) && (func.getAsResponseField() != null)) {
								funsMap.put(Integer.valueOf(func.getMiFuncNo()),func);
								func = new FunctionEntity();
							} else if (func.getMiFuncNo() != 0) {
								func = new FunctionEntity();
							}
							func.setMiFuncNo(Integer.parseInt(strLine
								.contains("：") ? strLine.split("：")[1]
								.trim() : strLine.split(":")[1].trim()));
						} else if (strLine.startsWith("description")) {
							func.setFunDesc(strLine.contains("：") ? strLine
								.split("：")[1].trim() : strLine
								.split(":")[1].trim());
						} else if (strLine.startsWith("RequestParamsList")) {
							if (strLine.contains("<SOH>")) {
								func.setAsRequestField(strLine
									.contains("：") ? strLine.split("：")[1]
									.trim().split("<SOH>") : strLine
									.split(":")[1].trim()
									.split("<SOH>"));
							}
						} else if (strLine.startsWith("responseParamsList")) {
							if (strLine.contains("<SOH>")) {
								func.setAsResponseField(strLine
									.contains("：") ? strLine.split("：")[1]
									.trim().split("<SOH>") : strLine
									.split(":")[1].trim()
									.split("<SOH>"));
							}else{
								func.setAsResponseField(new String[]{});
							}
						} else if (strLine.startsWith("ChineseFields")) {
							if (!strLine.trim().endsWith("：")
								&& ! strLine.trim().endsWith(":")) {
								String[] chnFields = strLine.contains("：") ? strLine
									.split("：")[1].trim()
									.split("<SOH>") : strLine
									.split(":")[1].trim()
									.split("<SOH>");
								Set chnSet = new HashSet();
								for (int i = 0; i < chnFields.length; i++) {
									chnSet.add(chnFields[i]);
								}
								func.setAsChtField(chnSet);
							}
						}
					}
				}
				if ((func.getMiFuncNo() != 0)
					&& (func.getAsRequestField() != null)
					&& (func.getAsResponseField() != null)) {
					funsMap.put(Integer.valueOf(func.getMiFuncNo()), func);
				}
				func.getMiFuncNo();
            } catch (Exception e) {
                log.error("加载contents文件失败",e);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    br = null;
                }
            }
        }
    }

    public static FunctionEntity getFuncById(int iFuncNo) {
        FunctionEntity __entity = funsMap.get(Integer.valueOf(iFuncNo));
        if (__entity == null) {
            System.out.println("Can not find FunctionEntity! iFuncNo=" + iFuncNo);
            return null;
        }
        return __entity;
    }

    public static HashMap getFuncMap() {
        return funsMap;
    }
}
