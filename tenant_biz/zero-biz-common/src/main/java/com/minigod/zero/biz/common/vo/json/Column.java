
package com.minigod.zero.biz.common.vo.json;

import com.alibaba.fastjson2.annotation.JSONField;

public class Column {

    @JSONField(name = "column_code")
    private String mColumnCode;
    @JSONField(name = "column_name")
    private String mColumnName;

    public String getColumnCode() {
        return mColumnCode;
    }

    public void setColumnCode(String columnCode) {
        mColumnCode = columnCode;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public void setColumnName(String columnName) {
        mColumnName = columnName;
    }

}
