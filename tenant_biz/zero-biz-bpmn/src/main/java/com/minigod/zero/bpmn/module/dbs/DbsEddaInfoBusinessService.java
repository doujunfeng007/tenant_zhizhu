package com.minigod.zero.bpmn.module.dbs;


import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;

public interface DbsEddaInfoBusinessService {

    /**
     * 请求查edda授权
     * @param eddaInfo
     * @param createBy
     */
    void sendEDDAInitiation(ClientEddaInfoApplicationEntity eddaInfo, String createBy);

    /**
     * 请求DBS eDDA授权查询
     * @param eddaInfo
     * @param updateBy
     * @return
     */
    boolean sendEDDAEnquiry(ClientEddaInfoApplicationEntity eddaInfo, String updateBy);

}
