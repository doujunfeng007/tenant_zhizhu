package com.minigod.zero.data.task.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.data.Jsonfilter.BigDecimalValueFilter;
import com.minigod.zero.data.config.CustomerStatementAccountPdfProperties;
import com.minigod.zero.data.config.PDFTemplate;
import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.dto.StatementDTO;
import com.minigod.zero.data.entity.*;
import com.minigod.zero.data.enums.*;
import com.minigod.zero.data.fegin.IAddrClient;
import com.minigod.zero.data.mapper.*;
import com.minigod.zero.data.mapper.CustomerFileMapper;
import com.minigod.zero.data.task.service.CustomerStatementAccountService;
import com.minigod.zero.data.utils.AsposeUtil;
import com.minigod.zero.data.utils.ExportUtil;
import com.minigod.zero.data.utils.FileUtils;
import com.minigod.zero.data.vo.*;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private CustomerFileMapper customerFileMapper;
    @Resource
    private IOssClient ossClient;
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
    private CustomerFundHoldingHistoryMapper customerFundHoldingHistoryMapper;
    @Resource
    private MstarFundNewestPriceMapper mstarFundNewestPriceMapper;
    @Resource
    private MstarFundPriceMapper mstarFundPriceMapper;

    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private CustomerBasicInfoMapper customerBasicInfoMapper;
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
        //hld bond fund订单成功的人
        List<String> hldOrderList = customerInfoMapper.selHldOrderList(DateUtil.beginOfDay(date), DateUtil.endOfDay(date));
        List<String> bondOrderList = customerInfoMapper.selBondOrderList(DateUtil.beginOfDay(date), DateUtil.endOfDay(date));
        List<String> fundOrderList = customerInfoMapper.selFundOrderList(DateUtil.beginOfDay(date), DateUtil.endOfDay(date));


		List<String> custList = Stream.of(accountList, hldOrderList, bondOrderList,fundOrderList) // 使用Stream.of合并多个列表
			.flatMap(List::stream) // 展开为单一流
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
                List<HldBondDailyVO.HoldingVO> fundHoldingVOList = new ArrayList<>();
                List<HldBondDailyVO.FundHoldingVO> fundHoldingVOList2 = new ArrayList<>();
                hldBondDailyVO.setHoldings(holdingVOList);
                hldBondDailyVO.setFundholdings(fundHoldingVOList2);

                //债券易用户
                CustStatementVO bondVo = customerInfoMapper.selCustBondStatement(custId);

                //活利得用户
				CustStatementVO hldVo = customerInfoMapper.selCustHldStatement(custId);

				//基金用户
				CustStatementVO fundVo = customerInfoMapper.selCustFundStatement(custId);


                //户口出入金额变动  //查  活力德购买赎回记录 债券易的购买赎回记录 --资金流水记录
                getChangeAccountVO(custStatementDTO, customerCashAssetsHistory2, changeAccountVOList);

				// 处理不同类型的用户  交易成交单据 持货结存(初始化)
				//债券易用户
				processUser(bondVo, custStatementDTO, transactionContractNoteList, bondHoldingVOList, MarketTypeEnum.BOND);
				//活利得用户
				processUser(hldVo, custStatementDTO, transactionContractNoteList, hldHoldingVOList, MarketTypeEnum.HLD);
				//基金用户
				processUser(fundVo, custStatementDTO, transactionContractNoteList, fundHoldingVOList, MarketTypeEnum.FUND);

                //持货结存 汇总
                holdingVOList.addAll(hldHoldingVOList);
                holdingVOList.addAll(bondHoldingVOList);
                //holdingVOList.addAll(fundHoldingVOList);
                //投资组合总览: 投资总汇
                //投资组合总览: 投资总汇(持仓总览)
                getInvestmentPoolVO(hldHoldingVOList, bondHoldingVOList,fundHoldingVOList, investmentPoolList);
                getTotalGainLoss(hldHoldingVOList, bondHoldingVOList,fundHoldingVOList,hldBondDailyVO);

                //交易单据 二次
                List<HldBondDailyVO.TransactionContractNoteVO> transactionHldList = new ArrayList<>();
                List<HldBondDailyVO.TransactionContractNoteVO> transactionBondList = new ArrayList<>();
                List<HldBondDailyVO.TransactionContractNoteVO> transactionFundList = new ArrayList<>();
                List<HldBondDailyVO.FundTransactionContractNoteVO> transactionFundList2 = new ArrayList<>();
                Map<Integer, List<HldBondDailyVO.TransactionContractNoteVO>> collect = transactionContractNoteList.stream().collect(Collectors.groupingBy(HldBondDailyVO.TransactionContractNoteVO::getType));
                for (Integer i : collect.keySet()) {
                    if (Objects.equals(i, MarketTypeEnum.HLD.getType())) {
                        transactionHldList = collect.get(i);
                    }
                    if (Objects.equals(i, MarketTypeEnum.BOND.getType())) {
                        transactionBondList = collect.get(i);
                    }
                    if (Objects.equals(i, MarketTypeEnum.FUND.getType())) {
						transactionFundList = collect.get(i);
                    }
                }
                hldBondDailyVO.setTransactionHldContractNote(transactionHldList);
                hldBondDailyVO.setTransactionBondContractNote(transactionBondList);
                hldBondDailyVO.setTransactionFundContractNote(transactionFundList);
                hldBondDailyVO.setTransactionFundContractNotes(transactionFundList2);
				//基金部分字段 取4位小数
				fundFourdispose(fundHoldingVOList, fundHoldingVOList2, transactionFundList, transactionFundList2);

				//投资组合总览: 现金变动结余
                cashChangeBalance(custStatementDTO, statementDTO, hldBondDailyVO, customerCashAssetsHistory,customerCashAssetsHistory2, transactionContractNoteList);

				// 投资总汇二次处理，根据币种分组计算差值
				Map<String, List<HldBondDailyVO.InvestmentPoolVO>> listMap = investmentPoolList.stream()
					.collect(Collectors.groupingBy(HldBondDailyVO.InvestmentPoolVO::getCurrency));

				listMap.forEach((currency, investmentPoolVOList) -> {
					HldBondDailyVO.InvestmentPoolVO aggregatedVO = new HldBondDailyVO.InvestmentPoolVO();

					// 计算各项总值
					BigDecimal totalBondSaleValue = investmentPoolVOList.stream()
						.map(HldBondDailyVO.InvestmentPoolVO::getTotalBondSaleValue)
						.filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalHldSaleValue = investmentPoolVOList.stream()
						.map(HldBondDailyVO.InvestmentPoolVO::getTotalHldSaleValue)
						.filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalFundSaleValue = investmentPoolVOList.stream()
						.map(HldBondDailyVO.InvestmentPoolVO::getTotalFundSaleValue)
						.filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					log.info("totalBondSaleValue:{}, totalHldSaleValue:{}", totalBondSaleValue, totalHldSaleValue);
					log.info("getRate:{}", investmentPoolVOList.get(0).getRate());

					// 计算总值
					BigDecimal rate = investmentPoolVOList.get(0).getRate();
					BigDecimal totalValue = (totalBondSaleValue.multiply(rate))
							.add(totalHldSaleValue.multiply(rate)
							.add(totalFundSaleValue.multiply(rate)));
					log.info("totalValue:{}", totalValue);

					// 设置聚合结果
					aggregatedVO.setCurrency(currency);
					aggregatedVO.setTotalHldValue(totalHldSaleValue);
					aggregatedVO.setTotalBondValue(totalBondSaleValue);
					aggregatedVO.setTotalFundValue(totalFundSaleValue);
					aggregatedVO.setTotalValue(totalValue);
					aggregatedVO.setRate(rate);
					aggregatedVO.setRates(rate.setScale(4, BigDecimal.ROUND_DOWN).toString());

					investmentPoolList2.add(aggregatedVO);
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

	private void fundFourdispose(List<HldBondDailyVO.HoldingVO> fundHoldingVOList,
								 List<HldBondDailyVO.FundHoldingVO> fundHoldingVOList2,
								 List<HldBondDailyVO.TransactionContractNoteVO> transactionFundList,
								 List<HldBondDailyVO.FundTransactionContractNoteVO> transactionFundList2) {
		//基金持仓 特殊处理 部分字段取4位
		for (HldBondDailyVO.HoldingVO holdingVO : fundHoldingVOList) {
			HldBondDailyVO.FundHoldingVO fundHoldingVO = new HldBondDailyVO.FundHoldingVO();
			BeanUtil.copyProperties(holdingVO, fundHoldingVO);
			fundHoldingVO.setFundTodayBalance(holdingVO.getTodayBalance());
			fundHoldingVO.setFundTodayChangeBalance(holdingVO.getTodayChangeBalance());
			fundHoldingVO.setFundNetBalance(holdingVO.getNetBalance());
			fundHoldingVO.setFundClosingPrice(holdingVO.getClosingPrice());
			fundHoldingVOList2.add(fundHoldingVO);
		}

		//基金交易单据 特殊处理 部分字段取4位
		for (HldBondDailyVO.TransactionContractNoteVO transactionContractNoteVO : transactionFundList) {
			HldBondDailyVO.FundTransactionContractNoteVO fundTransactionContractNoteVO = new HldBondDailyVO.FundTransactionContractNoteVO();
			BeanUtil.copyProperties(transactionContractNoteVO, fundTransactionContractNoteVO);
			fundTransactionContractNoteVO.setFundBusinessPrice(transactionContractNoteVO.getBusinessPrice());
			fundTransactionContractNoteVO.setFundTotalQuantity(transactionContractNoteVO.getTotalQuantity());
			fundTransactionContractNoteVO.setSettlementAmount(transactionContractNoteVO.getSettlementAmount());
			transactionFundList2.add(fundTransactionContractNoteVO);
		}
	}

	private void processUser(CustStatementVO userVo, CustStatementDTO custStatementDTO,
							 List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList,
							 List<HldBondDailyVO.HoldingVO> hldHoldingVOList,
							 MarketTypeEnum marketTypeEnum) {
		if (userVo != null && userVo.getSubAccountId() != null) {
			userVo.setFundsType(marketTypeEnum.getType());
			custStatementDTO.setSubAccountId(userVo.getSubAccountId());

			LambdaQueryWrapper<TaTDaClientEntity> taWrapper = new LambdaQueryWrapper<>();
			taWrapper.eq(TaTDaClientEntity::getDaclientid, userVo.getSubAccountId());
			taWrapper.orderByDesc(TaTDaClientEntity::getCreatetime);
			taWrapper.last("limit 1");

			TaTDaClientEntity taTDaClientEntity = taTDaClientMapper.selectOne(taWrapper);
			if (taTDaClientEntity != null) {
				custStatementDTO.setTaSubAccountId(taTDaClientEntity.getSubaccountid());
			}

			// 汇总
			getHoldingOverViews(custStatementDTO, transactionContractNoteList, custStatementDTO.getEndDate(), userVo, hldHoldingVOList);
		}
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
            customerFile.setStatus(StatementEnum.FileSendStatus.UN_SET.getCode());
			customerFile.setStatementType(StatementEnum.StatementType.FUND.getType());
            customerFileMapper.insert(customerFile);
        }
        //多数据源

        return false;
    }


    private static String getPdfPath(String custId, HldBondDailyVO hldBondDailyVO, String bondDailyTemplateUrl, String makeOutputPath, String accountId) {
        String jsonString = JSON.toJSONString(hldBondDailyVO, new BigDecimalValueFilter());
        //String jsonString = JSON.toJSONString(hldBondDailyVO);
        log.info("活利得、债券易日结单服务器jsonString：{}", jsonString);
        if (StringUtils.isEmpty(bondDailyTemplateUrl)){
            //bondDailyTemplateUrl = "F:\\数金\\消息模版\\month_statement_template-1121.docx";
            bondDailyTemplateUrl = "F:\\数金\\消息模版\\day_statement_template-1121.docx";
        }
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
        List<Integer> depositIds = Arrays.asList(14,26,27,28,32,34,35,36,56,43,52,53,54,55); // 入金类型 ThawingType  FlowBusinessType
        statementDTO.setWithdrawIds(depositIds);
        statementDTO.setCustId(custStatementDTO.getCustId());
        List<FinancingAccountAmountFlows> depositList = accountBusinessFlowMapper.selWithdrawList(statementDTO);

        //获取出金额  需要判断审核是否成功
        List<Integer> withdrawIds = Arrays.asList(15,19,29,30,31,37,38,42,44,46); // 出金类型
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

            String idCardProvinceName = " ";
            String idCardCityName = " ";
            String idCardCountyName = " ";

			if (customerBasicInfoEntity.getIdKind() == 1){
				//大陆开户显示省市区
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

	/**
	 * 投资组合总览: 现金变动结余  明细
	 * @param hldHoldingVOList
	 * @param bondHoldingVOList
	 * @param fundHoldingVOList
	 * @param investmentPoolList
	 */
	private void getInvestmentPoolVO(List<HldBondDailyVO.HoldingVO> hldHoldingVOList,
									 List<HldBondDailyVO.HoldingVO> bondHoldingVOList,
									 List<HldBondDailyVO.HoldingVO> fundHoldingVOList,
									 List<HldBondDailyVO.InvestmentPoolVO> investmentPoolList) {

		// 计算各类别的市场价值
		Map<String, BigDecimal> sumBondByCategory = bondHoldingVOList.stream()
			.filter(holding -> holding.getDayMktValue() != null)
			.collect(Collectors.groupingBy(
				HldBondDailyVO.HoldingVO::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					HldBondDailyVO.HoldingVO::getDayMktValue,
					BigDecimal::add)));

		Map<String, BigDecimal> sumHldByCategory = hldHoldingVOList.stream()
			.filter(holding -> holding.getDayMktValue() != null)
			.collect(Collectors.groupingBy(
				HldBondDailyVO.HoldingVO::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					HldBondDailyVO.HoldingVO::getDayMktValue,
					BigDecimal::add)));

		Map<String, BigDecimal> sumFundByCategory = fundHoldingVOList.stream()
			.filter(holding -> holding.getDayMktValue() != null)
			.collect(Collectors.groupingBy(
				HldBondDailyVO.HoldingVO::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					HldBondDailyVO.HoldingVO::getDayMktValue,
					BigDecimal::add)));

		// 输出结果
		sumBondByCategory.forEach((currency, totalAmount) ->
			log.info("bond持仓  Currency: " + currency + ", DayMktValue: " + totalAmount));

		sumHldByCategory.forEach((currency, totalAmount) ->
			log.info("hld持仓  Currency: " + currency + ", DayMktValue: " + totalAmount));

		sumFundByCategory.forEach((currency, totalAmount) ->
			log.info("fund持仓  Currency: " + currency + ", DayMktValue: " + totalAmount));

		// 处理每种货币
		for (String currency : Arrays.asList("HKD", "USD", "CNY","CNH")) {
			if (sumHldByCategory.containsKey(currency)
				|| sumBondByCategory.containsKey(currency)
				|| sumFundByCategory.containsKey(currency)) {
				HldBondDailyVO.InvestmentPoolVO investmentPoolVO = new HldBondDailyVO.InvestmentPoolVO();
				if (currency.equals("CNY") || currency.equals("CNH")){
					investmentPoolVO.setCurrency("CNY");
				}else {
					investmentPoolVO.setCurrency(currency);
				}

				investmentPoolVO.setTotalHldSaleValue(sumHldByCategory.getOrDefault(currency, BigDecimal.ZERO));
				investmentPoolVO.setTotalBondSaleValue(sumBondByCategory.getOrDefault(currency, BigDecimal.ZERO));
				investmentPoolVO.setTotalFundSaleValue(sumFundByCategory.getOrDefault(currency, BigDecimal.ZERO));

				// 设置汇率
				hldHoldingVOList.stream()
					.filter(buyConfirmForm -> buyConfirmForm.getCurrency().equals(currency))
					.findFirst()
					.ifPresent(buyConfirmForm -> investmentPoolVO.setRate(buyConfirmForm.getRate()));

				bondHoldingVOList.stream()
					.filter(buyConfirmForm -> buyConfirmForm.getCurrency().equals(currency))
					.findFirst()
					.ifPresent(buyConfirmForm -> investmentPoolVO.setRate(buyConfirmForm.getRate()));

				fundHoldingVOList.stream()
					.filter(buyConfirmForm -> buyConfirmForm.getCurrency().equals(currency))
					.findFirst()
					.ifPresent(buyConfirmForm -> investmentPoolVO.setRate(buyConfirmForm.getRate()));

				investmentPoolList.add(investmentPoolVO);
			}
		}
	}

    @NotNull
	private void getHoldingOverViews(CustStatementDTO custStatementDTO,
									 List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList,
									 Date date, CustStatementVO bondVo,
									 List<HldBondDailyVO.HoldingVO> holdingVOList) {
		// 获取债券易日结单买入确认单集合
		List<CustomerHldBondDailyAccountVO.BuyConfirmForm> buyConfirmFormList = new ArrayList<>();
		// 获取债券易日结单卖出确认单集合
		List<CustomerHldBondDailyAccountVO.SellConfirmForm> sellConfirmFormList = new ArrayList<>();
		// 获取活利得日结单买入确认单
		List<HldTradingBuyStatementDailyVO> hldBuyDailyList = new ArrayList<>();
		// 获取活利得日结单卖出确认单
		List<HldTradingSaleStatementDailyVO> hldSaleDailyList = new ArrayList<>();
		// 获取基金日结单买入确认单
		List<FundTradingBuyStatementDailyVO> fundBuyDailyList = new ArrayList<>();
		// 获取基金日结单卖出确认单
		List<FundTradingSaleStatementDailyVO> fundSaleDailyList = new ArrayList<>();


		if (Objects.equals(bondVo.getFundsType(), MarketTypeEnum.HLD.getType())) {
			// 获取活利得日结单买入确认单
			hldBuyDailyList = customerInfoMapper.hldTradingStatementList(custStatementDTO);
			// 获取活利得日结单卖出确认单
			hldSaleDailyList = customerInfoMapper.hldTradingSaleStatementList(custStatementDTO);
			// 获取活利得日结单持仓总览集合
			//List<HldHoldingHistoryStatementDailyVO> hldHoldingOverViewDailyList = customerInfoMapper.hldHoldingHistoryStatementList(custStatementDTO);
			List<HldHoldingHistoryStatementDailyVO> hldHoldingOverViewDailyList = customerInfoMapper.hldHoldingHistoryList(custStatementDTO);
			for (HldHoldingHistoryStatementDailyVO vo : hldHoldingOverViewDailyList) {
				BigDecimal totalQuantity = customerInfoMapper.hldHoldingHistoryByFundCode(vo.getProductId(), custStatementDTO);
				if (vo.getQuantity() != null && vo.getQuantity().compareTo(BigDecimal.ZERO) <= 0 && totalQuantity != null && totalQuantity.compareTo(BigDecimal.ZERO) <= 0) {
					//判断历史持仓是否为0，如果是0,说明之前已经清仓了则不显示
					continue;
				}
				BigDecimal exchRate = tExchRateHisMapper.selRateByDate(date, vo.getCurrency());
				vo.setRate(exchRate == null ? BigDecimal.ONE : exchRate);

				HldBondDailyVO.HoldingVO holdingVO = new HldBondDailyVO.HoldingVO();
				holdingVO.setMarket(MarketTypeEnum.HLD.getMarket());
				holdingVO.setType(MarketTypeEnum.HLD.getType());
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
					if (vo.getId().equals(customerHldHoldingHistory.getId())) {
						holdingVO.setTodayBalance(customerHldHoldingHistory.getTotalQuantity());
						holdingVO.setTodayChangeBalance(BigDecimal.ZERO);
					} else {
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
				TaTHoldingHistoryEntity taTHoldingHistory = taTHoldingHistoryMapper.getRealizedGainLoss(custStatementDTO, vo.getProductId());
				if (taTHoldingHistory == null) {
					holdingVO.setTotalGainLoss(BigDecimal.ZERO);
					holdingVO.setTotalHkGainLoss(BigDecimal.ZERO);
				} else {
					//买单取结单日之前的利息，卖单取结单日的利息
					boolean sellOrder = hldSaleDailyList.stream()
						.anyMatch(item -> Objects.equals(item.getHldCode(), vo.getHldCode()));
					if (sellOrder){
						taTHoldingHistory = taTHoldingHistoryMapper.getHldRealizedGainLoss(custStatementDTO, vo.getProductId());
					}
					BigDecimal sumInterest = taTHoldingHistoryMapper.getHldSumInterest(custStatementDTO, taTHoldingHistory.getSubaccountid(), vo.getProductId());
					//已实现收益
					BigDecimal realizedgainloss = taTHoldingHistory.getRealizedgainloss();

					//累计总收益
					BigDecimal totalGainLoss = sumInterest.subtract(realizedgainloss);
					log.info("hld 持仓收益:{} code:{}", sumInterest, vo.getHldCode());

					holdingVO.setTotalGainLoss(totalGainLoss);
					holdingVO.setTotalHkGainLoss(totalGainLoss.multiply(holdingVO.getRate()));
				}
				holdingVOList.add(holdingVO);

			}
		}

		if (Objects.equals(bondVo.getFundsType(), MarketTypeEnum.BOND.getType())) {
			// 获取债券易日结单买入确认单集合
			buyConfirmFormList = customerBondTradingTransactionMapper.getDailyBuyConfirmFormList(custStatementDTO);
			// 获取债券易日结单卖出确认单集合
			sellConfirmFormList = customerBondTradingTransactionMapper.getDailySellConfirmFormList(custStatementDTO);
			// 获取债券易日结单持仓总览集合
			//List<CustomerHldBondDailyAccountVO.HoldingOverView> holdingOverViewList = customerBondTradingTransactionMapper.getDailyHoldingOverViewList(custStatementDTO);//读取当前持仓
			//读取历史持仓最后一条记录
			List<CustomerHldBondDailyAccountVO.HoldingOverView> holdingOverViewList = customerBondTradingTransactionMapper.getDailyHoldingHistoryOverViewList(custStatementDTO);//读取历史持仓

			//持货结存
			for (CustomerHldBondDailyAccountVO.HoldingOverView holdingOverView : holdingOverViewList) {
				//查询汇率
				BigDecimal exchRate = tExchRateHisMapper.selRateByDate(date, holdingOverView.getCurrency());
				holdingOverView.setRate(exchRate == null ? BigDecimal.ONE : exchRate);
				//当前时间持仓
				BigDecimal totalQuantity = customerBondTradingTransactionMapper.getDailyHoldingHistoryOverViewByFundCode(holdingOverView.getProductId(), custStatementDTO);
				if (holdingOverView.getTotalQuantity() != null && holdingOverView.getTotalQuantity().compareTo(BigDecimal.ZERO) <= 0 && totalQuantity != null && totalQuantity.compareTo(BigDecimal.ZERO) <= 0) {
					//判断历史持仓是否为0，如果是0,说明之前已经清仓了则不显示
					continue;
				}
				HldBondDailyVO.HoldingVO holdingVO = new HldBondDailyVO.HoldingVO();
				holdingVO.setMarket(MarketTypeEnum.BOND.getMarket());
				holdingVO.setType(MarketTypeEnum.BOND.getType());
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
					if (holdingOverView.getId().equals(customerBondHoldingHistory.getId())) {
						holdingVO.setTodayBalance(customerBondHoldingHistory.getTotalQuantity());
						holdingVO.setTodayChangeBalance(BigDecimal.ZERO);
					} else {
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
				//买单取结单日之前的平均成本，卖单取结单日的平均成本
				/*boolean sellOrder = sellConfirmFormList.stream()
					.anyMatch(item -> Objects.equals(item.getFundCode(), holdingOverView.getBondCode()));
				TaTHoldingHistoryEntity taTHoldingHistory = new TaTHoldingHistoryEntity();
				if (sellOrder){
					taTHoldingHistory = taTHoldingHistoryMapper.getRealizedGainLoss(custStatementDTO, holdingOverView.getProductId());
				}else {
					taTHoldingHistory = taTHoldingHistoryMapper.getHldRealizedGainLoss(custStatementDTO, holdingOverView.getProductId());
				}
				if (taTHoldingHistory.getAveragecost() == null) {
					holdingVO.setTotalGainLoss(BigDecimal.ZERO);
					holdingVO.setTotalHkGainLoss(BigDecimal.ZERO);
				} else {
					BigDecimal averagecost = taTHoldingHistory.getAveragecost();
					averagecost = (holdingVO.getClosingPrice().subtract(averagecost)).multiply(holdingVO.getNetBalance());
					log.info("bond 持仓收益:{} ProductId:{}", averagecost, holdingOverView.getProductId());
					holdingVO.setTotalGainLoss(averagecost);
					holdingVO.setTotalHkGainLoss(averagecost.multiply(holdingVO.getRate()));
				}*/

				holdingVOList.add(holdingVO);
			}
		}
		if (Objects.equals(bondVo.getFundsType(), MarketTypeEnum.FUND.getType())) {
			// 获取基金日结单买入确认单
			fundBuyDailyList = customerInfoMapper.fundTradingStatementList(custStatementDTO);
			// 获取基金日结单卖出确认单
			fundSaleDailyList = customerInfoMapper.fundTradingSaleStatementList(custStatementDTO);

			// 获取基金日结单持仓总览集合
			//List<HldHoldingHistoryStatementDailyVO> hldHoldingOverViewDailyList = customerInfoMapper.hldHoldingHistoryStatementList(custStatementDTO);
			List<HldHoldingHistoryStatementDailyVO> fundHoldingOverViewDailyList = customerInfoMapper.fundHoldingHistoryList(custStatementDTO);
			for (HldHoldingHistoryStatementDailyVO vo : fundHoldingOverViewDailyList) {
				BigDecimal totalQuantity = customerInfoMapper.fundHoldingHistoryByFundCode(vo.getProductId(), custStatementDTO);
				if (vo.getQuantity() != null && vo.getQuantity().compareTo(BigDecimal.ZERO) <= 0 && totalQuantity != null && totalQuantity.compareTo(BigDecimal.ZERO) <= 0) {
					//判断历史持仓是否为0，如果是0,说明之前已经清仓了则不显示
					continue;
				}
				BigDecimal exchRate = tExchRateHisMapper.selRateByDate(date, vo.getCurrency());
				vo.setRate(exchRate == null ? BigDecimal.ONE : exchRate);

				HldBondDailyVO.HoldingVO holdingVO = new HldBondDailyVO.HoldingVO();
				holdingVO.setMarket(MarketTypeEnum.FUND.getMarket());
				holdingVO.setType(MarketTypeEnum.FUND.getType());
				holdingVO.setProductName(vo.getHldName());
				holdingVO.setProductCode(vo.getHldName()+"   "+vo.getHldCode());

				holdingVO.setCurrency(vo.getCurrency());

				//查询平均成本  历史持仓里面取
				LambdaQueryWrapper<CustomerFundHoldingHistory> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(CustomerFundHoldingHistory::getFundcode, vo.getProductId());
				queryWrapper.lt(CustomerFundHoldingHistory::getCreatetime, custStatementDTO.getStartDate());
				queryWrapper.eq(CustomerFundHoldingHistory::getSubaccountid, bondVo.getSubAccountId())
					.orderByDesc(CustomerFundHoldingHistory::getCreatetime).last("limit 1");
				CustomerFundHoldingHistory customerFundHoldingHistory = customerFundHoldingHistoryMapper.selectOne(queryWrapper);
				holdingVO.setTodayBalance(vo.getQuantity() == null ? BigDecimal.ZERO : vo.getQuantity());

				if (customerFundHoldingHistory == null) {
					holdingVO.setTodayBalance(BigDecimal.ZERO);
					holdingVO.setTodayChangeBalance(vo.getQuantity());
				} else {
					if (vo.getId().equals(customerFundHoldingHistory.getId())) {
						holdingVO.setTodayBalance(customerFundHoldingHistory.getTotalquantity());
						holdingVO.setTodayChangeBalance(BigDecimal.ZERO);
					} else {
						holdingVO.setTodayBalance(customerFundHoldingHistory.getTotalquantity());
						holdingVO.setTodayChangeBalance(vo.getQuantity().subtract(customerFundHoldingHistory.getTotalquantity()));
					}
				}


				holdingVO.setNetBalance(holdingVO.getTodayChangeBalance().add(holdingVO.getTodayBalance()));

				//获取结单日的产品净值
				String format = DateUtil.format(custStatementDTO.getEndDate(), "yyyy-MM-dd");
				LambdaQueryWrapper<MstarFundPrice> fundWrapper = new LambdaQueryWrapper<>();
				fundWrapper.eq(MstarFundPrice::getPerformanceId, vo.getPerformanceId())
					.eq(MstarFundPrice::getCcy, vo.getCurrency())
					.le(MstarFundPrice::getPriceDate, format)
					.orderByDesc(MstarFundPrice::getCreateTime).last("limit 1");
				MstarFundPrice mstarFundPrice = mstarFundPriceMapper.selectOne(fundWrapper);
				holdingVO.setClosingPrice(new BigDecimal(mstarFundPrice.getPrice()));
				holdingVO.setRate(vo.getRate());
				holdingVO.setDayMktValue(holdingVO.getClosingPrice().multiply(holdingVO.getNetBalance()));

				holdingVO.setRate(vo.getRate());

				//累计利息单独计算 根据平均成本计算市值之间差
				//获取平均成本
				//BigDecimal averageCost = holdingOverView.getAverageCost();
				/*if (customerFundHoldingHistory == null) {
					holdingVO.setTotalGainLoss(BigDecimal.ZERO);
					holdingVO.setTotalHkGainLoss(BigDecimal.ZERO);
				} else {
					BigDecimal averagecost = customerFundHoldingHistory.getAveragecost();
					averagecost = (holdingVO.getClosingPrice().subtract(averagecost)).multiply(holdingVO.getNetBalance());
					log.info("fund 持仓收益:{} ProductId:{}", averagecost, vo.getProductId());
					holdingVO.setTotalGainLoss(averagecost);
					holdingVO.setTotalHkGainLoss(averagecost.multiply(holdingVO.getRate()));
				}*/

				holdingVOList.add(holdingVO);

			}
		}


		//交易成交单据
		getTransactionContractNoteVO(buyConfirmFormList, transactionContractNoteList, hldBuyDailyList, sellConfirmFormList, hldSaleDailyList,fundBuyDailyList,fundSaleDailyList);

	}


    private void getChangeAccountVO(CustStatementDTO custStatementDTO,
                                    CustomerCashAssetsHistory customerCashAssetsHistory2,
                                    List<HldBondDailyVO.ChangeAccountVO> changeAccountVOList) {
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
                changeAccountVO.setFlowNetBalance(changeAccountVO.getDepositAccount());
            } else {
                changeAccountVO.setWithdrawAmount(flow.getAmount().multiply(new BigDecimal("-1")));
                changeAccountVO.setFlowNetBalance(changeAccountVO.getWithdrawAmount());
            }
            //changeAccountVOList.add(changeAccountVO);
            if (flow.getCurrency().equals("HKD")) {
                changeAccountVOHkdList.add(changeAccountVO);
                hkdCashAssets2 = hkdCashAssets2.add(changeAccountVO.getFlowNetBalance());

            } else if (flow.getCurrency().equals("CNY")) {
                changeAccountVOCnyList.add(changeAccountVO);
                cnyCashAssets2 = cnyCashAssets2.add(changeAccountVO.getFlowNetBalance());

            } else if (flow.getCurrency().equals("USD")) {
                changeAccountVOUsdList.add(changeAccountVO);
                usdCashAssets2 = usdCashAssets2.add(changeAccountVO.getFlowNetBalance());

            }
        }



        HldBondDailyVO.ChangeAccountVO changeAccountVO4 = new HldBondDailyVO.ChangeAccountVO();
        changeAccountVO4.setContents("转下现金结余");
        changeAccountVO4.setCurrency("HKD");
        changeAccountVO4.setFlowNetBalance(hkdCashAssets2.add(hkdCashAssets1));

        HldBondDailyVO.ChangeAccountVO changeAccountVO5 = new HldBondDailyVO.ChangeAccountVO();
        changeAccountVO5.setContents("转下现金结余");
        changeAccountVO5.setCurrency("CNY");
        changeAccountVO5.setFlowNetBalance(cnyCashAssets2.add(cnyCashAssets1));

        HldBondDailyVO.ChangeAccountVO changeAccountVO6 = new HldBondDailyVO.ChangeAccountVO();
        changeAccountVO6.setContents("转下现金结余");
        changeAccountVO6.setCurrency("USD");
        changeAccountVO6.setFlowNetBalance(usdCashAssets2.add(usdCashAssets1));

        HldBondDailyVO.ChangeAccountVO changeAccountVO1 = new HldBondDailyVO.ChangeAccountVO();
        changeAccountVO1.setContents("承上现金结余");
        changeAccountVO1.setCurrency("HKD");
        changeAccountVO1.setFlowNetBalance(hkdCashAssets1);

        changeAccountVOList.add(changeAccountVO1);
        changeAccountVOList.addAll(changeAccountVOHkdList);
        changeAccountVOList.add(changeAccountVO4);

        HldBondDailyVO.ChangeAccountVO changeAccountVO2 = new HldBondDailyVO.ChangeAccountVO();
        changeAccountVO2.setContents("承上现金结余");
        changeAccountVO2.setCurrency("CNY");
        changeAccountVO2.setFlowNetBalance(cnyCashAssets1);

        changeAccountVOList.add(changeAccountVO2);
        changeAccountVOList.addAll(changeAccountVOCnyList);
        changeAccountVOList.add(changeAccountVO5);

        HldBondDailyVO.ChangeAccountVO changeAccountVO3 = new HldBondDailyVO.ChangeAccountVO();
        changeAccountVO3.setContents("承上现金结余");
        changeAccountVO3.setCurrency("USD");
        changeAccountVO3.setFlowNetBalance(usdCashAssets1);

        changeAccountVOList.add(changeAccountVO3);
        changeAccountVOList.addAll(changeAccountVOUsdList);
        changeAccountVOList.add(changeAccountVO6);

    }

	/**
	 *
	 * @param depositMap 入金map
	 * @param withdrawMap 出金map
	 * @param hldBondDailyVO 总
	 * @param yesterdayTotalAssetsHistory  当天资产
	 * @param yesterdayTotalAssetsHistory2 昨天资产
	 * @param transactionContractNoteList
	 */
	private void getCashChangeBalance(Map<String, BigDecimal> depositMap,
									  Map<String, BigDecimal> withdrawMap,
									  HldBondDailyVO hldBondDailyVO,
									  CustomerCashAssetsHistory yesterdayTotalAssetsHistory,
									  CustomerCashAssetsHistory yesterdayTotalAssetsHistory2,
									  List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteList) {
		// 投资组合总览: 现金变动结余
		List<HldBondDailyVO.CashChangeBalanceVO> changeBalanceVOArrayList = new ArrayList<>();

		// 创建现金变动对象
		String[] currencies = {"HKD", "CNY", "USD"};
		for (String currency : currencies) {
			HldBondDailyVO.CashChangeBalanceVO cashChangeBalanceVO = new HldBondDailyVO.CashChangeBalanceVO();
			cashChangeBalanceVO.setCurrency(currency);

			// 获取昨日资产和在途金额
			BigDecimal yesterdayAssets = yesterdayTotalAssetsHistory2 != null ?
				(currency.equals("HKD") ? yesterdayTotalAssetsHistory2.getHkdAssets() :
					currency.equals("CNY") ? yesterdayTotalAssetsHistory2.getCnyAssets() :
						yesterdayTotalAssetsHistory2.getUsdAssets()) : BigDecimal.ZERO;

			BigDecimal intransit = yesterdayTotalAssetsHistory != null ?
				(currency.equals("HKD") ? yesterdayTotalAssetsHistory.getHkdIntransit() :
					currency.equals("CNY") ? yesterdayTotalAssetsHistory.getCnyIntransit() :
						yesterdayTotalAssetsHistory.getUsdIntransit()) : BigDecimal.ZERO;

			cashChangeBalanceVO.setBackBalance(intransit);
			cashChangeBalanceVO.setYesterdayBalance(yesterdayAssets);

			// 处理交易单据
			Map<String, List<HldBondDailyVO.TransactionContractNoteVO>> collect = transactionContractNoteList.stream()
				.collect(Collectors.groupingBy(HldBondDailyVO.TransactionContractNoteVO::getTradeKind));
			List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteVOBuyList = collect.getOrDefault("买入", new ArrayList<>());
			List<HldBondDailyVO.TransactionContractNoteVO> transactionContractNoteVOSellList = collect.getOrDefault("卖出", new ArrayList<>());
			transactionContractNoteVOBuyList.addAll(collect.getOrDefault("申购", new ArrayList<>())) ;
			transactionContractNoteVOSellList.addAll(collect.getOrDefault("赎回", new ArrayList<>())) ;

			// 计算买入和卖出金额
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

			log.info("买入单据和 sumBuyByCategory:{} currency:{}", sumBuyByCategory, currency);
			log.info("卖出单据和 sumSellByCategory:{} currency:{}", sumSellByCategory,currency);

			// 今日交收 公式=卖出金额-买入金额
			BigDecimal pendingBalance = (sumSellByCategory.get(currency) == null ? BigDecimal.ZERO : sumSellByCategory.get(currency))
				.subtract(sumBuyByCategory.get(currency) == null ? BigDecimal.ZERO : sumBuyByCategory.get(currency));
			cashChangeBalanceVO.setPendingBalance(pendingBalance);

			// 今日变动 入金额-出金额+活利得卖出的利息+债券易的派息-活利得和债券易的费用
			BigDecimal cashAssets = (depositMap.get(currency) == null ? BigDecimal.ZERO : depositMap.get(currency))
				.subtract(withdrawMap.get(currency) == null ? BigDecimal.ZERO : withdrawMap.get(currency));
			cashChangeBalanceVO.setTodayCashChangeBalance(cashAssets);

			// 净结余: 上日结余+今日变动额+今日交收+待交收
			cashChangeBalanceVO.setTodayCashBalance(cashChangeBalanceVO.getYesterdayBalance()
				.add(cashChangeBalanceVO.getTodayCashChangeBalance())
				.add(cashChangeBalanceVO.getPendingBalance())
				.add(cashChangeBalanceVO.getBackBalance()));

			changeBalanceVOArrayList.add(cashChangeBalanceVO);
		}

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
                                                     List<HldTradingSaleStatementDailyVO> hldSaleDailyList,
													 List<FundTradingBuyStatementDailyVO> fundBuyDailyList,
													 List<FundTradingSaleStatementDailyVO> fundSaleDailyList) {
		//债券易买入 交易成交单据
        for (CustomerHldBondDailyAccountVO.BuyConfirmForm buyConfirmForm : buyConfirmFormList) {
			//t_transaction表中amount字段买入包含(预付利息)和手续费，
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
            vo.setSettlementAmount(buyConfirmForm.getAmount().multiply(BigDecimal.valueOf(-1)));
            vo.setRate(buyConfirmForm.getRate());

            //应付日数
            //vo.setDaysPayable(buyConfirmForm);
            transactionContractNoteList.add(vo);

        }

		//活利得买入 交易成交单据
        for (HldTradingBuyStatementDailyVO hldTradingBuyStatementDailyVO : hldBuyDailyList) {
			//t_transaction表中amount字段买入包含(预付利息)和手续费，
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
            vo.setSettlementAmount((hldTradingBuyStatementDailyVO.getAmout().add(hldTradingBuyStatementDailyVO.getTransactionGainLoss())).multiply(BigDecimal.valueOf(-1)));
            vo.setRate(hldTradingBuyStatementDailyVO.getRate());
            //应付日数
            //vo.setDaysPayable(buyConfirmForm);
            transactionContractNoteList.add(vo);

        }

		//基金买入 交易成交单据
		for (FundTradingBuyStatementDailyVO hldTradingBuyStatementDailyVO : fundBuyDailyList) {
			//基金买入：t_transaction表中amount = 交易净额+费用
			HldBondDailyVO.TransactionContractNoteVO vo = new HldBondDailyVO.TransactionContractNoteVO();
			vo.setTradeDate(hldTradingBuyStatementDailyVO.getTransactionDate());
			vo.setType(MarketTypeEnum.FUND.getType());
			vo.setTradeKind("申购");
			vo.setSubmitDate(hldTradingBuyStatementDailyVO.getSubmitDate());
			vo.setTransactionAlias(hldTradingBuyStatementDailyVO.getOrderId());//参考编号
			vo.setProductCode(hldTradingBuyStatementDailyVO.getHldName()+"  "+hldTradingBuyStatementDailyVO.getHldCode());
			vo.setCurrency(hldTradingBuyStatementDailyVO.getCurrency());
			vo.setTotalQuantity(hldTradingBuyStatementDailyVO.getQuantity());
			vo.setBusinessPrice(hldTradingBuyStatementDailyVO.getBusinessPrice());
			vo.setBusinessBalance(hldTradingBuyStatementDailyVO.getAmout());
			vo.setFee(hldTradingBuyStatementDailyVO.getFee());
			vo.setSettlementAmount(hldTradingBuyStatementDailyVO.getAmout().multiply(BigDecimal.valueOf(-1)));
			vo.setRate(hldTradingBuyStatementDailyVO.getRate());
			//应付日数
			//vo.setDaysPayable(buyConfirmForm);
			transactionContractNoteList.add(vo);

		}

		//债券易卖出 交易成交单据
        for (CustomerHldBondDailyAccountVO.SellConfirmForm sellConfirmForm : sellConfirmFormList) {
			// t_transaction表中amount = 卖出包含利息-手续费
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

		//活力得卖出 交易成交单据
        for (HldTradingSaleStatementDailyVO hldTradingSaleStatementDailyVO : hldSaleDailyList) {
			//t_transaction表中amount = 卖出包含利息-手续费
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

		//基金卖出 交易成交单据
		for (FundTradingSaleStatementDailyVO hldTradingSaleStatementDailyVO : fundSaleDailyList) {
			//基金卖出：t_transaction表中amount = 赎回总金额 - 费用
			HldBondDailyVO.TransactionContractNoteVO vo = new HldBondDailyVO.TransactionContractNoteVO();
			vo.setTradeDate(hldTradingSaleStatementDailyVO.getTransactionDate());
			vo.setType(MarketTypeEnum.FUND.getType());
			vo.setTradeKind("赎回");
			vo.setSubmitDate(hldTradingSaleStatementDailyVO.getSubmitDate());
			vo.setTransactionAlias(hldTradingSaleStatementDailyVO.getOrderId());//参考编号
			vo.setProductCode(hldTradingSaleStatementDailyVO.getHldName()+"  "+hldTradingSaleStatementDailyVO.getHldCode());
			vo.setCurrency(hldTradingSaleStatementDailyVO.getCurrency());
			vo.setTotalQuantity(hldTradingSaleStatementDailyVO.getQuantity());
			vo.setBusinessPrice(hldTradingSaleStatementDailyVO.getBusinessPrice());
			vo.setBusinessBalance(hldTradingSaleStatementDailyVO.getAmout());
			vo.setFee(hldTradingSaleStatementDailyVO.getFee());
			vo.setSettlementAmount(hldTradingSaleStatementDailyVO.getAmout().add(hldTradingSaleStatementDailyVO.getFee()));
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
        List<String> fundOrderList = customerInfoMapper.selFundOrderList(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));

        //资金不为0 的人
        List<String> cashCustList = customerInfoMapper.selCashByCustList(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));

        //持仓不为空的人
        List<String> selCustList = customerInfoMapper.selCustList();

        accountList.addAll(hldOrderList);
        accountList.addAll(bondOrderList);
        accountList.addAll(fundOrderList);
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

                log.info("月结单 custId: {} accountId:{} accountDate:{}", custId ,accountId,hldBondDailyVO.getAccountDate() );
                statementDTO.setAccountId(hldBondDailyVO.getAccountId());


                //当月30号总额
                Date formatDate = DateUtil.endOfMonth(date);
                String format = DateUtil.format(formatDate, "yyyy-MM-dd");
                CustomerCashAssetsHistory customerCashAssetsHistory = customerInfoMapper.selCashBalanceList(format, statementDTO.getCustId());

                //当月1号0点之前总额
                Date newDate = DateUtil.offsetDay(DateUtil.beginOfMonth(date), -1);
                String format2 = DateUtil.format(newDate, "yyyy-MM-dd");
                CustomerCashAssetsHistory customerCashAssetsHistory2 = customerInfoMapper.selCashBalanceList(format2, statementDTO.getCustId());


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
				List<HldBondDailyVO.HoldingVO> fundHoldingVOList = new ArrayList<>();
				List<HldBondDailyVO.FundHoldingVO> fundHoldingVOList2 = new ArrayList<>();
				hldBondDailyVO.setHoldings(holdingVOList);
				hldBondDailyVO.setFundholdings(fundHoldingVOList2);

				//债券易用户
				CustStatementVO bondVo = customerInfoMapper.selCustBondStatement(custId);

				//活利得用户
				CustStatementVO hldVo = customerInfoMapper.selCustHldStatement(custId);

				//基金用户
				CustStatementVO fundVo = customerInfoMapper.selCustFundStatement(custId);


				//户口出入金额变动  //查  活力德购买赎回记录 债券易的购买赎回记录 --资金流水记录
                getChangeAccountVO(custStatementDTO, customerCashAssetsHistory2, changeAccountVOList);

				// 处理不同类型的用户  交易成交单据 持货结存(初始化)
				//债券易用户
				//债券易用户
				processUser(bondVo, custStatementDTO, transactionContractNoteList, bondHoldingVOList, MarketTypeEnum.BOND);
				//活利得用户
				processUser(hldVo, custStatementDTO, transactionContractNoteList, hldHoldingVOList, MarketTypeEnum.HLD);
				//基金用户
				processUser(fundVo, custStatementDTO, transactionContractNoteList, fundHoldingVOList, MarketTypeEnum.FUND);


                //持货结存 汇总
                holdingVOList.addAll(hldHoldingVOList);
                holdingVOList.addAll(bondHoldingVOList);
				//投资组合总览: 投资总汇
				//投资组合总览: 投资总汇(持仓总览)
				getInvestmentPoolVO(hldHoldingVOList, bondHoldingVOList,fundHoldingVOList, investmentPoolList);
				getTotalGainLoss(hldHoldingVOList, bondHoldingVOList,fundHoldingVOList,hldBondDailyVO);

				//交易单据 二次
				List<HldBondDailyVO.TransactionContractNoteVO> transactionHldList = new ArrayList<>();
				List<HldBondDailyVO.TransactionContractNoteVO> transactionBondList = new ArrayList<>();
				List<HldBondDailyVO.TransactionContractNoteVO> transactionFundList = new ArrayList<>();
				List<HldBondDailyVO.FundTransactionContractNoteVO> transactionFundList2 = new ArrayList<>();
				Map<Integer, List<HldBondDailyVO.TransactionContractNoteVO>> collect = transactionContractNoteList.stream().collect(Collectors.groupingBy(HldBondDailyVO.TransactionContractNoteVO::getType));
				for (Integer i : collect.keySet()) {
					if (Objects.equals(i, MarketTypeEnum.HLD.getType())) {
						transactionHldList = collect.get(i);
					}
					if (Objects.equals(i, MarketTypeEnum.BOND.getType())) {
						transactionBondList = collect.get(i);
					}
					if (Objects.equals(i, MarketTypeEnum.FUND.getType())) {
						transactionFundList = collect.get(i);
					}
				}
				hldBondDailyVO.setTransactionHldContractNote(transactionHldList);
				hldBondDailyVO.setTransactionBondContractNote(transactionBondList);
				hldBondDailyVO.setTransactionFundContractNote(transactionFundList);
				hldBondDailyVO.setTransactionFundContractNotes(transactionFundList2);

				//基金持仓 交易单据 部分字段 取4位小数
				fundFourdispose(fundHoldingVOList, fundHoldingVOList2, transactionFundList, transactionFundList2);

				//投资组合总览: 现金变动结余
				cashChangeBalance(custStatementDTO, statementDTO, hldBondDailyVO, customerCashAssetsHistory,customerCashAssetsHistory2, transactionContractNoteList);

				//投资组合总览: 现金变动结余
                cashChangeBalance(custStatementDTO, statementDTO, hldBondDailyVO, customerCashAssetsHistory, customerCashAssetsHistory2, transactionContractNoteList);

                //投资总汇二次处理  根据币种分组  计算差值
				Map<String, List<HldBondDailyVO.InvestmentPoolVO>> listMap = investmentPoolList.stream()
					.collect(Collectors.groupingBy(HldBondDailyVO.InvestmentPoolVO::getCurrency));

				listMap.forEach((currency, investmentPoolVOList) -> {
					HldBondDailyVO.InvestmentPoolVO aggregatedVO = new HldBondDailyVO.InvestmentPoolVO();

					// 计算各项总值
					BigDecimal totalBondSaleValue = investmentPoolVOList.stream()
						.map(HldBondDailyVO.InvestmentPoolVO::getTotalBondSaleValue)
						.filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalHldSaleValue = investmentPoolVOList.stream()
						.map(HldBondDailyVO.InvestmentPoolVO::getTotalHldSaleValue)
						.filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal totalFundSaleValue = investmentPoolVOList.stream()
						.map(HldBondDailyVO.InvestmentPoolVO::getTotalFundSaleValue)
						.filter(Objects::nonNull)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					log.info("totalBondSaleValue:{}, totalHldSaleValue:{}", totalBondSaleValue, totalHldSaleValue);
					log.info("getRate:{}", investmentPoolVOList.get(0).getRate());

					// 计算总值
					BigDecimal rate = investmentPoolVOList.get(0).getRate();
					BigDecimal totalValue = (totalBondSaleValue.multiply(rate))
													.add(totalHldSaleValue.multiply(rate)
													.add(totalFundSaleValue.multiply(rate)));
					log.info("totalValue:{}", totalValue);

					// 设置聚合结果
					aggregatedVO.setCurrency(currency);
					aggregatedVO.setTotalHldValue(totalHldSaleValue);
					aggregatedVO.setTotalBondValue(totalBondSaleValue);
					aggregatedVO.setTotalFundValue(totalFundSaleValue);
					aggregatedVO.setTotalValue(totalValue);
					aggregatedVO.setRate(rate);
					aggregatedVO.setRates(rate.setScale(4, BigDecimal.ROUND_DOWN).toString());

					investmentPoolList2.add(aggregatedVO);
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
				List<HldBondDailyVO.HoldingVO> holdingVOMktList = new ArrayList<>();
				holdingVOMktList.addAll(holdingVOList);
				holdingVOMktList.addAll(fundHoldingVOList);
				for (HldBondDailyVO.HoldingVO vo : holdingVOMktList) {
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
    private void getTotalGainLoss(List<HldBondDailyVO.HoldingVO> hldHoldingVOList, List<HldBondDailyVO.HoldingVO> bondHoldingVOList,List<HldBondDailyVO.HoldingVO> fundHoldingVOList, HldBondDailyVO hldBondDailyVO) {

		// 计算各类别的总收益损失
		Map<String, BigDecimal> sumByCategory = Stream.of(bondHoldingVOList, hldHoldingVOList, fundHoldingVOList)
			.flatMap(List::stream)
			.filter(holding -> holding.getTotalGainLoss() != null)
			.collect(Collectors.groupingBy(
				HldBondDailyVO.HoldingVO::getCurrency,
				Collectors.reducing(BigDecimal.ZERO,
					HldBondDailyVO.HoldingVO::getTotalHkGainLoss,
					BigDecimal::add)));

		// 输出结果
		sumByCategory.forEach((currency, totalHkGainLoss) ->
			log.info("持仓收益 Currency: " + currency + ", TotalGainLoss: " + totalHkGainLoss));

		// 计算投资总收益损失
		BigDecimal investmentTotalGainLoss = sumByCategory.values().stream()
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		log.info("投资组合 TotalGainLoss: " + investmentTotalGainLoss);
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





}
