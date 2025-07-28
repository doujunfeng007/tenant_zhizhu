package com.minigod.zero.dbs.bo;

import lombok.Data;

/**
 * FpsId账户查询
 *
 * @author chenyu
 * @title FpsIdQueryBo
 * @date 2023-04-14 13:19
 * @description
 */
@Data
public class FpsIdQueryBo extends DbsBaseRequestVO {

    private String bankAcctType;
    private String bankAcct;
    private String bankAcctName;
    private String bankId;
    private String bankNo;

}
