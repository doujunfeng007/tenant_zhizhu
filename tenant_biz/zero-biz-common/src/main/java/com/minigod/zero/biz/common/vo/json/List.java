
package com.minigod.zero.biz.common.vo.json;

import com.alibaba.fastjson2.annotation.JSONField;

public class List {

    @JSONField(name="art_code")
    private String mArtCode;
    @JSONField(name="codes")
    private java.util.List<Code> mCodes;
    @JSONField(name="columns")
    private java.util.List<Column> mColumns;
    @JSONField(name="display_time")
    private String mDisplayTime;
    @JSONField(name="notice_date")
    private String mNoticeDate;
    @JSONField(name="title")
    private String mTitle;

    public String getArtCode() {
        return mArtCode;
    }

    public void setArtCode(String artCode) {
        mArtCode = artCode;
    }

    public java.util.List<Code> getCodes() {
        return mCodes;
    }

    public void setCodes(java.util.List<Code> codes) {
        mCodes = codes;
    }

    public java.util.List<Column> getColumns() {
        return mColumns;
    }

    public void setColumns(java.util.List<Column> columns) {
        mColumns = columns;
    }

    public String getDisplayTime() {
        return mDisplayTime;
    }

    public void setDisplayTime(String displayTime) {
        mDisplayTime = displayTime;
    }

    public String getNoticeDate() {
        return mNoticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        mNoticeDate = noticeDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
