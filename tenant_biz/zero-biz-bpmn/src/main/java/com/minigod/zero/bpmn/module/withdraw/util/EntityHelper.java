package com.minigod.zero.bpmn.module.withdraw.util;

import cn.hutool.core.bean.BeanUtil;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.bpmn.module.withdraw.vo.WithdrawInfoReportVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体帮助类
 *
 * @author chenyu
 * @title EntityHelper
 * @date 2023-04-14 22:40
 * @description
 */
@Slf4j
public class EntityHelper {

    /**
     * 转为报表对象
     * @param infoList
     * @return
     */
    public static List<WithdrawInfoReportVo> convertInfoReportList(List<ClientFundWithdrawApplicationVo> infoList){
//        IClientInfoService clientInfoService = SpringUtils.getBean(IClientInfoService.class);
        List<WithdrawInfoReportVo> reportDtos = null;
        if(infoList != null && infoList.size() > 0){
            reportDtos = infoList.stream().map(mappper ->{
                WithdrawInfoReportVo reportDto = BeanUtil.toBean(mappper.getInfo(), WithdrawInfoReportVo.class);
                reportDto.setApplicationId(mappper.getApplicationId());
                reportDto.setApplicationStatus(mappper.getApplicationStatus());
                reportDto.setStatus(mappper.getStatus());
                reportDto.setGtDealStatusName(mappper.getGtDealStatusName());
                if(mappper.getTelegram() != null){
                    reportDto.setProvinceName(mappper.getTelegram().getProvinceName());
                    reportDto.setCityName(mappper.getTelegram().getCityName());
                }
                if(mappper.getTelegram() != null){
                    reportDto.setNationality(mappper.getTelegram().getNationality());
                }

                // 查询客户信息- 见证人 渠道 地址
//                ClientInfoVo clientInfoVo = clientInfoService.queryEntity(mappper.getInfo().getFundAccount());
//                if(null != clientInfoVo){
//                    reportDto.setWitnessName(clientInfoVo.getWitnessName());
//                    reportDto.setFamilyAddr(clientInfoVo.getFamilyAddr());
//                    reportDto.setOrgName(clientInfoVo.getOrgName());
//                }
                return reportDto;
            }).collect(Collectors.toList());
            log.info("convert result: {}", reportDtos.toString());
        }
        return reportDtos;
    }
    public static List<WithdrawInfoReportVo> convertInfoReportList1(List<ClientFundWithdrawApplicationVo> infoList){
//        IClientInfoService clientInfoService = SpringUtils.getBean(IClientInfoService.class);
        List<WithdrawInfoReportVo> reportDtos = null;
        if(infoList != null && infoList.size() > 0){
            reportDtos = infoList.stream().map(mappper ->{
                WithdrawInfoReportVo reportDto = BeanUtil.toBean(mappper.getInfo(), WithdrawInfoReportVo.class);
                reportDto.setApplicationId(mappper.getApplicationId());
                reportDto.setApplicationStatus(mappper.getApplicationStatus());
                reportDto.setGtDealStatusName(mappper.getGtDealStatusName());
                if(mappper.getTelegram() != null){
                    reportDto.setProvinceName(mappper.getTelegram().getProvinceName());
                    reportDto.setCityName(mappper.getTelegram().getCityName());
                }
                if(mappper.getTelegram() != null){
                    reportDto.setNationality(mappper.getTelegram().getNationality());
                }
                return reportDto;
            }).collect(Collectors.toList());
            log.info("convert result: {}", reportDtos.toString());
        }
        return reportDtos;
    }

    /**
     * 映射Map
     * @return
     */
//    public static Map<String, Object> convertDataMap(ClientFundWithdrawApplicationVo fundWithdrawApplicationVo) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            if (fundWithdrawApplicationVo != null) {
//                ClientFundWithdrawInfoVo fundWithdrawInfoVo = fundWithdrawApplicationVo.getInfo();
//                map.put("applyDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, fundWithdrawInfoVo.getEntrustTime()));
//                map.put("acctCode", fundWithdrawInfoVo.getFundAccount());
//                map.put("custName", fundWithdrawInfoVo.getCustName());
//
//                String custEName = fundWithdrawInfoVo.getCustEname();
//                map.put("custEName",fundWithdrawInfoVo.getCustEname());
//
//                map.put("ccy", fundWithdrawInfoVo.getCcy());
//                map.put("amount", fundWithdrawInfoVo.getWithdrawAmount());
//                map.put("transferType", SystemCommonEnum.TransferTypeEnum.getName(fundWithdrawInfoVo.getTransferType()));
//                map.put("deductWay", SystemCommonEnum.DeductWay.getName(fundWithdrawInfoVo.getDeductWay()));
//                map.put("chargeFee", fundWithdrawInfoVo.getChargeFee());
//                map.put("drawableBalance", NumberToRmbUtil.getCnNumber(fundWithdrawInfoVo.getDrawableBalance()));
//                map.put("actualAmount", fundWithdrawInfoVo.getActualAmount());
//                map.put("recvBankName", fundWithdrawInfoVo.getRecvBankName());
//                map.put("swiftCode", fundWithdrawInfoVo.getRecvSwiftCode());
//                map.put("recvBankAcctName", fundWithdrawInfoVo.getRecvBankAcctName());
//                map.put("recvBankAcct", fundWithdrawInfoVo.getRecvBankAcct());
//
//                ClientTeltransferInfoVo teltransferInfoVo = fundWithdrawApplicationVo.getTelegram();
//                if (teltransferInfoVo != null) {
//                    map.put("recvBankAddr", teltransferInfoVo.getAddress());
//                }
//                map.put("payBankName", fundWithdrawInfoVo.getPayBankName());
//                map.put("payBankAcctName", fundWithdrawInfoVo.getPayAccountName());
//                map.put("payBankAcct", fundWithdrawInfoVo.getPayBankAcct());
//
//                if (SystemCommonEnum.YesNo.YES.getIndex() == fundWithdrawInfoVo.getThirdRecvFlag()) {
//                    map.put("recvOther", "是");
//                    map.put("relationship", fundWithdrawInfoVo.getThirdRecvReal());
//                    map.put("recvReason", fundWithdrawInfoVo.getThirdRecvReason());
//                } else  {
//                    map.put("recvOther", "否");
//                }
//
//                //账户号码_中文名_英文名_取款金额
//                String cname = "";
//                try {
//                    cname = new String(fundWithdrawInfoVo.getCustName().getBytes("GBK"), "ISO-8859-1");
//                } catch (UnsupportedEncodingException e) {
//                    cname = "";
//                }
//                map.put("displayName", fundWithdrawInfoVo.getFundAccount() + "_" + cname + "_" + fundWithdrawInfoVo.getCustEname() +
//                    "_" + MathUtils.hold3Decimal(fundWithdrawInfoVo.getActualAmount()) + ".pdf");
//
//                map.put("payWay", SystemCommonEnum.TransferTypeEnum.getName(fundWithdrawInfoVo.getPayWay()));
//            }
//        } catch (Exception e) {
//            log.error("实体转换Map失败", e);
//        }
//
//        return map;
//    }

}
