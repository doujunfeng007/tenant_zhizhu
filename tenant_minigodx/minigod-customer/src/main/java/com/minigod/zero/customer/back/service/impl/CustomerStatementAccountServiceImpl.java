package com.minigod.zero.customer.back.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.zero.bpmn.module.common.entity.Address;
import com.minigod.zero.bpmn.module.feign.IAddrClient;
import com.minigod.zero.bpmn.module.feign.IBpmnWithdrawClient;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.Jsonfilter.BigDecimalValueFilter;
import com.minigod.zero.customer.back.service.CustomerStatementAccountService;
import com.minigod.zero.customer.back.service.ICustomerInfoService;
import com.minigod.zero.customer.back.service.TExchRateHisService;
import com.minigod.zero.customer.config.CustomerStatementAccountPdfProperties;
import com.minigod.zero.customer.config.PDFTemplate;
import com.minigod.zero.customer.dto.CustStatementDTO;
import com.minigod.zero.customer.dto.StatementDTO;
import com.minigod.zero.customer.emuns.StatementEnums;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.*;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.utils.AsposeUtil;
import com.minigod.zero.customer.utils.ExportUtil;
import com.minigod.zero.customer.utils.FileUtils;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.feign.IAccountClient;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.pulsar.shade.org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CustomerStatementAccountServiceImpl implements CustomerStatementAccountService {
    @Resource
    private CustomerStatementAccountPdfProperties accountPdfProperties;
    @Resource
    private IAddrClient addrClient;
    @Resource
    private ICustomerInfoService customerInfoService;
    @Resource
    private IOssClient ossClient;
    @Resource
    private IAccountClient iAccountClient;
    @Resource
    private IBpmnWithdrawClient iBpmnWithdrawClient;
    @Resource
    private CustomerBondTradingTransactionMapper customerBondTradingTransactionMapper;
    @Resource
    private TExchRateHisMapper tExchRateHisMapper;
    @Resource
    private CustomerFinancingAccountMapper customerFinancingAccountMapper;
    @Resource
    private OrganizationBasicInfoMapper organizationBasicInfoMapper;

    @Resource
    private CustomerBondHoldingHistoryMapper customerBondHoldingHistoryMapper;
    @Resource
    private CustomerHldHoldingHistoryMapper customerHldHoldingHistoryMapper;

    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private CustomerBasicInfoMapper customerBasicInfoMapper;

    @Resource
    private CustomerFileMapper customerFileMapper;

    @Resource
    private FinancingAccountAmountFlowsMapper financingAccountAmountFlowsMapper;
    @Resource
    private AccountBusinessFlowMapper accountBusinessFlowMapper;
    @Resource
    private TaTDaClientMapper taTDaClientMapper;
    @Resource
    private TaTHoldingHistoryMapper taTHoldingHistoryMapper;
    @Resource
    private IDictBizClient dictBizClient;
    @Resource
    private ZeroRedis zeroRedis;
    @Resource
    private TNonTradingDayConfigMapper tNonTradingDayConfigMapper;

    private final static String NAME_KEY = "ADDRESS_NAME";


    @Override
    public String getTemplatePathByPdfType(String tenantId, CustomerStatementAccountFileEnum accountFileEnum) {
        String accountTemplatePath = null;
        PDFTemplate pdfTemplate = accountPdfProperties.getTenantMap().get(tenantId);
        switch (accountFileEnum) {
            case HLD_BOND_DAILY_STATEMENT_ACCOUNT_TEMPLATE:
                accountTemplatePath = pdfTemplate.getHldBondDailyTemplate();
                break;
            case HLD_BOND_MONTH_STATEMENT_ACCOUNT_TEMPLATE:
                accountTemplatePath = pdfTemplate.getHldBondMonthTemplate();
                break;
            case HLD_BOND_DAY_STATEMENT_ACCOUNT_TEMPLATE:
                accountTemplatePath = pdfTemplate.getHldBondDayTemplate();
                break;
            default:
                break;
        }
        if (null == accountTemplatePath) {
            throw new UnsupportedOperationException("无法获取报表模板信息");
        }
        return accountTemplatePath;
    }

    @Override
    public String makeOutputPath(String tenantId) {
        return accountPdfProperties.getPlacePath() + "/" + tenantId + "/" + System.currentTimeMillis() + "/";
    }


	/**
	 * 日结单生成
	 * @param custStatementDTO
	 */
    @Override
    public void exportHldBondDailyAccountList2(CustStatementDTO custStatementDTO) {
        StatementDTO statementDTO = new StatementDTO();

        if (AuthUtil.getTenantId().isEmpty()) {
            custStatementDTO.setTenantId("000000");
        }
        String originalCustId = custStatementDTO.getCustId();
        log.info("结单任务开始  custStatementDTO: {}", custStatementDTO);


        //List<String> accountList = customerInfoMapper.selCustList(DateUtil.beginOfDay(custStatementDTO.getDate()), DateUtil.endOfDay(custStatementDTO.getDate()));
        Date date = DateUtil.offsetDay(custStatementDTO.getDate(), -1);
		List<Integer> businessTypeList =  new ArrayList<>();
		businessTypeList.add(FlowBusinessType.AVAILABLE_DEPOSIT.getCode());
		businessTypeList.add(FlowBusinessType.AVAILABLE_DEDUCTIONS.getCode());
		businessTypeList.add(FlowBusinessType.FREEZE_DEDUCTIONS.getCode());
		businessTypeList.add(FlowBusinessType.TRANSITED_DEPOSIT.getCode());

		//资金流水变动
        List<String> accountList = financingAccountAmountFlowsMapper.selectCustByFlowList(DateUtil.beginOfDay(date), DateUtil.endOfDay(date), businessTypeList);
		//hld bond 订单成功的人
		List<String> hldOrderList = customerInfoMapper.selHldOrderList(DateUtil.beginOfDay(date), DateUtil.endOfDay(date));
		List<String> bondOrderList = customerInfoMapper.selBondOrderList(DateUtil.beginOfDay(date), DateUtil.endOfDay(date));
		List<String> custList = Stream.concat(
				Stream.concat(accountList.stream(), hldOrderList.stream()),
				bondOrderList.stream()
			)
			.distinct() // 去重
			.collect(Collectors.toList());

        int m = 0;
        for (String custId : custList) {
            try {

                custStatementDTO.setStartDate(DateUtil.beginOfDay(date));
                custStatementDTO.setEndDate(DateUtil.endOfDay(date));
				custStatementDTO.setStatementType(StatementEnum.DAY.getCode());
                custStatementDTO.setCustId(custId);
				log.info("日结单 custId: {} accountDate:{}", custId ,DateUtil.format(date, "yyyy-MM-dd") );
                statementDTO.setStartDate(DateUtil.beginOfDay(date));
                statementDTO.setEndDate(DateUtil.endOfDay(date));
                statementDTO.setCustId(custId);

                if (StringUtils.isNotEmpty(originalCustId)) {
                    if (!originalCustId.equals(custStatementDTO.getCustId())) {
                        continue;
                    }
                }
                //查询用户信息
                CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoMapper.selectByCustId(Long.valueOf(custId));
                CustomerFinancingAccountEntity customerFinancingAccountEntity = customerFinancingAccountMapper.selectByCustId(Long.valueOf(custId));
                if (customerBasicInfoEntity == null) {
                    continue;
                }


                HldBondDailyVO hldBondDailyVO = new HldBondDailyVO();
                hldBondDailyVO.setCustId(String.valueOf(customerFinancingAccountEntity.getCustId()));
				String accountId = customerFinancingAccountEntity.getAccountId();
				hldBondDailyVO.setAccountId(accountId);


				hldBondDailyVO.setAccountDate(DateUtil.format(date, "yyyy-MM-dd"));
				custStatementDTO.setAccountId(customerFinancingAccountEntity.getAccountId());

				//用户个人信息
				custMessage(custId, customerFinancingAccountEntity, hldBondDailyVO, customerBasicInfoEntity);

				log.info("日结单 custId: {} accountId:{} accountDate:{}", custId ,hldBondDailyVO.getAccountId(),hldBondDailyVO.getAccountDate() );
				statementDTO.setAccountId(hldBondDailyVO.getAccountId());
                /*********************************************************/

                //当天总额
                String format = DateUtil.format(date, "yyyy-MM-dd");
                CustomerCashAssetsHistory customerCashAssetsHistory = customerInfoMapper.selCashBalanceList(format, statementDTO.getCustId());

                //前一天总额
                Date newDate = DateUtil.offsetDay(date, -1);
                String format2 = DateUtil.format(newDate, "yyyy-MM-dd");
                CustomerCashAssetsHistory customerCashAssetsHistory2 = customerInfoMapper.selCashBalanceList(format2, statementDTO.getCustId());


                /**************************************/
                //投资组合总览: 投资总汇   买入的hld bond订单  持仓总览
                List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList = new ArrayList<>();
                List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList2 = new ArrayList<>();
                hldBondDailyVO.setInvestmentPools(investmentPoolList2);

                //交易单据
                List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList = new ArrayList<>();
                hldBondDailyVO.setTransactionContractNote(transactionContractNoteList);

				//户口出入金额变动
                List<HldBondDailyVO.ChangeAccountVO> changeAccountVOList = new ArrayList<>();
                hldBondDailyVO.setChangeAccount(changeAccountVOList);

                //持货结存
                List<HldBondDailyVO.HoldingVO> holdingVOList = new ArrayList<>();
                List<HldBondDailyVO.HoldingVO> hldHoldingVOList = new ArrayList<>();
                List<HldBondDailyVO.HoldingVO> bondHoldingVOList = new ArrayList<>();
                hldBondDailyVO.setHoldings(holdingVOList);

                //债券易用户
                CustStatementVO bondVo = customerInfoMapper.selCustBondStatement(custId);
                //活利得用户
                CustStatementVO hldVo = customerInfoMapper.selCustHldStatement(custId);


                //户口出入金额变动  //查  活力德购买赎回记录 债券易的购买赎回记录 --资金流水记录
                getChangeAccountVO(custStatementDTO, customerCashAssetsHistory2, bondVo, hldVo, changeAccountVOList, customerCashAssetsHistory);

                BigDecimal accumulatedBondRevenue = new BigDecimal(0.00);

                if (bondVo != null && bondVo.getSubAccountId() != null) {
                    custStatementDTO.setSubAccountId(bondVo.getSubAccountId());
					//ta用户
					LambdaQueryWrapper<TaTDaClientEntity> taWrapper = new LambdaQueryWrapper<>();
					taWrapper.eq(TaTDaClientEntity::getDaclientid, bondVo.getSubAccountId());
					taWrapper.orderByDesc(TaTDaClientEntity::getCreatetime);
					taWrapper.last("limit 1");
					TaTDaClientEntity taTDaClientEntity = taTDaClientMapper.selectOne(taWrapper);
					if (taTDaClientEntity != null) {
						custStatementDTO.setSubAccountId(taTDaClientEntity.getDaclientid());
					}
					//汇总
                    getHoldingOverViews(custStatementDTO, investmentPoolList, transactionContractNoteList, custStatementDTO.getEndDate(), bondVo, bondHoldingVOList);

                }

                //活利得用户
                BigDecimal accumulatedHldRevenue = new BigDecimal(0.00);
                if (hldVo != null && hldVo.getSubAccountId() != null) {
                    custStatementDTO.setSubAccountId(hldVo.getSubAccountId());
					LambdaQueryWrapper<TaTDaClientEntity> taWrapper = new LambdaQueryWrapper<>();
					taWrapper.eq(TaTDaClientEntity::getDaclientid, bondVo.getSubAccountId());
					taWrapper.orderByDesc(TaTDaClientEntity::getCreatetime);
					taWrapper.last("limit 1");
					TaTDaClientEntity taTDaClientEntity = taTDaClientMapper.selectOne(taWrapper);
					if (taTDaClientEntity != null){
						custStatementDTO.setTaSubAccountId(taTDaClientEntity.getSubaccountid());
					}
                    //汇总
                    getHoldingOverViews(custStatementDTO, investmentPoolList, transactionContractNoteList, custStatementDTO.getEndDate(), hldVo, hldHoldingVOList);

                }
                //持货结存 汇总
                holdingVOList.addAll(hldHoldingVOList);
                holdingVOList.addAll(bondHoldingVOList);
				//投资组合总览: 投资总汇
				//投资组合总览: 投资总汇(持仓总览)
				getInvestmentPoolVO(hldHoldingVOList, bondHoldingVOList, investmentPoolList);
				getTotalGainLoss(hldHoldingVOList, bondHoldingVOList,hldBondDailyVO);

                //交易单据 二次
				List<HldBondDailyVO.TransactionContractNoteVO> transactionHldList = new ArrayList<>();
				List<HldBondDailyVO.TransactionContractNoteVO> transactionBondList = new ArrayList<>();
                Map<Integer, List<HldBondDailyVO.TransactionContractNoteVO>> collect = transactionContractNoteList.stream().collect(Collectors.groupingBy(HldBondDailyVO.TransactionContractNoteVO::getType));
                for (Integer i : collect.keySet()) {
                    if (Objects.equals(i, MarketTypeEnum.HLD.getType())) {
                        transactionHldList = collect.get(i);
                    }
                    if (Objects.equals(i, MarketTypeEnum.BOND.getType())) {
                        transactionBondList = collect.get(i);
                    }
                }
                hldBondDailyVO.setTransactionHldContractNote(transactionHldList);
                hldBondDailyVO.setTransactionBondContractNote(transactionBondList);

				//投资组合总览: 现金变动结余
				cashChangeBalance(custStatementDTO, statementDTO, hldBondDailyVO, customerCashAssetsHistory,customerCashAssetsHistory2, transactionContractNoteList);



                //投资总汇二次处理  根据币种分组  计算差值
                Map<String, List<HldBondDailyVO.InvestmentPoolVO>> listMap = investmentPoolList.stream().collect(Collectors.groupingBy(HldBondDailyVO.InvestmentPoolVO::getCurrency));
                listMap.forEach((k, v) -> {
                    HldBondDailyVO.InvestmentPoolVO vo = new HldBondDailyVO.InvestmentPoolVO();

                    BigDecimal totalBondSaleValue = v.stream().map(HldBondDailyVO.InvestmentPoolVO::getTotalBondSaleValue).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totalHldSaleValue = v.stream().map(HldBondDailyVO.InvestmentPoolVO::getTotalHldSaleValue).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                    log.info("totalBondSaleValue:{},totalHldSaleValue:{}", totalBondSaleValue, totalHldSaleValue);
                    log.info("getRate:{}",v.get(0).getRate());

                    BigDecimal totalValue = (totalBondSaleValue.multiply(v.get(0).getRate())).add(totalHldSaleValue.multiply(v.get(0).getRate()));
					log.info("totalValue:{}",totalValue);
					vo.setCurrency(k);
                    vo.setTotalHldValue(totalHldSaleValue);
                    vo.setTotalBondValue(totalBondSaleValue);
                    vo.setTotalValue(totalValue);
                    vo.setRate(v.get(0).getRate());
					vo.setRates(v.get(0).getRate().setScale(4, RoundingMode.HALF_UP).toString());

					investmentPoolList2.add(vo);
                });
                //投资总汇 投资组合总值 (HKD 等值)
                hldBondDailyVO.setInvestmentPoolValue(BigDecimal.ZERO);
				BigDecimal investmentPoolValue = BigDecimal.ZERO;
                for (HldBondDailyVO.InvestmentPoolVO vo : investmentPoolList2) {
					BigDecimal totalValue = vo.getTotalValue();
					investmentPoolValue = totalValue.add(investmentPoolValue);
                }
				hldBondDailyVO.setInvestmentPoolValue(investmentPoolValue);

				String bondDailyTemplateUrl = dictBizClient.getValue("statement_template", "daily_statement_template").getData();
				log.info("日结单模板路径：：{}", bondDailyTemplateUrl);


				if (fileOperation(custStatementDTO, custId, hldBondDailyVO, bondDailyTemplateUrl, accountId, date))
					continue;
				m++;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("执行日结单生成任务失败 cust_id{}", custId);
            }

        }
        log.info("结单任务结束  data:{} 生成{}条结单", DateUtil.format(date, "yyyy-MM-dd"), m);
    }

	private boolean fileOperation(CustStatementDTO custStatementDTO, String custId, HldBondDailyVO hldBondDailyVO, String bondDailyTemplateUrl, String accountId, Date date) {
		String makeOutputPath = makeOutputPath(custStatementDTO.getTenantId());
		String pdfPath = getPdfPath(custId, hldBondDailyVO, bondDailyTemplateUrl, makeOutputPath, accountId);
		String link = null;
		String fileName = null;
		try {
			String message = "";
			if (custStatementDTO.getStatementType().equals(StatementEnum.DAY.getCode())) {
				message = CustomerStatementAccountFileEnum.HLD_BOND_DAILY_STATEMENT_ACCOUNT_TEMPLATE.getMessage();
			}else if (custStatementDTO.getStatementType().equals(StatementEnum.MONTH.getCode())) {
				message = CustomerStatementAccountFileEnum.HLD_BOND_MONTH_STATEMENT_ACCOUNT_TEMPLATE.getMessage();
			}
			// pdf上传到云端下载
			String name =  accountId +"-"+ custId + "-" + hldBondDailyVO.getAccountDate()+"-"+message+".pdf";;
			R<ZeroFile> uploadRes = ossClient.uploadMinioFile(FileUtils.getMultipartFile(FileUtils.file(pdfPath)),name);
			if (!uploadRes.isSuccess()) {
				log.info("活利得、债券易日结单pdf上传失败 ,{}",uploadRes.getMsg());
				return true;
			}
			link = uploadRes.getData().getLink();
			fileName = uploadRes.getData().getOriginalName();

			log.info("活利得、债券易日结单pdf路径：：{}", link);
		} catch (ServiceException e) {
			log.info("活利得、债券易日结单pdf上传失败");
			return true;
		}
		CustomerFile customerFile = new CustomerFile();
		customerFile.setCustId(Long.valueOf(custId));
		customerFile.setDate(DateUtil.beginOfDay(date));
		customerFile.setType(custStatementDTO.getStatementType());
		customerFile.setUrl(link);
		customerFile.setFileName(fileName);
		LambdaQueryWrapper<CustomerFile> queryWrapper3 = new LambdaQueryWrapper<>();
		queryWrapper3.eq(CustomerFile::getCustId, customerFile.getCustId());
		queryWrapper3.eq(CustomerFile::getDate, customerFile.getDate());
		queryWrapper3.eq(CustomerFile::getType, customerFile.getType());
		CustomerFile customerFile1 = customerFileMapper.selectOne(queryWrapper3);
		if (customerFile1 != null) {
			customerFile.setId(customerFile1.getId());
			customerFile.setUpdateTime(new Date());
			customerFileMapper.updateById(customerFile);
		}else {
			customerFile.setCreateTime(new Date());
			customerFile.setUpdateTime(new Date());
			customerFile.setStatus(StatementEnums.FileSendStatus.UN_SET.getCode());
			customerFileMapper.insert(customerFile);
		}
		return false;
	}


	private static String getPdfPath(String custId, HldBondDailyVO hldBondDailyVO, String bondDailyTemplateUrl, String makeOutputPath, String accountId) {
		String jsonString = JSON.toJSONString(hldBondDailyVO, new BigDecimalValueFilter());
		//String jsonString = JSON.toJSONString(hldBondDailyVO);
		log.info("活利得、债券易日结单服务器jsonString：{}", jsonString);
		//if (StringUtils.isEmpty(bondDailyTemplateUrl)){
		//	//bondDailyTemplateUrl = "F:\\数金\\消息模版\\month_statement_template-0827.docx";
		//	bondDailyTemplateUrl = "F:\\数金\\消息模版\\day_statement_template-0827.docx";
		//}
		// 导出生成的债券易日结单word
		String exportDocUrl = ExportUtil.exportTemplateWord(bondDailyTemplateUrl, makeOutputPath, CustomerStatementAccountFileEnum.HLD_BOND_DAILY_STATEMENT_ACCOUNT_TEMPLATE.getMessage()  + "-" + accountId +"-"+ custId + "-" + hldBondDailyVO.getAccountDate() + "-" + UUID.randomUUID()+ ".docx", JSONObject.parseObject(jsonString));
		log.info("活利得、债券易日结单服务器word路径：：{}", exportDocUrl);

		// 获取pdf生成路径
		String pdfPath = makeOutputPath + CustomerStatementAccountFileEnum.HLD_BOND_DAILY_STATEMENT_ACCOUNT_TEMPLATE.getMessage()  + "-" + accountId +"-"+ custId + "-" + hldBondDailyVO.getAccountDate() + ".pdf";
		// word转pdf
		AsposeUtil.doc2pdf(exportDocUrl, pdfPath);
		return pdfPath;
	}

	/**
	 * 投资组合总览: 现金变动结余
	 * @param custStatementDTO
	 * @param statementDTO
	 * @param hldBondDailyVO
	 * @param customerCashAssetsHistory2
	 * @param transactionContractNoteList
	 */
	private void cashChangeBalance(CustStatementDTO custStatementDTO,
								   StatementDTO statementDTO,
								   HldBondDailyVO hldBondDailyVO,
								   CustomerCashAssetsHistory customerCashAssetsHistory,
								   CustomerCashAssetsHistory customerCashAssetsHistory2,
								   List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList) {
		Map<String, BigDecimal> depositMap = new HashMap<>();
		Map<String, BigDecimal> withdrawMap = new HashMap<>();

		//获取入金额
		List<Integer> depositIds = Arrays.asList(14,26,27,28,32,34,35,36); // 入金类型 ThawingType
		statementDTO.setWithdrawIds(depositIds);
		statementDTO.setCustId(custStatementDTO.getCustId());
		List<FinancingAccountAmountFlows> depositList = accountBusinessFlowMapper.selWithdrawList(statementDTO);

		//获取出金额  需要判断审核是否成功
		List<Integer> withdrawIds = Arrays.asList(15,19,29,30,31,37,38); // 出金类型
		statementDTO.setWithdrawIds(withdrawIds);
		List<FinancingAccountAmountFlows> withdrawlist = accountBusinessFlowMapper.selWithdrawList(statementDTO);



		depositMap = depositList.stream()
			.collect(Collectors.groupingBy(
				FinancingAccountAmountFlows::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					FinancingAccountAmountFlows::getAmount,
					BigDecimal::add)));
		// 输出结果
		depositMap.forEach((currency, totalAmount) ->
			log.info("deposit  Currency: " + currency + ", Amount: " + totalAmount));

		withdrawMap = withdrawlist.stream()
				.collect(Collectors.groupingBy(
					FinancingAccountAmountFlows::getCurrency,
						Collectors.reducing(BigDecimal.ZERO,
							FinancingAccountAmountFlows::getAmount,
								BigDecimal::add)));
		// 输出结果
		withdrawMap.forEach((currency, totalAmount) ->
			log.info("withdraw  Currency: " + currency + ", Amount: " + totalAmount));

		//投资组合总览: 现金变动结余
		getCashChangeBalance(depositMap, withdrawMap, hldBondDailyVO, customerCashAssetsHistory,customerCashAssetsHistory2, transactionContractNoteList);
		//getCashChangeBalanceVO(customerCashAssetsHistory2, customerCashAssetsHistory, hldBondDailyVO, transactionContractNoteList);

	}

	private void custMessage(String custId, CustomerFinancingAccountEntity customerFinancingAccountEntity, HldBondDailyVO hldBondDailyVO, CustomerBasicInfoEntity customerBasicInfoEntity) {
		if (customerFinancingAccountEntity.getAccountType() == 1) {
			//机构户
			OrganizationBasicInfo organizationBasicInfo = organizationBasicInfoMapper.selectByCustId(Long.valueOf(custId));

			hldBondDailyVO.setCustName(organizationBasicInfo.getName());
			hldBondDailyVO.setAddress1(organizationBasicInfo.getAddress());
			hldBondDailyVO.setAddress(organizationBasicInfo.getAddress());
		} else {
			if (customerBasicInfoEntity.getClientName() != null) {
				hldBondDailyVO.setCustName(customerBasicInfoEntity.getClientName());
			} else {
				if (customerBasicInfoEntity.getGivenNameSpell() != null && customerBasicInfoEntity.getFamilyNameSpell() != null){
					hldBondDailyVO.setCustName(customerBasicInfoEntity.getGivenNameSpell() + " " + customerBasicInfoEntity.getFamilyNameSpell());
				}

			}

			String idCardProvinceName = "";
			String idCardCityName = "";
			String idCardCountyName = "";
			if (Objects.equals(customerBasicInfoEntity.getIdKind(), IdKindType.ID_CARD_FRONT.getIdKind())){
				R addressName1 = addrClient.getAddressName(customerBasicInfoEntity.getContactProvinceName());
				log.info("idCardProvinceName:{}",addressName1.toString());
				log.info("getContactProvinceName:{}",customerBasicInfoEntity.getContactProvinceName());
				if (addressName1.isSuccess()){
					idCardProvinceName = addressName1.getData().toString();
				}
				R addressName2 = addrClient.getAddressName(customerBasicInfoEntity.getContactCityName());
				if (addressName2.isSuccess()){
					idCardCityName = addressName2.getData().toString();
				}
				R addressName3 = addrClient.getAddressName(customerBasicInfoEntity.getContactCountyName());
				if (addressName3.isSuccess()) {
					idCardCountyName = addressName3.getData().toString();
				}

			}

			String contactDetailAddress = "";
			if (customerBasicInfoEntity.getContactDetailAddress() == null){
				if (customerBasicInfoEntity.getPermanentDetailAddress() == null){
					if (customerBasicInfoEntity.getIdCardDetailAddress() != null){
						contactDetailAddress = customerBasicInfoEntity.getIdCardDetailAddress();
					}
				}else {
					contactDetailAddress = customerBasicInfoEntity.getPermanentDetailAddress();
				}
			}else {
				contactDetailAddress = customerBasicInfoEntity.getContactDetailAddress();
			}

			hldBondDailyVO.setAddress(idCardProvinceName+idCardCityName+idCardCountyName+contactDetailAddress);
		}
	}


	private void getInvestmentPoolVO(List<HldBondDailyVO.HoldingVO> hldHoldingVOList,
                                     List<HldBondDailyVO.HoldingVO> bondHoldingVOList,
                                     List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList) {

        HldBondDailyVO.InvestmentPoolVO investmentPoolVO1 = new HldBondDailyVO.InvestmentPoolVO();//港币
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO2 = new HldBondDailyVO.InvestmentPoolVO();//美元
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO3 = new HldBondDailyVO.InvestmentPoolVO(); //人民币

        Map<String, BigDecimal> sumBondByCategory = bondHoldingVOList.stream()
			.filter(holding -> holding.getDayMktValue() != null)
			.collect(Collectors.groupingBy(
                        HldBondDailyVO.HoldingVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldBondDailyVO.HoldingVO::getDayMktValue,
                                BigDecimal::add)));
        // 输出结果
        sumBondByCategory.forEach((currency, totalAmount) ->
                log.info("bond持仓  Currency: " + currency + ", DayMktValue: " + totalAmount));

        Map<String, BigDecimal> sumHldByCategory = hldHoldingVOList.stream()
			.filter(holding -> holding.getDayMktValue() != null)
			.collect(Collectors.groupingBy(
                        HldBondDailyVO.HoldingVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldBondDailyVO.HoldingVO::getDayMktValue,
                                BigDecimal::add)));
        // 输出结果
        sumHldByCategory.forEach((currency, totalAmount) ->
                log.info("hld持仓  Currency: " + currency + ", DayMktValue: " + totalAmount));

        if (sumHldByCategory.containsKey("HKD") || sumBondByCategory.containsKey("HKD")) {
            investmentPoolVO1.setCurrency("HKD");
            investmentPoolVO1.setTotalHldSaleValue(sumHldByCategory.get("HKD") == null ? BigDecimal.ZERO : sumHldByCategory.get("HKD"));
            investmentPoolVO1.setTotalBondSaleValue(sumBondByCategory.get("HKD") == null ? BigDecimal.ZERO : sumBondByCategory.get("HKD"));
            hldHoldingVOList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("HKD")) {
                    investmentPoolVO1.setRate(buyConfirmForm.getRate());
                }
            });
            bondHoldingVOList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("HKD")) {
                    investmentPoolVO1.setRate(buyConfirmForm.getRate());
                }
            });

            investmentPoolList.add(investmentPoolVO1);
        }
        if (sumHldByCategory.containsKey("USD") || sumBondByCategory.containsKey("USD")) {
            investmentPoolVO2.setCurrency("USD");
            investmentPoolVO2.setTotalHldSaleValue(sumHldByCategory.get("USD") == null ? BigDecimal.ZERO : sumHldByCategory.get("USD"));
            investmentPoolVO2.setTotalBondSaleValue(sumBondByCategory.get("USD") == null ? BigDecimal.ZERO : sumBondByCategory.get("USD"));
            hldHoldingVOList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("USD")) {
                    investmentPoolVO2.setRate(buyConfirmForm.getRate());
                }
            });
            bondHoldingVOList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("USD")) {
                    investmentPoolVO2.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO2);
        }
        if (sumHldByCategory.containsKey("CNY") || sumBondByCategory.containsKey("CNY")) {
            investmentPoolVO3.setCurrency("CNY");
            investmentPoolVO3.setTotalHldSaleValue(sumHldByCategory.get("CNY") == null ? BigDecimal.ZERO : sumHldByCategory.get("CNY"));
            investmentPoolVO3.setTotalBondSaleValue(sumBondByCategory.get("CNY") == null ? BigDecimal.ZERO : sumBondByCategory.get("CNY"));
            hldHoldingVOList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("CNY")) {
                    investmentPoolVO3.setRate(buyConfirmForm.getRate());
                }
            });
            bondHoldingVOList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("CNY")) {
                    investmentPoolVO3.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO3);
        }


    }

    @NotNull
    private List<CustomerHldBondDailyAccountVO.HoldingOverView> getHoldingOverViews(CustStatementDTO custStatementDTO,
                                                                                    List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList,
                                                                                    List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList,
                                                                                    Date date, CustStatementVO bondVo,
                                                                                    List<HldBondDailyVO.HoldingVO> holdingVOList) {
        // 获取债券易日结单买入确认单集合
        List<CustomerHldBondDailyAccountVO.BuyConfirmForm> buyConfirmFormList = customerBondTradingTransactionMapper.getDailyBuyConfirmFormList(custStatementDTO);
        // 获取债券易日结单卖出确认单集合
        List<CustomerHldBondDailyAccountVO.SellConfirmForm> sellConfirmFormList = customerBondTradingTransactionMapper.getDailySellConfirmFormList(custStatementDTO);
        // 获取活利得日结单买入确认单
        List<HldTradingBuyStatementDailyVO> hldBuyDailyList = customerInfoMapper.hldTradingStatementList(custStatementDTO);
        // 获取活利得日结单卖出确认单
        List<HldTradingSaleStatementDailyVO> hldSaleDailyList = customerInfoMapper.hldTradingSaleStatementList(custStatementDTO);


        //交易成交单据
        getTransactionContractNoteVO(buyConfirmFormList, transactionContractNoteList, hldBuyDailyList, sellConfirmFormList, hldSaleDailyList);


        // 获取债券易日结单持仓总览集合
        //List<CustomerHldBondDailyAccountVO.HoldingOverView> holdingOverViewList = customerBondTradingTransactionMapper.getDailyHoldingOverViewList(custStatementDTO);//读取当前持仓
        //读取历史持仓最后一条记录
		List<CustomerHldBondDailyAccountVO.HoldingOverView> holdingOverViewList = customerBondTradingTransactionMapper.getDailyHoldingHistoryOverViewList(custStatementDTO);//读取历史持仓

        //持货结存
        for (CustomerHldBondDailyAccountVO.HoldingOverView holdingOverView : holdingOverViewList) {
			//查询汇率
			BigDecimal exchRate = tExchRateHisMapper.selRateByDate(date, holdingOverView.getCurrency());
			holdingOverView.setRate(exchRate==null?BigDecimal.ONE:exchRate);
			//当前时间持仓
			BigDecimal totalQuantity = customerBondTradingTransactionMapper.getDailyHoldingHistoryOverViewByFundCode(holdingOverView.getProductId(),custStatementDTO);
			if (totalQuantity!= null && totalQuantity.compareTo(BigDecimal.ZERO) <= 0){
				continue;
			}
            HldBondDailyVO.HoldingVO holdingVO = new HldBondDailyVO.HoldingVO();
            holdingVO.setMarket("债券易");
            holdingVO.setType(2);
            holdingVO.setProductName(holdingOverView.getBondName());
            holdingVO.setProductCode(holdingOverView.getBondCode());
            holdingVO.setCurrency(holdingOverView.getCurrency());


            LambdaQueryWrapper<CustomerBondHoldingHistory> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerBondHoldingHistory::getFundCode, holdingOverView.getProductId());
            queryWrapper.eq(CustomerBondHoldingHistory::getSubAccountId, bondVo.getSubAccountId()).orderByDesc(CustomerBondHoldingHistory::getCreateTime).last("limit 1");
            queryWrapper.lt(CustomerBondHoldingHistory::getCreateTime, custStatementDTO.getStartDate());
            CustomerBondHoldingHistory customerBondHoldingHistory = customerBondHoldingHistoryMapper.selectOne(queryWrapper);
            holdingVO.setTodayBalance(holdingOverView.getTotalQuantity() == null ? BigDecimal.ZERO : holdingOverView.getTotalQuantity());

			if (customerBondHoldingHistory == null) {
				holdingVO.setTodayBalance(BigDecimal.ZERO);
				holdingVO.setTodayChangeBalance(holdingOverView.getTotalQuantity());
			} else {
				if (holdingOverView.getId().equals(customerBondHoldingHistory.getId())){
					holdingVO.setTodayBalance(customerBondHoldingHistory.getTotalQuantity());
					holdingVO.setTodayChangeBalance(BigDecimal.ZERO);
				}else {
					holdingVO.setTodayBalance(customerBondHoldingHistory.getTotalQuantity());
					holdingVO.setTodayChangeBalance(holdingVO.getTodayBalance().subtract(customerBondHoldingHistory.getTotalQuantity()));
				}
			}

            holdingVO.setNetBalance(holdingVO.getTodayChangeBalance().add(holdingVO.getTodayBalance()));
            holdingVO.setClosingPrice(holdingOverView.getPrice());
            holdingVO.setRate(holdingOverView.getRate());
            holdingVO.setDayMktValue(holdingVO.getClosingPrice().multiply(holdingVO.getNetBalance()));
            //holdingVO.setTotalGainLoss(holdingOverView.getRealizedGainLoss());
            holdingVO.setRate(holdingOverView.getRate());

			//累计利息单独计算 根据平均成本计算市值之间差
			//获取平均成本
			//BigDecimal averageCost = holdingOverView.getAverageCost();
			TaTHoldingHistoryEntity taTHoldingHistory = taTHoldingHistoryMapper.getRealizedGainLoss(custStatementDTO,holdingOverView.getProductId());
			if (taTHoldingHistory == null) {
				holdingVO.setTotalGainLoss(BigDecimal.ZERO);
				holdingVO.setTotalHkGainLoss(BigDecimal.ZERO);
			}else {
				BigDecimal averagecost = taTHoldingHistory.getAveragecost();
				averagecost = (holdingVO.getClosingPrice().subtract(averagecost)).multiply(holdingVO.getNetBalance());
				log.info("bond 累计总收益:{}",averagecost);
				holdingVO.setTotalGainLoss(averagecost);
				holdingVO.setTotalHkGainLoss(averagecost.multiply(holdingVO.getRate()));
			}

			holdingVOList.add(holdingVO);
		}

        // 获取活利得日结单持仓总览集合
        //List<HldHoldingHistoryStatementDailyVO> hldHoldingOverViewDailyList = customerInfoMapper.hldHoldingHistoryStatementList(custStatementDTO);
        List<HldHoldingHistoryStatementDailyVO> hldHoldingOverViewDailyList = customerInfoMapper.hldHoldingHistoryList(custStatementDTO);
        for (HldHoldingHistoryStatementDailyVO vo : hldHoldingOverViewDailyList) {
			BigDecimal totalQuantity = customerInfoMapper.hldHoldingHistoryByFundCode(vo.getProductId(),custStatementDTO);
			if (vo.getQuantity()!=null && vo.getQuantity().compareTo(BigDecimal.ZERO) <= 0  && totalQuantity!= null && totalQuantity.compareTo(BigDecimal.ZERO) <= 0){
				//判断历史持仓是否为0，如果是0,说明之前已经清仓了则不显示
				continue;
			}
			BigDecimal exchRate = tExchRateHisMapper.selRateByDate(date, vo.getCurrency());
			vo.setRate(exchRate==null?BigDecimal.ONE:exchRate);

            HldBondDailyVO.HoldingVO holdingVO = new HldBondDailyVO.HoldingVO();
            holdingVO.setMarket("活利得");
            holdingVO.setType(1);
            holdingVO.setProductName(vo.getHldName());
            holdingVO.setProductCode(vo.getHldCode());
            holdingVO.setCurrency(vo.getCurrency());

            LambdaQueryWrapper<CustomerHldHoldingHistory> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerHldHoldingHistory::getFundCode, vo.getProductId());
            queryWrapper.lt(CustomerHldHoldingHistory::getCreateTime, custStatementDTO.getStartDate());
            queryWrapper.eq(CustomerHldHoldingHistory::getSubAccountId, bondVo.getSubAccountId())
				.orderByDesc(CustomerHldHoldingHistory::getCreateTime).last("limit 1");
            CustomerHldHoldingHistory customerHldHoldingHistory = customerHldHoldingHistoryMapper.selectOne(queryWrapper);
			holdingVO.setTodayBalance(vo.getQuantity() == null ? BigDecimal.ZERO : vo.getQuantity());

			if (customerHldHoldingHistory == null) {
				holdingVO.setTodayBalance(BigDecimal.ZERO);
				holdingVO.setTodayChangeBalance(vo.getQuantity());
			} else {
				if (vo.getId().equals(customerHldHoldingHistory.getId())){
					holdingVO.setTodayBalance(customerHldHoldingHistory.getTotalQuantity());
					holdingVO.setTodayChangeBalance(BigDecimal.ZERO);
				}else {
					holdingVO.setTodayBalance(customerHldHoldingHistory.getTotalQuantity());
					holdingVO.setTodayChangeBalance(vo.getQuantity().subtract(customerHldHoldingHistory.getTotalQuantity()));
				}
			}


            holdingVO.setNetBalance(holdingVO.getTodayChangeBalance().add(holdingVO.getTodayBalance()));
            holdingVO.setClosingPrice(vo.getPrice());
            holdingVO.setRate(vo.getRate());
			holdingVO.setDayMktValue(holdingVO.getClosingPrice().multiply(holdingVO.getNetBalance()));

            holdingVO.setRate(vo.getRate());

			//累计利息单独计算 去ta 每日计息汇总  减去已实现收益(类似提现走的意思
			TaTHoldingHistoryEntity taTHoldingHistory = taTHoldingHistoryMapper.getRealizedGainLoss(custStatementDTO,vo.getProductId());
			if (taTHoldingHistory==null) {
				holdingVO.setTotalGainLoss(BigDecimal.ZERO);
				holdingVO.setTotalHkGainLoss(BigDecimal.ZERO);
			}else {
				//已实现收益
				BigDecimal realizedgainloss = taTHoldingHistory.getRealizedgainloss();
				//累计总收益
				BigDecimal sumInterest = taTHoldingHistoryMapper.getHldSumInterest(custStatementDTO,taTHoldingHistory.getSubaccountid(),vo.getProductId());
				BigDecimal totalGainLoss = sumInterest.subtract(realizedgainloss);
				log.info("hld 累计总收益:{}",sumInterest);
				holdingVO.setTotalGainLoss(totalGainLoss);
				holdingVO.setTotalHkGainLoss(totalGainLoss.multiply(holdingVO.getRate()));
			}
			holdingVOList.add(holdingVO);

        }

        //投资组合总览: 投资总汇
        //买入
        //getInvestmentBuyPoolVO(buyConfirmFormList, hldBuyDailyList, investmentPoolList);
        //卖出
        //getInvestmentPoolSaleVO(sellConfirmFormList, hldSaleDailyList, investmentPoolList);
        //投资组合总览: 投资总汇(持仓总览)
        //getInvestmentPoolVO(holdingOverViewList, hldHoldingOverViewDailyList, investmentPoolList);

        return holdingOverViewList;
    }


    private void getChangeAccountVO(CustStatementDTO custStatementDTO,
                                    CustomerCashAssetsHistory customerCashAssetsHistory2,
                                    CustStatementVO bondVo,
                                    CustStatementVO hldVo,
                                    List<HldBondDailyVO.ChangeAccountVO> changeAccountVOList,
                                    CustomerCashAssetsHistory customerCashAssetsHistory) {
		//户口出入金额变动  读取港币变动

		BigDecimal hkdCashAssets1 = customerCashAssetsHistory2 != null ? customerCashAssetsHistory2.getHkdAssets() : BigDecimal.ZERO;
		BigDecimal cnyCashAssets1 = customerCashAssetsHistory2 != null ? customerCashAssetsHistory2.getCnyAssets() : BigDecimal.ZERO;
		BigDecimal usdCashAssets1 = customerCashAssetsHistory2 != null ? customerCashAssetsHistory2.getUsdAssets() : BigDecimal.ZERO;

		List<HldBondDailyVO.ChangeAccountVO> changeAccountVOHkdList = new ArrayList<>();
		List<HldBondDailyVO.ChangeAccountVO> changeAccountVOUsdList = new ArrayList<>();
		List<HldBondDailyVO.ChangeAccountVO> changeAccountVOCnyList = new ArrayList<>();

		BigDecimal hkdCashAssets2 = BigDecimal.ZERO;
		BigDecimal cnyCashAssets2 = BigDecimal.ZERO;
		BigDecimal usdCashAssets2 = BigDecimal.ZERO;

		//读取流水
		ArrayList<Integer> operationTypeList = new ArrayList<>();
		operationTypeList.add(FlowBusinessType.AVAILABLE_DEPOSIT.getCode());
		operationTypeList.add(FlowBusinessType.AVAILABLE_DEDUCTIONS.getCode());
		operationTypeList.add(FlowBusinessType.FREEZE_DEDUCTIONS.getCode());
		operationTypeList.add(FlowBusinessType.TRANSITED_DEPOSIT.getCode());

		ArrayList<Integer> statusList = new ArrayList<>();
		statusList.add(PayStatus.ADVANCE_CHARGE.getCode());
		statusList.add(PayStatus.PAYMENT_SUCCESSFUL.getCode());
		statusList.add(PayStatus.PARTIAL_PAYMENT.getCode());

		List<FinancingAccountAmountFlows> financingAccountAmountFlows = financingAccountAmountFlowsMapper.selectFlowList(custStatementDTO,operationTypeList,statusList);
		for (FinancingAccountAmountFlows flow : financingAccountAmountFlows) {
				/*if ((flow.getRemark().contains("债券易")|| flow.getRemark().contains("活利得"))) {
					continue;
				}*/
			HldBondDailyVO.ChangeAccountVO changeAccountVO = new HldBondDailyVO.ChangeAccountVO();
			//changeAccountVO.setTransactionAlias();
			String format = DateUtil.format(flow.getCreateTime(), "yyyy-MM-dd");
			changeAccountVO.setTradeDate(format);
			//changeAccountVO.setTransactionAlias(String.valueOf(flow.getId()));
			String remark = flow.getRemark();
			remark = remark.replaceAll("\\(.*?\\)", "");

			changeAccountVO.setContents(remark);
			changeAccountVO.setCurrency(flow.getCurrency());
			//changeAccountVO.setContentTwos();
			//changeAccountVO.setTotalQuantity();
			if (flow.getBeforeAmount().compareTo(flow.getAfterAmount()) < 0) {
				changeAccountVO.setDepositAccount(flow.getAmount());
				changeAccountVO.setNetBalance(changeAccountVO.getDepositAccount());
			} else {
				changeAccountVO.setWithdrawAmount(flow.getAmount().multiply(new BigDecimal("-1")));
				changeAccountVO.setNetBalance(changeAccountVO.getWithdrawAmount());
			}
			//changeAccountVOList.add(changeAccountVO);
			if (flow.getCurrency().equals("HKD")) {
				changeAccountVOHkdList.add(changeAccountVO);
				hkdCashAssets2 = hkdCashAssets2.add(changeAccountVO.getNetBalance());

			} else if (flow.getCurrency().equals("CNY")) {
				changeAccountVOCnyList.add(changeAccountVO);
				cnyCashAssets2 = cnyCashAssets2.add(changeAccountVO.getNetBalance());

			} else if (flow.getCurrency().equals("USD")) {
				changeAccountVOUsdList.add(changeAccountVO);
				usdCashAssets2 = usdCashAssets2.add(changeAccountVO.getNetBalance());

			}
		}



		HldBondDailyVO.ChangeAccountVO changeAccountVO4 = new HldBondDailyVO.ChangeAccountVO();
		changeAccountVO4.setContents("转下现金结余");
		changeAccountVO4.setCurrency("HKD");
		changeAccountVO4.setNetBalance(hkdCashAssets2.add(hkdCashAssets1));

		HldBondDailyVO.ChangeAccountVO changeAccountVO5 = new HldBondDailyVO.ChangeAccountVO();
		changeAccountVO5.setContents("转下现金结余");
		changeAccountVO5.setCurrency("CNY");
		changeAccountVO5.setNetBalance(cnyCashAssets2.add(cnyCashAssets1));

		HldBondDailyVO.ChangeAccountVO changeAccountVO6 = new HldBondDailyVO.ChangeAccountVO();
		changeAccountVO6.setContents("转下现金结余");
		changeAccountVO6.setCurrency("USD");
		changeAccountVO6.setNetBalance(usdCashAssets2.add(usdCashAssets1));

		HldBondDailyVO.ChangeAccountVO changeAccountVO1 = new HldBondDailyVO.ChangeAccountVO();
		changeAccountVO1.setContents("承上现金结余");
		changeAccountVO1.setCurrency("HKD");
		changeAccountVO1.setNetBalance(hkdCashAssets1);

		changeAccountVOList.add(changeAccountVO1);
		changeAccountVOList.addAll(changeAccountVOHkdList);
		changeAccountVOList.add(changeAccountVO4);

		HldBondDailyVO.ChangeAccountVO changeAccountVO2 = new HldBondDailyVO.ChangeAccountVO();
		changeAccountVO2.setContents("承上现金结余");
		changeAccountVO2.setCurrency("CNY");
		changeAccountVO2.setNetBalance(cnyCashAssets1);

		changeAccountVOList.add(changeAccountVO2);
		changeAccountVOList.addAll(changeAccountVOCnyList);
		changeAccountVOList.add(changeAccountVO5);

		HldBondDailyVO.ChangeAccountVO changeAccountVO3 = new HldBondDailyVO.ChangeAccountVO();
		changeAccountVO3.setContents("承上现金结余");
		changeAccountVO3.setCurrency("USD");
		changeAccountVO3.setNetBalance(usdCashAssets1);

		changeAccountVOList.add(changeAccountVO3);
		changeAccountVOList.addAll(changeAccountVOUsdList);
		changeAccountVOList.add(changeAccountVO6);

	}

    /**
     *
     * @param depositMap  入金map
     * @param withdrawMap  出金map
     * @param hldBondDailyVO 总
     */
    private void getCashChangeBalance(Map<String, BigDecimal> depositMap,
                                      Map<String, BigDecimal> withdrawMap,
                                      HldBondDailyVO hldBondDailyVO,
                                      CustomerCashAssetsHistory yesterdayTotalAssetsHistory,
                                      CustomerCashAssetsHistory yesterdayTotalAssetsHistory2,
									  List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList) {
        //投资组合总览: 现金变动结余
        List<HldBondDailyVO.CashChangeBalanceVO> changeBalanceVOArrayList = new ArrayList<>();

        HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO1 = new HldBondDailyVO.CashChangeBalanceVO();
        cashChangeBalanceVO1.setCurrency("HKD");
        BigDecimal hkdCashAssets2 = yesterdayTotalAssetsHistory2 != null ? yesterdayTotalAssetsHistory2.getHkdAssets() : BigDecimal.ZERO;
        BigDecimal hkdIntransit = yesterdayTotalAssetsHistory != null ? yesterdayTotalAssetsHistory.getHkdIntransit()==null?BigDecimal.ZERO:yesterdayTotalAssetsHistory.getHkdIntransit() : BigDecimal.ZERO;
		cashChangeBalanceVO1.setBackBalance(hkdIntransit);
		cashChangeBalanceVO1.setYesterdayBalance(hkdCashAssets2);

        HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO2 = new HldBondDailyVO.CashChangeBalanceVO();
        cashChangeBalanceVO2.setCurrency("CNY");
        BigDecimal cnyCashAssets2 = yesterdayTotalAssetsHistory2 != null ? yesterdayTotalAssetsHistory2.getCnyAssets() : BigDecimal.ZERO;
		BigDecimal cnyIntransit = yesterdayTotalAssetsHistory != null ? yesterdayTotalAssetsHistory.getCnyIntransit()==null?BigDecimal.ZERO:yesterdayTotalAssetsHistory.getCnyIntransit() : BigDecimal.ZERO;

		cashChangeBalanceVO1.setBackBalance(cnyIntransit);
        cashChangeBalanceVO2.setYesterdayBalance(cnyCashAssets2);

        HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO3 = new HldBondDailyVO.CashChangeBalanceVO();
        cashChangeBalanceVO3.setCurrency("USD");
        BigDecimal usdCashAssets2 = yesterdayTotalAssetsHistory2 != null ? yesterdayTotalAssetsHistory2.getUsdAssets() : BigDecimal.ZERO;
		BigDecimal usdIntransit = yesterdayTotalAssetsHistory != null ? yesterdayTotalAssetsHistory.getUsdIntransit()==null?BigDecimal.ZERO:yesterdayTotalAssetsHistory.getUsdIntransit() : BigDecimal.ZERO;

		cashChangeBalanceVO1.setBackBalance(usdIntransit);
        cashChangeBalanceVO3.setYesterdayBalance(usdCashAssets2);

        Map<String, List<HldBondDailyVO.TransactionContractNoteVO>> collect = transactionContractNoteList.stream()
			.collect(Collectors.groupingBy(HldBondDailyVO.TransactionContractNoteVO::getTradeKind));
        List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteVOBuyList = collect.get("买入")==null?new ArrayList<>():collect.get("买入");
        List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteVOSellList = collect.get("卖出")==null?new ArrayList<>():collect.get("卖出");


        Map<String, BigDecimal> sumBuyByCategory = transactionContractNoteVOBuyList.stream()
                .collect(Collectors.groupingBy(
                        HldBondDailyVO.TransactionContractNoteVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldBondDailyVO.TransactionContractNoteVO::getBusinessBalance,
                                BigDecimal::add)));
        Map<String, BigDecimal> sumSellByCategory = transactionContractNoteVOSellList.stream()
                .collect(Collectors.groupingBy(
                        HldBondDailyVO.TransactionContractNoteVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldBondDailyVO.TransactionContractNoteVO::getBusinessBalance,
                                BigDecimal::add)));
		log.info("买入单据和 sumBuyByCategory:{}",sumBuyByCategory);
        log.info("卖出单据和 sumSellByCategory:{}",sumSellByCategory);
		//今日交收 公式=卖出金额-买入金额
        BigDecimal hkdPending = (sumSellByCategory.get("HKD") == null ? BigDecimal.ZERO : sumSellByCategory.get("HKD")).subtract(sumBuyByCategory.get("HKD") == null ? BigDecimal.ZERO : sumBuyByCategory.get("HKD"));
        cashChangeBalanceVO1.setPendingBalance(hkdPending);
        BigDecimal cnyPending = (sumSellByCategory.get("CNY") == null ? BigDecimal.ZERO : sumSellByCategory.get("CNY")).subtract(sumBuyByCategory.get("CNY") == null ? BigDecimal.ZERO : sumBuyByCategory.get("CNY"));
        cashChangeBalanceVO2.setPendingBalance(cnyPending);
        BigDecimal usdPending = (sumSellByCategory.get("USD") == null ? BigDecimal.ZERO : sumSellByCategory.get("USD")).subtract(sumBuyByCategory.get("USD") == null ? BigDecimal.ZERO : sumBuyByCategory.get("USD"));
        cashChangeBalanceVO3.setPendingBalance(usdPending);


		//卖出的利息


		//今日变动 入金额-出金额+活利得卖出的利息+债券易的派息-活利得和债券易的费用
		BigDecimal hkdCashAssets = (depositMap.get("HKD")==null?BigDecimal.ZERO:depositMap.get("HKD"))
			.subtract(withdrawMap.get("HKD")==null?BigDecimal.ZERO:withdrawMap.get("HKD"));
		cashChangeBalanceVO1.setTodayChangeBalance(hkdCashAssets);

		BigDecimal cnyCashAssets = (depositMap.get("CNY")==null?BigDecimal.ZERO:depositMap.get("CNY"))
			.subtract(withdrawMap.get("CNY")==null?BigDecimal.ZERO:withdrawMap.get("CNY"));

		cashChangeBalanceVO2.setTodayChangeBalance(cnyCashAssets);

		BigDecimal usdCashAssets = (depositMap.get("USD")==null?BigDecimal.ZERO:depositMap.get("USD"))
			.subtract(withdrawMap.get("USD")==null?BigDecimal.ZERO:withdrawMap.get("USD"));

		cashChangeBalanceVO3.setTodayChangeBalance(usdCashAssets);


		cashChangeBalanceVO1.setTodayBalance(cashChangeBalanceVO1.getYesterdayBalance()
			.add(cashChangeBalanceVO1.getTodayChangeBalance())
			.add(cashChangeBalanceVO1.getPendingBalance())
			.add(cashChangeBalanceVO1.getBackBalance())
		);
		cashChangeBalanceVO2.setTodayBalance(cashChangeBalanceVO2.getYesterdayBalance()
			.add(cashChangeBalanceVO2.getTodayChangeBalance())
			.add(cashChangeBalanceVO2.getPendingBalance())
			.add(cashChangeBalanceVO1.getBackBalance())
		);
		cashChangeBalanceVO3.setTodayBalance(cashChangeBalanceVO3.getYesterdayBalance()
			.add(cashChangeBalanceVO3.getTodayChangeBalance())
			.add(cashChangeBalanceVO3.getPendingBalance())
			.add(cashChangeBalanceVO1.getBackBalance())
		);


		changeBalanceVOArrayList.add(cashChangeBalanceVO1);
        changeBalanceVOArrayList.add(cashChangeBalanceVO2);
        changeBalanceVOArrayList.add(cashChangeBalanceVO3);
        hldBondDailyVO.setCashChangeBalances(changeBalanceVOArrayList);

    }
    /**
     * @param yesterdayTotalAssetsHistory 昨天资金信息
     * @param dayTotalAssetsHistory       今天资金信息
     * @param hldBondDailyVO
     * @param transactionContractNoteList
     */
    private static void getCashChangeBalanceVO(CustomerCashAssetsHistory yesterdayTotalAssetsHistory,
                                               CustomerCashAssetsHistory dayTotalAssetsHistory,
                                               HldBondDailyVO hldBondDailyVO,
                                               List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList) {
        //投资组合总览: 现金变动结余
        List<HldBondDailyVO.CashChangeBalanceVO> changeBalanceVOArrayList = new ArrayList<>();

        HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO1 = new HldBondDailyVO.CashChangeBalanceVO();
        cashChangeBalanceVO1.setCurrency("HKD");
        BigDecimal hkdCashAssets2 = yesterdayTotalAssetsHistory != null ? yesterdayTotalAssetsHistory.getHkdAssets() : BigDecimal.ZERO;
        BigDecimal hkdCashAssets = dayTotalAssetsHistory != null ? dayTotalAssetsHistory.getHkdAssets() : BigDecimal.ZERO;
        cashChangeBalanceVO1.setYesterdayBalance(hkdCashAssets2);
        cashChangeBalanceVO1.setTodayChangeBalance(hkdCashAssets.subtract(hkdCashAssets2));
        //cashChangeBalanceVO1.setPendingBalance();

        cashChangeBalanceVO1.setTodayBalance(hkdCashAssets);

        HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO2 = new HldBondDailyVO.CashChangeBalanceVO();
        cashChangeBalanceVO2.setCurrency("CNY");
        BigDecimal cnyCashAssets2 = yesterdayTotalAssetsHistory != null ? yesterdayTotalAssetsHistory.getCnyAssets() : BigDecimal.ZERO;
        BigDecimal cnyCashAssets = dayTotalAssetsHistory != null ? dayTotalAssetsHistory.getCnyAssets() : BigDecimal.ZERO;
        cashChangeBalanceVO2.setYesterdayBalance(cnyCashAssets2);
        cashChangeBalanceVO2.setTodayChangeBalance(cnyCashAssets.subtract(cnyCashAssets2));
        cashChangeBalanceVO2.setTodayBalance(cnyCashAssets);
        //cashChangeBalanceVO2.setPendingBalance();

        HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO3 = new HldBondDailyVO.CashChangeBalanceVO();
        cashChangeBalanceVO3.setCurrency("USD");
        BigDecimal usdCashAssets2 = yesterdayTotalAssetsHistory != null ? yesterdayTotalAssetsHistory.getUsdAssets() : BigDecimal.ZERO;
        BigDecimal usdCashAssets = dayTotalAssetsHistory != null ? dayTotalAssetsHistory.getUsdAssets() : BigDecimal.ZERO;
        cashChangeBalanceVO3.setYesterdayBalance(usdCashAssets2);
        cashChangeBalanceVO3.setTodayChangeBalance(usdCashAssets.subtract(usdCashAssets2));
        cashChangeBalanceVO3.setTodayBalance(usdCashAssets);
        //cashChangeBalanceVO3.setPendingBalance();

        Map<String, BigDecimal> sumHldByCategory = transactionContractNoteList.stream()
                .collect(Collectors.groupingBy(
                        HldBondDailyVO.TransactionContractNoteVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldBondDailyVO.TransactionContractNoteVO::getSettlementAmount,
                                BigDecimal::add)));
        sumHldByCategory.forEach((k, v) -> {
            if ("HKD".equals(k)) {
                cashChangeBalanceVO1.setPendingBalance(v);
            } else if ("CNY".equals(k)) {
                cashChangeBalanceVO2.setPendingBalance(v);
            } else if ("USD".equals(k)) {
                cashChangeBalanceVO3.setPendingBalance(v);
            }
        });

        changeBalanceVOArrayList.add(cashChangeBalanceVO1);
        changeBalanceVOArrayList.add(cashChangeBalanceVO2);
        changeBalanceVOArrayList.add(cashChangeBalanceVO3);
        hldBondDailyVO.setCashChangeBalances(changeBalanceVOArrayList);

    }



    /**
     * @param buyConfirmFormList
     * @param transactionContractNoteList
     * @param hldBuyDailyList
     * @param sellConfirmFormList
     * @param hldSaleDailyList
     */
    private static void getTransactionContractNoteVO(List<CustomerHldBondDailyAccountVO.BuyConfirmForm> buyConfirmFormList,
                                                     List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList,
                                                     List<HldTradingBuyStatementDailyVO> hldBuyDailyList,
                                                     List<CustomerHldBondDailyAccountVO.SellConfirmForm> sellConfirmFormList,
                                                     List<HldTradingSaleStatementDailyVO> hldSaleDailyList) {
        for (CustomerHldBondDailyAccountVO.BuyConfirmForm buyConfirmForm : buyConfirmFormList) {
            //债券易 交易成交单据
            HldBondDailyVO.TransactionContractNoteVO vo = new HldBondDailyVO.TransactionContractNoteVO();
            vo.setType(MarketTypeEnum.BOND.getType());
            vo.setTradeDate(buyConfirmForm.getTransactionDateOnly());
			vo.setTradeKind("买入");
			vo.setTransactionAlias(buyConfirmForm.getOrderId());//参考编号
            vo.setProductName(buyConfirmForm.getName());
            vo.setProductCode(buyConfirmForm.getFundCode());
            vo.setCurrency(buyConfirmForm.getCurrency());
            vo.setTotalQuantity(buyConfirmForm.getQuantity());
            vo.setNominalValue(buyConfirmForm.getNominalValue());
            vo.setFaceAmount(buyConfirmForm.getFaceAmount());
            vo.setBusinessPrice(buyConfirmForm.getBusinessPrice());
            vo.setBusinessBalance(buyConfirmForm.getBusinessPrice().multiply(buyConfirmForm.getQuantity()));
            vo.setRealizedGainLoss(buyConfirmForm.getTransactionGainLoss());
            vo.setSettlementAmount(vo.getBusinessPrice().add(vo.getRealizedGainLoss()));
            vo.setRate(buyConfirmForm.getRate());

            //应付日数
            //vo.setDaysPayable(buyConfirmForm);
            transactionContractNoteList.add(vo);

        }

        for (HldTradingBuyStatementDailyVO hldTradingBuyStatementDailyVO : hldBuyDailyList) {
            //活利得 交易成交单据
            HldBondDailyVO.TransactionContractNoteVO vo = new HldBondDailyVO.TransactionContractNoteVO();
            vo.setTradeDate(hldTradingBuyStatementDailyVO.getTransactionDate());
            vo.setType(MarketTypeEnum.HLD.getType());
			vo.setTradeKind("买入");
            vo.setTransactionAlias(hldTradingBuyStatementDailyVO.getOrderId());//参考编号
            vo.setProductName(hldTradingBuyStatementDailyVO.getHldName());
            vo.setProductCode(hldTradingBuyStatementDailyVO.getHldCode());
            vo.setCurrency(hldTradingBuyStatementDailyVO.getCurrency());
            vo.setTotalQuantity(hldTradingBuyStatementDailyVO.getQuantity());
            vo.setNominalValue(hldTradingBuyStatementDailyVO.getNominalValue());
            vo.setFaceAmount(hldTradingBuyStatementDailyVO.getFaceAmount());
            vo.setBusinessPrice(hldTradingBuyStatementDailyVO.getBusinessPrice());
            vo.setBusinessBalance(hldTradingBuyStatementDailyVO.getAmout());
            vo.setBusinessBalance(hldTradingBuyStatementDailyVO.getBusinessPrice().multiply(hldTradingBuyStatementDailyVO.getQuantity()));

            vo.setRealizedGainLoss(hldTradingBuyStatementDailyVO.getTransactionGainLoss());
            vo.setSettlementAmount(hldTradingBuyStatementDailyVO.getTotalSettlementAmount());
            vo.setRate(hldTradingBuyStatementDailyVO.getRate());
            //应付日数
            //vo.setDaysPayable(buyConfirmForm);
            transactionContractNoteList.add(vo);

        }


        for (CustomerHldBondDailyAccountVO.SellConfirmForm sellConfirmForm : sellConfirmFormList) {
            //债券易 交易成交单据
            HldBondDailyVO.TransactionContractNoteVO vo = new HldBondDailyVO.TransactionContractNoteVO();
            vo.setTradeDate(sellConfirmForm.getTransactionDateOnly());
            vo.setType(MarketTypeEnum.BOND.getType());
			vo.setTradeKind("卖出");
            vo.setTransactionAlias(sellConfirmForm.getOrderId());//参考编号
            vo.setProductName(sellConfirmForm.getName());
            vo.setProductCode(sellConfirmForm.getFundCode());
            vo.setCurrency(sellConfirmForm.getCurrency());
            vo.setTotalQuantity(sellConfirmForm.getQuantity());
            vo.setNominalValue(sellConfirmForm.getNominalValue());
            vo.setFaceAmount(sellConfirmForm.getFaceAmount());
            vo.setBusinessPrice(sellConfirmForm.getBusinessPrice());
            vo.setBusinessBalance(sellConfirmForm.getBusinessPrice().multiply(sellConfirmForm.getQuantity()));
            vo.setRealizedGainLoss(sellConfirmForm.getTransactionGainLoss());
            vo.setSettlementAmount(sellConfirmForm.getTotalSettlementAmount());
            vo.setRate(sellConfirmForm.getRate());

            //应付日数
            //vo.setDaysPayable(buyConfirmForm);
            transactionContractNoteList.add(vo);

        }

        for (HldTradingSaleStatementDailyVO hldTradingSaleStatementDailyVO : hldSaleDailyList) {
            //活力得 交易成交单据
            HldBondDailyVO.TransactionContractNoteVO vo = new HldBondDailyVO.TransactionContractNoteVO();
            vo.setTradeDate(hldTradingSaleStatementDailyVO.getTransactionDate());
            vo.setType(MarketTypeEnum.HLD.getType());
			vo.setTradeKind("卖出");
            vo.setTransactionAlias(hldTradingSaleStatementDailyVO.getOrderId());//参考编号
            vo.setProductName(hldTradingSaleStatementDailyVO.getHldName());
            vo.setProductCode(hldTradingSaleStatementDailyVO.getHldCode());
            vo.setCurrency(hldTradingSaleStatementDailyVO.getCurrency());
            vo.setTotalQuantity(hldTradingSaleStatementDailyVO.getQuantity());
            vo.setNominalValue(hldTradingSaleStatementDailyVO.getNominalValue());
            vo.setFaceAmount(hldTradingSaleStatementDailyVO.getFaceAmount());
            vo.setBusinessPrice(hldTradingSaleStatementDailyVO.getBusinessPrice());
            vo.setBusinessBalance(hldTradingSaleStatementDailyVO.getBusinessPrice().multiply(hldTradingSaleStatementDailyVO.getQuantity()));
            vo.setRealizedGainLoss(hldTradingSaleStatementDailyVO.getTransactionGainLoss());
            vo.setSettlementAmount(hldTradingSaleStatementDailyVO.getTotalSettlementAmount());
            vo.setRate(hldTradingSaleStatementDailyVO.getRate());
            //应付日数
            //vo.setDaysPayable(buyConfirmForm);
            transactionContractNoteList.add(vo);
        }
    }

    /**
     * 投资总汇  卖出债券易  活力得
     *
     * @param sellConfirmFormList
     * @param hldSaleDailyList
     * @param investmentPoolList
     */
    private void getInvestmentPoolSaleVO(List<CustomerHldBondDailyAccountVO.SellConfirmForm> sellConfirmFormList,
                                         List<HldTradingSaleStatementDailyVO> hldSaleDailyList,
                                         List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList) {
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO1 = new HldBondDailyVO.InvestmentPoolVO();//港币
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO2 = new HldBondDailyVO.InvestmentPoolVO();//美元
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO3 = new HldBondDailyVO.InvestmentPoolVO(); //人民币

        Map<String, BigDecimal> sumBondByCategory = sellConfirmFormList.stream()
                .collect(Collectors.groupingBy(
                        CustomerHldBondDailyAccountVO.SellConfirmForm::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                CustomerHldBondDailyAccountVO.SellConfirmForm::getAmount,
                                BigDecimal::add)));
        // 输出结果
        sumBondByCategory.forEach((currency, totalAmount) ->
                log.info("bond卖出  Currency: " + currency + ", Total Amount: " + totalAmount));

        Map<String, BigDecimal> sumHldByCategory = hldSaleDailyList.stream()
                .collect(Collectors.groupingBy(
                        HldTradingSaleStatementDailyVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldTradingSaleStatementDailyVO::getAmout,
                                BigDecimal::add)));
        // 输出结果
        sumHldByCategory.forEach((currency, totalAmount) ->
                log.info("hld卖出  Currency: " + currency + ", Total Amount: " + totalAmount));

        if (sumHldByCategory.containsKey("HKD") || sumBondByCategory.containsKey("HKD")) {
            investmentPoolVO1.setCurrency("HKD");
            investmentPoolVO1.setTotalHldSaleValue(sumHldByCategory.get("HKD") == null ? BigDecimal.ZERO : sumHldByCategory.get("HKD"));
            investmentPoolVO1.setTotalBondSaleValue(sumBondByCategory.get("HKD") == null ? BigDecimal.ZERO : sumBondByCategory.get("HKD"));
            sellConfirmFormList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("HKD")) {
                    investmentPoolVO1.setRate(buyConfirmForm.getRate());
                }
            });
            sellConfirmFormList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("HKD")) {
                    investmentPoolVO1.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO1);
        }
        if (sumHldByCategory.containsKey("USD") || sumBondByCategory.containsKey("USD")) {
            investmentPoolVO2.setCurrency("USD");
            investmentPoolVO2.setTotalHldSaleValue(sumHldByCategory.get("USD") == null ? BigDecimal.ZERO : sumHldByCategory.get("USD"));
            investmentPoolVO2.setTotalBondSaleValue(sumBondByCategory.get("USD") == null ? BigDecimal.ZERO : sumBondByCategory.get("USD"));
            hldSaleDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("USD")) {
                    investmentPoolVO2.setRate(buyConfirmForm.getRate());
                }
            });
            hldSaleDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("USD")) {
                    investmentPoolVO2.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO2);
        }
        if (sumHldByCategory.containsKey("CNY") || sumBondByCategory.containsKey("CNY")) {
            investmentPoolVO3.setCurrency("CNY");
            investmentPoolVO3.setTotalHldSaleValue(sumHldByCategory.get("CNY") == null ? BigDecimal.ZERO : sumHldByCategory.get("CNY"));
            investmentPoolVO3.setTotalBondSaleValue(sumBondByCategory.get("CNY") == null ? BigDecimal.ZERO : sumBondByCategory.get("CNY"));
            hldSaleDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("CNY")) {
                    investmentPoolVO3.setRate(buyConfirmForm.getRate());
                }
            });
            hldSaleDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("CNY")) {
                    investmentPoolVO3.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO3);
        }

    }

    /**
     * 投资总汇  买入债券易  活力得
     *
     * @param buyConfirmFormList
     * @param hldBuyDailyList
     * @param investmentPoolList
     */
    private static void getInvestmentBuyPoolVO(List<CustomerHldBondDailyAccountVO.BuyConfirmForm> buyConfirmFormList,
                                               List<HldTradingBuyStatementDailyVO> hldBuyDailyList,
                                               List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList) {
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO1 = new HldBondDailyVO.InvestmentPoolVO();//港币
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO2 = new HldBondDailyVO.InvestmentPoolVO();//美元
        HldBondDailyVO.InvestmentPoolVO investmentPoolVO3 = new HldBondDailyVO.InvestmentPoolVO(); //人民币

        Map<String, BigDecimal> sumBondByCategory = buyConfirmFormList.stream()
                .collect(Collectors.groupingBy(
                        CustomerHldBondDailyAccountVO.BuyConfirmForm::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                CustomerHldBondDailyAccountVO.BuyConfirmForm::getAmount,
                                BigDecimal::add)));
        // 输出结果
        sumBondByCategory.forEach((currency, totalAmount) ->
                log.info("bond买入  Currency: " + currency + ", Total Amount: " + totalAmount));

        Map<String, BigDecimal> sumHldByCategory = hldBuyDailyList.stream()
                .collect(Collectors.groupingBy(
                        HldTradingBuyStatementDailyVO::getCurrency,
                        Collectors.reducing(BigDecimal.ZERO,
                                HldTradingBuyStatementDailyVO::getAmout,
                                BigDecimal::add)));
        // 输出结果
        sumHldByCategory.forEach((currency, totalAmount) ->
                log.info("hld买入  Currency: " + currency + ", Total Amount: " + totalAmount));

        if (sumHldByCategory.containsKey("HKD") || sumBondByCategory.containsKey("HKD")) {
            investmentPoolVO1.setCurrency("HKD");
            investmentPoolVO1.setTotalHldBuyValue(sumHldByCategory.get("HKD") == null ? BigDecimal.ZERO : sumHldByCategory.get("HKD"));
            investmentPoolVO1.setTotalBondBuyValue(sumBondByCategory.get("HKD") == null ? BigDecimal.ZERO : sumBondByCategory.get("HKD"));
            buyConfirmFormList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("HKD")) {
                    investmentPoolVO1.setRate(buyConfirmForm.getRate());
                }
            });
            hldBuyDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("HKD")) {
                    investmentPoolVO1.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO1);
        }
        if (sumHldByCategory.containsKey("USD") || sumBondByCategory.containsKey("USD")) {
            investmentPoolVO2.setCurrency("USD");
            investmentPoolVO2.setTotalHldBuyValue(sumHldByCategory.get("USD") == null ? BigDecimal.ZERO : sumHldByCategory.get("USD"));
            investmentPoolVO2.setTotalBondBuyValue(sumBondByCategory.get("USD") == null ? BigDecimal.ZERO : sumBondByCategory.get("USD"));
            buyConfirmFormList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("USD")) {
                    investmentPoolVO2.setRate(buyConfirmForm.getRate());
                }
            });
            hldBuyDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("USD")) {
                    investmentPoolVO2.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO2);
        }
        if (sumHldByCategory.containsKey("CNY") || sumBondByCategory.containsKey("CNY")) {
            investmentPoolVO3.setCurrency("CNY");
            investmentPoolVO3.setTotalHldBuyValue(sumHldByCategory.get("CNY") == null ? BigDecimal.ZERO : sumHldByCategory.get("CNY"));
            investmentPoolVO3.setTotalBondBuyValue(sumBondByCategory.get("CNY") == null ? BigDecimal.ZERO : sumBondByCategory.get("CNY"));
            buyConfirmFormList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("CNY")) {
                    investmentPoolVO3.setRate(buyConfirmForm.getRate());
                }
            });
            hldBuyDailyList.stream().forEach(buyConfirmForm -> {
                if (buyConfirmForm.getCurrency().equals("CNY")) {
                    investmentPoolVO3.setRate(buyConfirmForm.getRate());
                }
            });
            investmentPoolList.add(investmentPoolVO3);
        }
    }

	/**
	 * 月结单
	 * @param custStatementDTO
	 */
    @Override
    public void exportHldBondMonthAccountList2(CustStatementDTO custStatementDTO) {
		StatementDTO statementDTO = new StatementDTO();

		if (AuthUtil.getTenantId().isEmpty()) {
			custStatementDTO.setTenantId("000000");
		}
		String originalCustId = custStatementDTO.getCustId();
		log.info("月结单任务开始  custStatementDTO: {}", custStatementDTO);

		custStatementDTO.setStartDate(DateUtil.beginOfMonth(custStatementDTO.getDate()));
		custStatementDTO.setEndDate(DateUtil.endOfMonth(custStatementDTO.getDate()));

		Date date =  DateUtil.offset(custStatementDTO.getDate(), DateField.MONTH, -1);
		//资金流水变动
		List<String> accountList = financingAccountAmountFlowsMapper.selectCustByFlowList(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date), new ArrayList<>());
		//hld bond 订单成功的人
		List<String> hldOrderList = customerInfoMapper.selHldOrderList(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));
		List<String> bondOrderList = customerInfoMapper.selBondOrderList(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));

		//资金不为0 的人
		List<String> cashCustList = customerInfoMapper.selCashByCustList(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));

		//持仓不为空的人
		List<String> selCustList = customerInfoMapper.selCustList();

		accountList.addAll(hldOrderList);
		accountList.addAll(bondOrderList);
		accountList.addAll(selCustList);
		accountList.addAll(cashCustList);

		List<String> custList = new ArrayList<>(new LinkedHashSet<>(accountList));

		int m = 0;
		for (String custId : custList) {
			try {

				custStatementDTO.setStartDate(DateUtil.beginOfMonth(date));
				custStatementDTO.setEndDate(DateUtil.endOfMonth(date));
				custStatementDTO.setStatementType(StatementEnum.MONTH.getCode());
				custStatementDTO.setCustId(custId);
				log.info("月结单 custId: {} accountDate:{}", custId ,DateUtil.format(date, "yyyy-MM-dd") );
				statementDTO.setStartDate(DateUtil.beginOfMonth(date));
				statementDTO.setEndDate(DateUtil.endOfMonth(date));
				statementDTO.setCustId(custId);

				if (StringUtils.isNotEmpty(originalCustId)) {
					if (!originalCustId.equals(custStatementDTO.getCustId())) {
						continue;
					}
				}
				//查询用户信息
				CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoMapper.selectByCustId(Long.valueOf(custId));
				CustomerFinancingAccountEntity customerFinancingAccountEntity = customerFinancingAccountMapper.selectByCustId(Long.valueOf(custId));
				if (customerBasicInfoEntity == null) {
					continue;
				}


				HldBondDailyVO hldBondDailyVO = new HldBondDailyVO();
				hldBondDailyVO.setCustId(String.valueOf(customerFinancingAccountEntity.getCustId()));
				String accountId = customerFinancingAccountEntity.getAccountId();
				hldBondDailyVO.setAccountId(accountId);

				hldBondDailyVO.setAccountDate(DateUtil.format(date, "yyyy-MM"));

				hldBondDailyVO.setAccountDate2(DateUtil.format(new Date(), "yyyy-MM-dd"));
				//获取最后一个交易日
				String latestTradeDate = getLatestTradeDate(date);
				Date latestDate = DateUtil.parse(latestTradeDate, "yyyy-MM-dd");
				Date endLatestDate = DateUtil.endOfDay(latestDate);
				hldBondDailyVO.setAccountDate3(latestTradeDate);
				custStatementDTO.setAccountId(customerFinancingAccountEntity.getAccountId());

				//用户个人信息
				custMessage(custId, customerFinancingAccountEntity, hldBondDailyVO, customerBasicInfoEntity);

				log.info("日结单 custId: {} accountId:{} accountDate:{}", custId ,accountId,hldBondDailyVO.getAccountDate() );
				statementDTO.setAccountId(hldBondDailyVO.getAccountId());
				/*********************************************************/

				//当月30号总额
				Date formatDate = DateUtil.endOfMonth(date);
				String format = DateUtil.format(formatDate, "yyyy-MM-dd");
				CustomerCashAssetsHistory customerCashAssetsHistory = customerInfoMapper.selCashBalanceList(format, statementDTO.getCustId());

				//当月1号0点之前总额
				Date newDate = DateUtil.offsetDay(DateUtil.beginOfMonth(date), -1);
				String format2 = DateUtil.format(newDate, "yyyy-MM-dd");
				CustomerCashAssetsHistory customerCashAssetsHistory2 = customerInfoMapper.selCashBalanceList(format2, statementDTO.getCustId());

				/**************************************/
				//投资组合总览: 投资总汇   买入的hld bond订单  持仓总览
				List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList = new ArrayList<>();
				List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList2 = new ArrayList<>();
				hldBondDailyVO.setInvestmentPools(investmentPoolList2);

				//交易单据
				List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList = new ArrayList<>();
				hldBondDailyVO.setTransactionContractNote(transactionContractNoteList);


				List<HldBondDailyVO.ChangeAccountVO> changeAccountVOList = new ArrayList<>();
				hldBondDailyVO.setChangeAccount(changeAccountVOList);

				//持货结存
				List<HldBondDailyVO.HoldingVO> holdingVOList = new ArrayList<>();
				List<HldBondDailyVO.HoldingVO> hldHoldingVOList = new ArrayList<>();
				List<HldBondDailyVO.HoldingVO> bondHoldingVOList = new ArrayList<>();
				hldBondDailyVO.setHoldings(holdingVOList);

				//债券易用户
				CustStatementVO bondVo = customerInfoMapper.selCustBondStatement(custId);
				//活利得用户
				CustStatementVO hldVo = customerInfoMapper.selCustHldStatement(custId);

				//户口出入金额变动  //查  活力德购买赎回记录 债券易的购买赎回记录 --资金流水记录
				getChangeAccountVO(custStatementDTO, customerCashAssetsHistory2, bondVo, hldVo, changeAccountVOList, customerCashAssetsHistory);
				//债券易用户
				if (bondVo != null && bondVo.getSubAccountId() != null) {
					custStatementDTO.setSubAccountId(bondVo.getSubAccountId());
					//汇总
					getHoldingOverViews(custStatementDTO, investmentPoolList, transactionContractNoteList, endLatestDate, bondVo, bondHoldingVOList);
				}

				//活利得用户
				BigDecimal accumulatedHldRevenue = new BigDecimal(0.00);
				if (hldVo != null && hldVo.getSubAccountId() != null) {
					custStatementDTO.setSubAccountId(hldVo.getSubAccountId());
					//汇总
					getHoldingOverViews(custStatementDTO, investmentPoolList, transactionContractNoteList, endLatestDate, hldVo, hldHoldingVOList);
				}

				//持货结存 汇总
				holdingVOList.addAll(hldHoldingVOList);
				holdingVOList.addAll(bondHoldingVOList);

				//交易单据 二次
				List<HldBondDailyVO.TransactionContractNoteVO> transactionHldList = new ArrayList<>();
				List<HldBondDailyVO.TransactionContractNoteVO> transactionBondList = new ArrayList<>();
				Map<Integer, List<HldBondDailyVO.TransactionContractNoteVO>> collect = transactionContractNoteList.stream().collect(Collectors.groupingBy(HldBondDailyVO.TransactionContractNoteVO::getType));
				for (Integer i : collect.keySet()) {
					if (Objects.equals(i, MarketTypeEnum.HLD.getType())) {
						transactionHldList = collect.get(i);
					}
					if (Objects.equals(i, MarketTypeEnum.BOND.getType())) {
						transactionBondList = collect.get(i);
					}
				}
				hldBondDailyVO.setTransactionHldContractNote(transactionHldList);
				hldBondDailyVO.setTransactionBondContractNote(transactionBondList);

				//投资组合总览: 现金变动结余
				cashChangeBalance(custStatementDTO, statementDTO, hldBondDailyVO, customerCashAssetsHistory, customerCashAssetsHistory2, transactionContractNoteList);

				//投资组合总览: 投资总汇
				//投资组合总览: 投资总汇(持仓总览)
				getInvestmentPoolVO(hldHoldingVOList, bondHoldingVOList, investmentPoolList);
				getTotalGainLoss(hldHoldingVOList, bondHoldingVOList,hldBondDailyVO);

				//投资总汇二次处理  根据币种分组  计算差值
				Map<String, List<HldBondDailyVO.InvestmentPoolVO>> listMap = investmentPoolList.stream().collect(Collectors.groupingBy(HldBondDailyVO.InvestmentPoolVO::getCurrency));
				listMap.forEach((k, v) -> {
					HldBondDailyVO.InvestmentPoolVO vo = new HldBondDailyVO.InvestmentPoolVO();

					BigDecimal totalBondSaleValue = v.stream().map(HldBondDailyVO.InvestmentPoolVO::getTotalBondSaleValue).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
					BigDecimal totalHldSaleValue = v.stream().map(HldBondDailyVO.InvestmentPoolVO::getTotalHldSaleValue).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalValue = totalBondSaleValue.multiply(v.get(0).getRate()).add(totalHldSaleValue.multiply(v.get(0).getRate()));
					vo.setCurrency(k);
					vo.setTotalHldValue(totalHldSaleValue);
					vo.setTotalBondValue(totalBondSaleValue);
					vo.setTotalValue(totalValue);
					vo.setRate(v.get(0).getRate());
					vo.setRates(v.get(0).getRate().setScale(4, RoundingMode.HALF_UP).toString());
					investmentPoolList2.add(vo);
				});
				//投资总汇 投资组合总值 (HKD 等值)
				hldBondDailyVO.setInvestmentPoolValue(BigDecimal.ZERO);
				BigDecimal investmentPoolValue = BigDecimal.ZERO;
				for (HldBondDailyVO.InvestmentPoolVO vo : investmentPoolList2) {
					BigDecimal totalValue = vo.getTotalValue();
					investmentPoolValue = totalValue.add(investmentPoolValue);
				}
				hldBondDailyVO.setInvestmentPoolValue(investmentPoolValue);

				//	户口出入金额变动 汇总
				hldBondDailyVO.setChangeAccountValue(BigDecimal.ZERO);

				//持货结存 : 总市值
				hldBondDailyVO.setMarketValue(BigDecimal.ZERO);
				for (HldBondDailyVO.HoldingVO vo : holdingVOList) {
					BigDecimal dayMktValue = vo.getDayMktValue();
					hldBondDailyVO.setMarketValue(hldBondDailyVO.getMarketValue().add(dayMktValue));
				}

				// 获取doc云端模板路径 /template/bond_daily_template.docx
				String bondMonthTemplateUrl = dictBizClient.getValue("statement_template", "month_statement_template").getData();
				log.info("月结单模板路径：：{}", bondMonthTemplateUrl);

				if (fileOperation(custStatementDTO, custId, hldBondDailyVO, bondMonthTemplateUrl, accountId, DateUtil.beginOfMonth(date)))
					continue;
				m++;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("执行日结单生成任务失败 cust_id{}", custId);
			}

		}
		log.info("月结单任务结束  data:{} 生成{}条结单", DateUtil.format(date, "yyyy-MM-dd"), m);
	}

	/**
	 * 获取最后一个交易日
	 * @param date
	 * @return
	 */
	public String getLatestTradeDate(Date date) {
		LocalDate lastDayOfMonth = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().with(java.time.temporal.TemporalAdjusters.lastDayOfMonth());
		List<TNonTradingDayConfig> tNonTradingDayConfigs = tNonTradingDayConfigMapper.selectList(null);
		List<Date> collect = tNonTradingDayConfigs.stream().map(TNonTradingDayConfig::getNontradingday).collect(Collectors.toList());

		// 如果最后一天是周六或周日，则向前回退到最近的工作日
		while (lastDayOfMonth.getDayOfWeek() == DayOfWeek.SATURDAY || lastDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY ||collect.contains(lastDayOfMonth)) {
			lastDayOfMonth = lastDayOfMonth.minusDays(1);
		}

		// 将LocalDate转换为yyyymmdd格式的字符串
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return lastDayOfMonth.format(formatter);
	}

	@Override
	public String getBaseTradeDate(Date date) {
		LocalDate localDate = date.toInstant() // 将 Date 转换为 Instant
			.atZone(ZoneId.systemDefault()) // 将 Instant 转换为 ZonedDateTime，使用系统默认时区
			.toLocalDate();

		List<TNonTradingDayConfig> tNonTradingDayConfigs = tNonTradingDayConfigMapper.selectList(null);
		List<Date> collect = tNonTradingDayConfigs.stream().map(TNonTradingDayConfig::getNontradingday).collect(Collectors.toList());

		// 如果最后一天是周六或周日，则向前加一天到最近的工作日
		while (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY ||collect.contains(localDate)) {
			localDate = localDate.plusDays(1);
		}

		// 将LocalDate转换为yyyymmdd格式的字符串
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDate.format(formatter);
	}

	/**
	 * 获取累计收益
	 * @param hldHoldingVOList
	 * @param bondHoldingVOList
	 */
	private void getTotalGainLoss(List<HldBondDailyVO.HoldingVO> hldHoldingVOList, List<HldBondDailyVO.HoldingVO> bondHoldingVOList, HldBondDailyVO hldBondDailyVO) {
		BigDecimal investmentTotalGainLoss = BigDecimal.ZERO;
		Map<String, BigDecimal> sumBondByCategory = bondHoldingVOList.stream()
			.filter(holding -> holding.getTotalGainLoss() != null)
			.collect(Collectors.groupingBy(
				HldBondDailyVO.HoldingVO::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					HldBondDailyVO.HoldingVO::getTotalGainLoss,
					BigDecimal::add)));
		// 输出结果
		sumBondByCategory.forEach((currency, totalHkGainLoss) ->
			log.info("bond持仓  Currency: " + currency + ", TotalGainLoss: " + totalHkGainLoss));

		Map<String, BigDecimal> sumHldByCategory = hldHoldingVOList.stream()
			.filter(holding -> holding.getTotalGainLoss() != null)
			.collect(Collectors.groupingBy(
				HldBondDailyVO.HoldingVO::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					HldBondDailyVO.HoldingVO::getTotalHkGainLoss,
					BigDecimal::add)));
		// 输出结果
		sumHldByCategory.forEach((currency, totalHkGainLoss) ->
			log.info("hld持仓  Currency: " + currency + ", TotalGainLoss: " + totalHkGainLoss));
		if (sumHldByCategory.containsKey("HKD") || sumBondByCategory.containsKey("HKD")) {
			BigDecimal bigDecimal1 = sumHldByCategory.get("HKD") == null ? BigDecimal.ZERO : sumHldByCategory.get("HKD");
			BigDecimal bigDecimal2 = sumBondByCategory.get("HKD") == null ? BigDecimal.ZERO : sumBondByCategory.get("HKD");
			investmentTotalGainLoss = investmentTotalGainLoss.add(bigDecimal1).add(bigDecimal2);
		}
		if (sumHldByCategory.containsKey("USD") || sumBondByCategory.containsKey("USD")) {
			BigDecimal bigDecimal1 = sumHldByCategory.get("USD") == null ? BigDecimal.ZERO : sumHldByCategory.get("USD");
			BigDecimal bigDecimal2 = sumBondByCategory.get("USD") == null ? BigDecimal.ZERO : sumBondByCategory.get("USD");
			investmentTotalGainLoss = investmentTotalGainLoss.add(bigDecimal1).add(bigDecimal2);
		}
		if (sumHldByCategory.containsKey("CNY") || sumBondByCategory.containsKey("CNY")) {
			BigDecimal bigDecimal1 = sumHldByCategory.get("CNY") == null ? BigDecimal.ZERO : sumHldByCategory.get("CNY");
			BigDecimal bigDecimal2 = sumBondByCategory.get("CNY") == null ? BigDecimal.ZERO : sumBondByCategory.get("CNY");
			investmentTotalGainLoss = investmentTotalGainLoss.add(bigDecimal1).add(bigDecimal2);
		}
		hldBondDailyVO.setInvestmentTotalGainLoss(investmentTotalGainLoss);
	}

	private String getAddressNameKey(String value) {
        StringBuilder key = new StringBuilder(NAME_KEY);
        if (StringUtil.isNotBlank(value)) {
            key.append(":");
            key.append(value);
        }
        return key.toString();
    }

    @Override
    public void exportHldBondDailyAccountList3(CustStatementDTO custStatementDTO) {

    }


	/*public static void main(String[] args) throws Exception {
		//XWPFDocument document = new XWPFDocument();
		//XWPFDocument document = new XWPFDocument();
		XWPFDocument document = new XWPFDocument(new FileInputStream("D:\\jiedan\\example.docx"));

		//FileOutputStream out = new FileOutputStream("D:\\jiedan\\example.docx");
		// 添加页眉页脚（示例代码省略具体实现，请参考Apache POI文档）

		// 创建表格
		XWPFTable table1 = document.createTable(4, 3); // 4行3列

		//table.setWidth(XWPFTable.XWPFBorderType.DASHED);
		XWPFTableRow row1 = table1.getRow(0);
		row1.getCell(0).setText("杜总");
		row1.getCell(1).setText("客户户口: " + "12345");
		row1.getCell(2).setText("编印于 " + "2024-08-07");

		XWPFTableRow row2 = table1.getRow(1);
		row2.getCell(0).setText("深圳");
		row2.getCell(1).setText("户口类别 : 现金");
		row2.getCell(2).setText("客户主任 : HOUSE");

		XWPFTable table2 = document.createTable(2, 1); // 4行3列
		table2.getRow(0).getCell(0).setText("综合成交单据及账户日结单 (" + "2024-08-07" + ")");
		table2.getRow(1).getCell(0).setText("投资组合总览");
		CTTblBorders tblBorders = table2.getCTTbl().getTblPr().getTblBorders();
		if (tblBorders == null) {
			tblBorders = CTTblBorders.Factory.newInstance();
			table2.getCTTbl().getTblPr().setTblBorders(tblBorders);
		}
		// 设置上边框
		tblBorders.getTop().setVal(STBorder.NONE); // 或者使用setSz(BigInteger.ZERO) 来显式设置宽度为0


		// 创建一个表格 - 示例：现金变动结余
		XWPFTable table3 = document.createTable(2, 6); // 创建2行6列的表格
		setTableHeader(table3, new String[]{"币种", "上日结余", "今日变动额", "今日交收", "待交收", "净结余(结欠)"});

		// 假设的数据
		List<String[]> data1 = new ArrayList<>();
		data1.add(new String[]{"HKD", "10000", "500", "400", "100", "10400"});
		fillTableData(table3, data1);

		// 创建一个新的表格 - 示例：投资总汇
		XWPFTable table4 = document.createTable(2, 8); // 创建2行8列的表格
		setTableHeader(table4, new String[]{"币种", "活利得", "债券易", "基金", "股票", "数字货币", "其他", "总投资"});

		List<String[]> data2 = new ArrayList<>();
		data2.add(new String[]{"HKD", "2000", "3000", "4000", "5000", "1000", "500", "15500"});
		fillTableData(table4, data2);


		//table1.setTableAlignment(TableRowAlign.CENTER);
		//table2.setTableAlignment(TableRowAlign.CENTER);
		//table3.setTableAlignment(TableRowAlign.CENTER);
		//table4.setTableAlignment(TableRowAlign.CENTER);
		// 将文档保存到文件
		//String filePath = "D:\\jiedan\\example.docx";

		// 将文档保存到文件
		String filePath = "D:\\example.docx";

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			document.write(out);
			System.out.println("Word文档已保存到本地：" + filePath);

			System.out.println("Word文档创建成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}


	}*/


}
