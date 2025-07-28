package com.minigod.zero.dbs.service;

/**
 * @ClassName: DbsCallBackApi
 * @Description:
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
public interface DbsCallBackApi {

    String TENANT_ID = "Tenant-Id";

    String PATH = "/dbs/callback";
    String ICN = "/icn";
    String IDN = "/idn";
    String EDDA_ACK2 = "/eddaAck2";
    String REMIT_ACK = "/remitAck";
}
