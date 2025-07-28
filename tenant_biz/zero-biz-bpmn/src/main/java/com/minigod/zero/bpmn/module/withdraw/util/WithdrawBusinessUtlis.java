package com.minigod.zero.bpmn.module.withdraw.util;

import cn.hutool.extra.spring.SpringUtil;
import com.minigod.zero.bpmn.module.withdraw.service.DbsFundBusinessService;
import com.minigod.zero.bpmn.module.withdraw.service.IBankPayingService;
import com.minigod.zero.dbs.protocol.AccountBalResponse;
import com.minigod.zero.bpmn.module.withdraw.bo.BankPayingBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 业务工具类
 *
 * @author marty
 * @title BusinessUtlis
 * @date 2023-04-06 17:34
 * @description
 */
public class WithdrawBusinessUtlis {

    /**
     * 根据币种查询银行账户余额
     *
     * @param accountNo
     * @return
     */
    public static Map<String, BigDecimal> queryBankAccountBalance(String tenantId,String accountNo){
        DbsFundBusinessService dbsFundBusinessService = SpringUtil.getBean(DbsFundBusinessService.class);
        List<AccountBalResponse>  dataList = dbsFundBusinessService.sendBleQuery(tenantId,accountNo);
        if(null != dataList && dataList.size() > 0){
            // 过滤 只需要 三种币种
            dataList = dataList.stream().filter(mapper -> SystemCommonEnum.MoneyType.getCname(mapper.getAccountCcy())!=null).collect(Collectors.toList());
            return dataList.stream().collect(Collectors.toMap(AccountBalResponse::getAccountCcy, AccountBalResponse::getClsAvailableBal));
        }
        return null;
    }

    /**
     * 查询DBS付款银行
     *
     * @return
     */
    public static BankPaying queryBankPayingEntity(String tenantId, String recvCcy){
        return queryBankPayingEntity("DBS", tenantId, recvCcy, SystemCommonEnum.YesNo.YES.getIndex());
    }

    /**
     * 查询付款银行
     *
     * @return
     */
    public static BankPaying queryBankPayingEntity(String bankCode, String tradeAcct, String recvCcy){
        IBankPayingService bankPayingService = SpringUtil.getBean(IBankPayingService.class);
        BankPayingBo bo = new BankPayingBo();
        bo.setBankCode(bankCode);
        bo.setCcy(recvCcy);
        bo.setServiceType("SEC");
        return bankPayingService.queryEntity(bo);
    }

    /**
     * 查询付款银行
     *
     * @return
     */
    public static BankPaying queryBankPayingEntity(String bankCode, String tenantId, String recvCcy, Integer isActive){
        IBankPayingService bankPayingService = SpringUtil.getBean(IBankPayingService.class);
        BankPayingBo bo = new BankPayingBo();
        bo.setBankCode(bankCode);
        bo.setCcy(recvCcy);
        bo.setServiceType("SEC");
        bo.setActive(isActive);
        bo.setTenantId(tenantId);
        return bankPayingService.queryEntity(bo);
    }

    /**
     * 是否禁止取款账户类型
     *
     * @return
     */
    public static boolean isForbitWtmAcctType(String tradeAcct){
//        DictService dictService = SpringUtil.getBean(DictService.class);
//        String label = dictService.getDictLabel(DictConstants.FORBID_WTM_ACCT_TYPE, AcctTypeUtils.getAcctType(tradeAcct));
//        if(StringUtils.isNotBlank(label)){
//            return true;
//        }
        return false;
    }

    /**
     * 是否黑名单客户
     *
     * @return
     */
    public static boolean isWtmBlackList(String tradeAcct){
//        DictService dictService = SpringUtil.getBean(DictService.class);
//        String label = dictService.getDictLabel(DictConstants.WTM_BLACK_LIST, tradeAcct);
//        if(StringUtils.isNotBlank(label)){
//            return true;
//        }
        return false;
    }

    /**
     * 银行账号校验
     *
     * @param acct
     * @return
     * @throws Exception
     */
    public static boolean checkBankAcct(String acct) throws Exception{
        if(StringUtils.isNotEmpty(acct)){
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
            return pattern.matches("[a-zA-Z0-9]+", acct);
        }
        return true;
    }

    /**
     * 柜台状态名称
     * @return
     */
    public static String getGtDealStatusName(Integer gtBusinessStep, Integer gtDealStatus){
//        StringBuffer buffer = new StringBuffer();
//        if(gtBusinessStep != null){
//            buffer.append(SystemCommonEnum.FundWithDrawStep.valueOf(gtBusinessStep).getDesc());
//            if(gtDealStatus != null){
//                buffer.append("-").append(SystemCommonEnum.CommonProcessResultStatus.valueOf(gtDealStatus).getDesc());
//                return buffer.toString();
//            }
//        }
        return null;
    }

}
