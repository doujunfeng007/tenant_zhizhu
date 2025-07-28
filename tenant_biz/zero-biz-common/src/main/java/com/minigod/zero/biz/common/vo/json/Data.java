
package com.minigod.zero.biz.common.vo.json;

import com.alibaba.fastjson2.annotation.JSONField;

@SuppressWarnings("unused")
public class Data {

    @JSONField(name = "list")
    private java.util.List<List> mList;
    @JSONField(name = "page_index")
    private Long mPageIndex;
    @JSONField(name = "page_size")
    private Long mPageSize;
    @JSONField(name = "total_hits")
    private Long mTotalHits;

    public java.util.List<List> getList() {
        return mList;
    }

    public void setList(java.util.List<List> list) {
        mList = list;
    }

    public Long getPageIndex() {
        return mPageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        mPageIndex = pageIndex;
    }

    public Long getPageSize() {
        return mPageSize;
    }

    public void setPageSize(Long pageSize) {
        mPageSize = pageSize;
    }

    public Long getTotalHits() {
        return mTotalHits;
    }

    public void setTotalHits(Long totalHits) {
        mTotalHits = totalHits;
    }

}
