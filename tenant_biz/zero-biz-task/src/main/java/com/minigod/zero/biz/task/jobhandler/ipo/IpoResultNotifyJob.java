//package com.minigod.zero.biz.task.jobhandler.ipo;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.biz.common.cache.IpoAllotSmsFlag;
//import com.minigod.zero.biz.common.cache.IpoNotAllotSmsFlag;
//import com.minigod.zero.biz.common.constant.CommonConstant;
//import com.minigod.zero.biz.common.constant.OpenApiConstant;
//import com.minigod.zero.biz.common.enums.IpoZqTypeEnum;
//import com.minigod.zero.biz.common.utils.CommonUtil;
//import com.minigod.zero.biz.common.utils.ProtocolUtils;
//import com.minigod.zero.biz.common.utils.RestTemplateUtil;
//import com.minigod.zero.biz.common.vo.CommonReqVO;
//import com.minigod.zero.biz.common.vo.ipo.IPOClientApply;
//import com.minigod.zero.biz.common.vo.ipo.PageDto;
//import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
//import com.minigod.zero.biz.task.service.IBpmAccountInfoService;
//import com.minigod.zero.biz.task.service.ICustDeviceService;
//import com.minigod.zero.biz.task.service.IIpoSmsZqInfoService;
//import com.minigod.zero.biz.task.vo.BpmAccountInfoVo;
//import com.minigod.zero.core.redis.cache.ZeroRedis;
//import com.minigod.zero.core.tool.api.R;
//import com.minigod.zero.core.tool.utils.DateUtil;
//import com.minigod.zero.core.tool.utils.StringUtil;
//import com.minigod.zero.cust.apivo.CustTradePushInfoVO;
//import com.minigod.zero.cust.entity.CustDeviceEntity;
//import com.minigod.zero.cust.feign.ICustTradeClient;
//import com.minigod.zero.platform.constants.CommonTemplateCode;
//import com.minigod.zero.platform.dto.SendEmailDTO;
//import com.minigod.zero.platform.dto.SendNotifyDTO;
//import com.minigod.zero.platform.enums.InformEnum;
//import com.minigod.zero.platform.enums.MsgStaticType;
//import com.minigod.zero.platform.feign.IPlatformMsgClient;
//import com.minigod.zero.platform.feign.IPlatformSysMsgClient;
//import com.minigod.zero.platform.vo.PlatformSysMsgVO;
//import com.minigod.zero.trade.entity.HkipoInfoEntity;
//import com.minigod.zero.trade.entity.IpoSmsZqInfoEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * IPO 结果通知
// */
//@Slf4j
//@Component
//public class IpoResultNotifyJob {
//
//    @Resource
//    private ZeroRedis zeroRedis;
//    @Resource
//    private IBpmAccountInfoService bpmAccountInfoService;
//    @Resource
//    private IIpoSmsZqInfoService ipoSmsZqInfoService;
//    @Resource
//    private ICustDeviceService custDeviceService;
//    @Resource
//    private IPlatformSysMsgClient platformSysMsgClient;
//    @Resource
//    private IPlatformMsgClient platformMsgClient;
//    @Resource
//    private RestTemplateUtil restTemplateUtil;
//    @Resource
//    private ICustTradeClient custTradeClient;
//
//    private volatile ReentrantLock lock = new ReentrantLock();
//    private volatile ReentrantLock lockWin = new ReentrantLock();
//
//    /* IPO 中签通知 **/
//    @XxlJob("ipoWinBid")
//    void ipoWinBid() {
//        try {
//            if (lockWin.tryLock(1, TimeUnit.SECONDS)) {
//                log.info("开始发送IPO中签通知");
//                IpoAllotSmsFlag ipoAllotSmsFlag = zeroRedis.get(IpoAllotSmsFlag.class.getSimpleName());
//                if (ipoAllotSmsFlag == null) {
//                    String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_ZQ_IMPORT, CommonUtil.getRequestJson(new HashMap<>()));
//                    log.info("IPO_ZQ_IMPORT.url={}, response={}", OpenApiConstant.IPO_ZQ_IMPORT, jsonResult);
//                    if (!StringUtil.isBlank(jsonResult)) {
//                        if (CommonUtil.checkCode(jsonResult)) {
//                            String result = CommonUtil.returnResult(jsonResult);
//                            if (!StringUtil.isBlank(result)) {
//                                boolean isImported = (boolean) JSONObject.parseObject(result).get("isImported");
//                                if (isImported) {
//                                    log.info("sleep 5 minutes, wait to import");
//                                    Thread.sleep(3000);
//                                    doZqNotify();
//                                }
//                            }
//                        }
//                    }
//                }
//                log.info("发送IPO中签通知结束");
//                XxlJobHelper.handleSuccess();
//            }
//        } catch (Exception e) {
//            log.error("发送中签通知异常", e);
//            XxlJobHelper.handleFail();
//        } finally {
//            lockWin.unlock();
//        }
//    }
//
//    /* IPO 未中签通知 **/
//    @XxlJob("ipoFailed")
//    void ipoFailed() {
//        try {
//            if (lock.tryLock(1, TimeUnit.SECONDS)) {
//                log.info("开始发送IPO未中签通知");
//                IpoNotAllotSmsFlag ipoNotAllotSmsFlag = zeroRedis.get(IpoNotAllotSmsFlag.class.getSimpleName());
//                if (ipoNotAllotSmsFlag == null) {
//                    String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_ZQ_IMPORT, CommonUtil.getRequestJson(new HashMap<>()));
//                    log.info("IPO_N_ZQ_LIST.url={}, response={}", OpenApiConstant.IPO_ZQ_IMPORT, jsonResult);
//                    if (!StringUtil.isBlank(jsonResult)) {
//                        if (CommonUtil.checkCode(jsonResult)) {
//                            String result = CommonUtil.returnResult(jsonResult);
//                            if (!StringUtil.isBlank(result)) {
//                                boolean isImported = (boolean) JSONObject.parseObject(result).get("isImported");
//                                if (isImported) {
//                                    log.info("sleep 5 minutes, wait to import");
//                                    Thread.sleep(3000);
//                                    doNotZqNotify();
//                                }
//                            }
//                        }
//                    }
//                }
//                log.info("发送IPO未中签通知结束");
//                XxlJobHelper.handleSuccess();
//            }
//        } catch (Exception e) {
//            log.error("发送未中签通知异常", e);
//            XxlJobHelper.handleFail();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    void doNotZqNotify() {
//        // 获取交易系统未中签数据
//        JSONObject jsonObject = new JSONObject();
//        JSONObject params = new JSONObject();
//        jsonObject.put("params", params);
//        int pageSize = 500;// 每100条记录28k
//        int totalPages = 1;
//        int totalCount = 0;// 总记录数
//        for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
//            params.put("pageNo", pageNo);
//            params.put("pageSize", pageSize);
//            String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_N_ZQ_LIST, jsonObject.toJSONString());
//            if (CommonUtil.checkCode(jsonResult)) {
//                String result = CommonUtil.returnResult(jsonResult);
//                if (StringUtil.isNotBlank(result)) {
//                    PageDto<IPOClientApply> pageDto = JSON.parseObject(result, new TypeReference<PageDto<IPOClientApply>>() {
//                    });
//                    totalPages = pageDto.getTotalPages();
//                    totalCount = pageDto.getTotalCount();
//                    if (pageNo == 1) {
//                        //只打印第一页数据
//                        log.info("IPO_N_ZQ_LIST.pageDto totalPages={}, totalCount={}", pageDto.getTotalPages(), pageDto.getTotalCount());
//                    }
//                    pushListMessage(pageDto.getList(), IpoZqTypeEnum.IPO_NOT_ZQ.getCode());
//                }
//            }
//        }
//        if (totalCount > 0) {
//            //处理完未中签记录的情况
//            IpoNotAllotSmsFlag ipoNotAllotSmsFlag = new IpoNotAllotSmsFlag();
//            int seconds = ProtocolUtils.getTodayRemainSeconds();
//            zeroRedis.setEx(IpoNotAllotSmsFlag.class.getSimpleName(), ipoNotAllotSmsFlag, Long.valueOf(seconds));
//        }
//    }
//
//    void doZqNotify() {
//        // 获取交易系统已中签数据
//        JSONObject jsonObject = new JSONObject();
//        JSONObject params = new JSONObject();
//        jsonObject.put("params", params);
//        int pageSize = 500;// 每100条记录28k
//        int totalPages = 1;
//        int totalCount = 0;// 总记录数
//        for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
//            params.put("pageNo", pageNo);
//            params.put("pageSize", pageSize);
//            String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_ZQ_LIST, jsonObject.toJSONString());
//            log.info("doZqNotify jsonResult=" + JSON.toJSON(jsonResult));
//            if (CommonUtil.checkCode(jsonResult)) {
//                String result = CommonUtil.returnResult(jsonResult);
//                if (!StringUtil.isBlank(result)) {
//                    PageDto<IPOClientApply> pageDto = JSON.parseObject(result, new TypeReference<PageDto<IPOClientApply>>() {
//                    });
//                    totalPages = pageDto.getTotalPages();
//                    totalCount = pageDto.getTotalCount();
//                    if (pageNo == 1) {
//                        //只打印第一页数据
//                        log.info("IPO_ZQ_LIST.pageDto totalPages={}, totalCount={}", pageDto.getTotalPages(), pageDto.getTotalCount());
//                    }
//                    pushListMessage(pageDto.getList(), IpoZqTypeEnum.IPO_ZQ.getCode());
//                }
//            }
//        }
//        if (totalCount > 0) {
//            //处理完中签记录的情况
//            IpoAllotSmsFlag ipoAllotSmsFlag = new IpoAllotSmsFlag();
//            int seconds = ProtocolUtils.getTodayRemainSeconds();
//            zeroRedis.setEx(IpoAllotSmsFlag.class.getSimpleName(), ipoAllotSmsFlag, Long.valueOf(seconds));
//        }
//    }
//
//    // type: 0 未中签，1 已中签
//    void pushListMessage(List<IPOClientApply> ipoClientApply, int type) {
//        if (CollectionUtils.isEmpty(ipoClientApply)) {
//            return;
//        }
//        for (IPOClientApply apply : ipoClientApply) {
//            String assetId = apply.getAssetId();
//            String clientId = apply.getClientId();
//            String fundAccount = apply.getFundAccount();
//            int quantityAllotted = apply.getQuantityAllotted(); // 中签数量
//            int quantityApply = apply.getQuantityApply(); // 申购数量
//            BpmAccountInfoVo infoVo = bpmAccountInfoService.findOneByClientId(clientId);
//            if (null == infoVo) {
//                log.error("findOneByClientId.failed clientId={}", clientId);
//                continue;
//            }
//            IpoSmsZqInfoEntity smsZqInfoEntity = findIpoSmsObj(infoVo.getCustId(), clientId, fundAccount, assetId, type, quantityAllotted);
//            HkipoInfoEntity hkIpoInfo = null;
//            String lang = CommonConstant.ZH_CN;
//            try {
//                CommonReqVO reqVO = new CommonReqVO();
//                IpoVO ipoVo = new IpoVO();
//                ipoVo.setAssetId(assetId);
//                reqVO.setParams(ipoVo);
//                hkIpoInfo = restTemplateUtil.postSend(OpenApiConstant.HK_IPO_INFO_URL, HkipoInfoEntity.class, reqVO);
//                if (null == smsZqInfoEntity) {
//                    SendNotifyDTO sendNotifyDTO = new SendNotifyDTO();
//                    sendNotifyDTO.setLstToUserId(Arrays.asList(infoVo.getCustId()));
//                    CustDeviceEntity custDevice = custDeviceService.getCustDevice(sendNotifyDTO.getLstToUserId().get(0));
//                    if (custDevice != null && StringUtil.isNotBlank(custDevice.getLang())) {
//                        lang = custDevice.getLang();
//                    }
//                    sendNotifyDTO.setLang(lang);
//                    sendNotifyDTO.setTemplateCode(IpoZqTypeEnum.IPO_ZQ.getCode() == type ? InformEnum.MsgTempEnum.IPO_ZQ_SUCCESS.getCode() : InformEnum.MsgTempEnum.IPO_WZQ_SUCCESS.getCode());
//                    List<String> paramList = new ArrayList<>();
//                    if (IpoZqTypeEnum.IPO_ZQ.getCode() == type) {
//                        paramList.add(String.valueOf(quantityApply));
//                        if (null != hkIpoInfo) {
//                            paramList.add(hkIpoInfo.getShortName());
//                        }
//                        paramList.add(assetId);
//                        paramList.add(String.valueOf(quantityAllotted));
//                    } else {
//                        paramList.add(String.valueOf(quantityApply));
//                        if (null != hkIpoInfo) {
//                            paramList.add(hkIpoInfo.getShortName());
//                        }
//                        paramList.add(assetId);
//                        // 返款金额
//                        String refundAmount = String.valueOf(apply.getRefundAmount());
//                        paramList.add(refundAmount);
//                    }
//                    sendNotifyDTO.setParams(paramList);
//                    int displayGroup = MsgStaticType.DisplayGroup.STK_NEW_REMIND;
//                    sendNotifyDTO.setDisplayGroup(displayGroup);
//                    PlatformSysMsgVO newSysMsg = new PlatformSysMsgVO();
//                    newSysMsg.setTempCode(sendNotifyDTO.getTemplateCode());
//                    newSysMsg.setTargetId(sendNotifyDTO.getLstToUserId().get(0));
//                    newSysMsg.setParams(sendNotifyDTO.getParams());
//                    newSysMsg.setLang(lang);
//                    platformSysMsgClient.saveSysMsg(newSysMsg);
//                    R r = platformMsgClient.sendNotify(sendNotifyDTO);
//                    if (r.isSuccess()) {
//                        IpoSmsZqInfoEntity smsZqInfo = new IpoSmsZqInfoEntity();
//                        smsZqInfo.setCustId(infoVo.getCustId());
//                        smsZqInfo.setAssetId(assetId);
//                        smsZqInfo.setClientId(clientId);
//                        smsZqInfo.setFundAccount(fundAccount);
//                        smsZqInfo.setQuantityAllotted(quantityAllotted);
//                        smsZqInfo.setIsSend(true);
//                        smsZqInfo.setCreateTime(new Date());
//                        smsZqInfo.setType(type);
//                        ipoSmsZqInfoService.save(smsZqInfo);
//                    }
//                }
//            } catch (Exception e) {
//                log.error("发送给客户[{}]{}通知异常:{}", clientId, (type == 1 ? "中签" : "未中签"), e);
//            }
//
//            try {
//                if (null == smsZqInfoEntity) {
//                    R<CustTradePushInfoVO>  r = custTradeClient.getCustTradePushInfo(fundAccount);
//                    if(!r.isSuccess() || r.getData() == null) {
//                        log.error("获取客户有效账号信息失败" + fundAccount);
//                        return;
//                    }
//                    CustTradePushInfoVO tradePushInfo = r.getData();
//                    //发送邮件
//                    List<String> paramList = new ArrayList<>();
//                    if (IpoZqTypeEnum.IPO_ZQ.getCode() == type) {
//                        if (null != hkIpoInfo) {
//                            paramList.add(hkIpoInfo.getShortName());
//                        }
//                        paramList.add(assetId);
//                        paramList.add(String.valueOf(quantityAllotted));
//                    }else {
//                        paramList.add(String.valueOf(quantityApply));
//                        if (null != hkIpoInfo) {
//                            paramList.add(hkIpoInfo.getShortName());
//                        }
//                        paramList.add(assetId);
//                        // 返款金额
//                        String refundAmount = String.valueOf(apply.getRefundAmount());
//                        paramList.add(refundAmount);
//                    }
//                    SendEmailDTO sendDto = new SendEmailDTO();
//                    List<String> accepts = new ArrayList<>();
//                    accepts.add(tradePushInfo.getEmail());
//                    sendDto.setAccepts(accepts);
//                    sendDto.setCode(IpoZqTypeEnum.IPO_ZQ.getCode() == type?CommonTemplateCode.Email.IPO_ZQ_SUCCESS:CommonTemplateCode.Email.IPO_WZQ_SUCCESS);
//                    sendDto.setList(DateUtil.getddMMHHmm());
//                    sendDto.setLang(lang);
//                    sendDto.setList(paramList);
//                    platformMsgClient.sendEmail(sendDto);
//                }
//            } catch (Exception e) {
//                log.error("发送给客户[{}]{}邮件通知异常:{}", clientId, (type == 1 ? "中签" : "未中签"), e);
//            }
//
//        }
//    }
//
//    IpoSmsZqInfoEntity findIpoSmsObj(Long custId, String clientId, String fundAccount, String assetId, int type,
//                                     int quantityAllotted) {
//        IpoSmsZqInfoEntity params = new IpoSmsZqInfoEntity();
//        params.setCustId(custId);
//        params.setClientId(clientId);
//        params.setFundAccount(fundAccount);
//        params.setAssetId(assetId);
//        params.setIsSend(true);
//        params.setType(type);
//        params.setQuantityAllotted(quantityAllotted);
//        IpoSmsZqInfoEntity entity = ipoSmsZqInfoService.queryObjectByBean(params);
//        return entity;
//    }
//}
