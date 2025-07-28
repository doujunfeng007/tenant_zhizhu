package com.minigod.zero.customer.back.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.back.service.CustomerStatementService;
import com.minigod.zero.customer.back.service.ICustomerInfoService;
import com.minigod.zero.customer.dto.StatementFileListDTO;
import com.minigod.zero.customer.dto.StatementHistoryListDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.emuns.StatementEnums;
import com.minigod.zero.customer.entity.CustomerFile;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.CustomerStatementFileHistoryEntity;
import com.minigod.zero.customer.enums.CustEnums;
import com.minigod.zero.customer.enums.StatementEnum;
import com.minigod.zero.customer.enums.StatusEnum;
import com.minigod.zero.customer.mapper.CustomerFileMapper;
import com.minigod.zero.customer.mapper.CustomerInfoMapper;
import com.minigod.zero.customer.mapper.CustomerStatementFileHistoryMapper;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.PushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static io.protostuff.CollectionSchema.MessageFactories.Set;

/**
 * @ClassName: com.minigod.zero.customer.back.service.impl.CustomerStatementServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:41
 * @Version: 1.0
 */
@Slf4j
@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {
	@Resource
	private CustomerInfoMapper customerInfoMapper;
	@Resource
	private CustomerFileMapper customerFileMapper;

	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private CustomerStatementFileHistoryMapper customerStatementFileHistoryMapper;
	@Resource
	private ICustomerInfoService customerInfoService;

	/**
	 * 汇总统计
	 *
	 * @param statementListDTO
	 * @return
	 */
	@Override
	public R getTabulateStatistics(StatementListDTO statementListDTO) {

		StatementTabulateStatisticsVO statementTabulateStatisticsVO = new StatementTabulateStatisticsVO();
		List<CustomerOpenAccountVO> customerStatementList = customerInfoService.getCustomerStatementList(null, statementListDTO);
		int custSize = customerStatementList.stream()
			.map(CustomerOpenAccountVO::getCustId)
			.collect(Collectors.toSet()).size();
		statementTabulateStatisticsVO.setCustSize(custSize);
		statementTabulateStatisticsVO.setStatementSize(customerStatementList.size());

		return R.data(statementTabulateStatisticsVO);
	}

	/**
	 * 发送结单邮件
	 *
	 * @param list
	 * @return
	 */
	@Override
	public R sendStatementEmail(List<StatementFileListDTO> list) {
		int size = list.size();
		int successSize = 0;
		for (StatementFileListDTO dto : list) {
			CustomerFile customerFile = customerFileMapper.selectById(dto.getStatementFileId());
			if (customerFile == null || dto.getOpenEmail() == null) {
				log.error("文件不存在或未填写邮箱，无法发送邮件 statementFileId:{}", dto.getStatementFileId());
				continue;
			}
			if (Objects.equals(customerFile.getStatus(), StatementEnums.FileSendStatus.SEND_SUCCESS.getCode())
				|| Objects.equals(customerFile.getStatus(), StatementEnums.FileSendStatus.SEND_ING.getCode())) {
				log.info("文件已发送成功，无需再次发送 statementFileId:{}", dto.getStatementFileId());
				continue;
			}
			Date time = new Date();

			// 标题参数
			List<String> titleParams = new ArrayList<>();
			String param = DateUtil.format(customerFile.getDate(), "yyyy/MM/dd");

			titleParams.add(param);
			//正文
			List<String> bodyParams = new ArrayList<>();
			bodyParams.add(param);
			SendEmailDTO sendEmailDTO = new SendEmailDTO();
			sendEmailDTO.setAccepts(Arrays.asList(dto.getOpenEmail()));
			sendEmailDTO.setAttachmentUrls(Arrays.asList(customerFile.getUrl()));
			sendEmailDTO.setTitleParam(titleParams);
			sendEmailDTO.setList(bodyParams);
			sendEmailDTO.setSendId("statement_"+IdUtil.getSnowflakeNextIdStr());


			if (customerFile.getType() == StatementEnum.DAY.getCode()) {
				//发送消息通知
				List<String> pushParams = new ArrayList<>();
				String param2 = DateUtil.format(customerFile.getDate(), "yyyy-MM-dd");
				pushParams.add(param2);
				PushUtil.builder()
					.msgGroup("P")
					.custId(customerFile.getCustId())
					.params(pushParams)
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.templateCode(PushTemplate.DAILY_STATEMENT.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();

				sendEmailDTO.setCode(EmailTemplate.DAILY_STATEMENT.getCode());
			} else if (customerFile.getType() == StatementEnum.MONTH.getCode()) {
				List<String> pushParams = new ArrayList<>();
				String param2 = DateUtil.format(customerFile.getDate(), "yyyy-MM");
				pushParams.add(param2);
				PushUtil.builder()
					.msgGroup("P")
					.custId(customerFile.getCustId())
					.params(pushParams)
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.templateCode(PushTemplate.MONTHLY_STATEMENT.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();

				sendEmailDTO.setCode(EmailTemplate.MONTHLY_STATEMENT.getCode());
			}
			R sendRes = platformMsgClient.sendEmail(sendEmailDTO);
			if (sendRes != null && sendRes.isSuccess()) {
				log.info("发送结单邮件成功!!!");
				customerFile.setStatus(StatementEnums.FileSendStatus.SEND_ING.getCode());
				customerFile.setUpdateTime(time);
				customerFileMapper.updateById(customerFile);
				successSize++;
			} else {
				log.error("发送结单邮件失败!!! msg:{}", sendRes.getMsg());
				customerFile.setStatus(StatementEnums.FileSendStatus.SEND_FAIL.getCode());
				customerFile.setUpdateTime(time);
				customerFileMapper.updateById(customerFile);
			}
			CustomerStatementFileHistoryEntity fileHistory = customerStatementFileHistoryMapper.selectOne(Wrappers.<CustomerStatementFileHistoryEntity>lambdaQuery()
				.eq(CustomerStatementFileHistoryEntity::getStatementFileId, dto.getStatementFileId()));
			if (fileHistory == null) {
				fileHistory = new CustomerStatementFileHistoryEntity();
				fileHistory.setStatementFileId(dto.getStatementFileId());
				fileHistory.setSendTime(time);
				fileHistory.setSendEmailId(sendEmailDTO.getSendId());
				fileHistory.setEmail(dto.getOpenEmail());
				fileHistory.setSendNum(1);
				fileHistory.setCreateTime(time);
				fileHistory.setUpdateTime(time);
				customerStatementFileHistoryMapper.insert(fileHistory);
			} else {
				fileHistory.setSendNum(fileHistory.getSendNum() + 1);
				fileHistory.setSendTime(time);
				fileHistory.setSendEmailId(sendEmailDTO.getSendId());
				fileHistory.setEmail(dto.getOpenEmail());
				fileHistory.setUpdateTime(time);
				customerStatementFileHistoryMapper.updateById(fileHistory);
			}
		}
		int i = size - successSize;
		if (i > 0){
			log.error("一共发送" + size + "封结单邮件,成功发送" + successSize + "封结单邮件,失败" + i + "封");
			return R.fail("一共发送" + size + "封结单邮件,成功发送" + successSize + "封结单邮件,失败" + i + "封");
		}else {
			return R.success("一共发送" + size + "封结单邮件,成功发送" + successSize + "封结单邮件,失败" + i + "封");
		}

	}

	@Override
	public R<IPage<StatementHistoryListVO>> customerStatementhistory(IPage<StatementHistoryListVO> page, StatementHistoryListDTO statementHistoryListDTO) {
		List<StatementHistoryListVO> list = getStatementHistoryListVOS(page, statementHistoryListDTO);
		page.setRecords(list);
		return R.data(page);

	}

	@Override
	public List<StatementHistoryListVO> getStatementHistoryListVOS(IPage<StatementHistoryListVO> page, StatementHistoryListDTO statementHistoryListDTO) {

		if (StringUtil.isBlank(statementHistoryListDTO.getStartTime()) && StringUtil.isBlank(statementHistoryListDTO.getEndTime())) {
			DateTime yesterday = DateUtil.yesterday();
			String format = DateUtil.format(yesterday, "yyyy-MM-dd");
			statementHistoryListDTO.setStartTime(format);
			statementHistoryListDTO.setEndTime(format);
		}
		List<StatementHistoryListVO> list = customerStatementFileHistoryMapper.customerStatementList(page, statementHistoryListDTO);
		return list;
	}

	@Override
	public void downFlile(StatementListDTO statementListDTO, HttpServletResponse response) {
		if (StringUtil.isBlank(statementListDTO.getStartTime()) && StringUtil.isBlank(statementListDTO.getEndTime())) {

			String format ="";
			if (statementListDTO.getStatementType() == StatementEnum.DAY.getCode()){
				DateTime yesterday = cn.hutool.core.date.DateUtil.yesterday();
				format = cn.hutool.core.date.DateUtil.format(yesterday, "yyyy-MM-dd");
			}else if (statementListDTO.getStatementType() == StatementEnum.MONTH.getCode()){
				DateTime yesterday = cn.hutool.core.date.DateUtil.lastMonth();
				format = cn.hutool.core.date.DateUtil.format(yesterday, "yyyy-MM");
			}
			statementListDTO.setStartTime(format);
			statementListDTO.setEndTime(format);
		}
		List<CustomerOpenAccountVO> list = customerInfoMapper.customerStatementList(null,statementListDTO);
		List<StatementUrlVO> dailyUrlVOList = list.stream()
			.filter(vo -> vo.getDailyStatement() != null && !vo.getDailyStatement().isEmpty())
			.map(vo -> {
				String url = vo.getDailyStatement();
				String fileName = vo.getStatementFileName();
				return new StatementUrlVO(url, fileName);
			})
			.collect(Collectors.toList());

		List<StatementUrlVO> monthUrlVOList = list.stream()
			.filter(vo -> vo.getMonthlyStatement() != null && !vo.getMonthlyStatement().isEmpty())
			.map(vo -> {
				String url = vo.getMonthlyStatement();
				String fileName = vo.getStatementFileName();
				return new StatementUrlVO(url, fileName);
			})
			.collect(Collectors.toList());

		dailyUrlVOList.addAll(monthUrlVOList);

		// 临时目录
		Path tempDir = Paths.get("temp");
		if (!Files.exists(tempDir)) {
			try {
				Files.createDirectory(tempDir);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		// 下载PDF文件并保存到临时目录
		for (CustomerOpenAccountVO customerOpenAccountVO : list) {
			if (StringUtil.isBlank(customerOpenAccountVO.getDailyStatement()) && StringUtil.isBlank(customerOpenAccountVO.getMonthlyStatement())) {
				continue;
			}
			if (StringUtil.isBlank(customerOpenAccountVO.getDailyStatement())) {
				customerOpenAccountVO.setDailyStatement(customerOpenAccountVO.getMonthlyStatement());
			} else if (StringUtil.isBlank(customerOpenAccountVO.getMonthlyStatement())) {
				customerOpenAccountVO.setMonthlyStatement(customerOpenAccountVO.getDailyStatement());
			}
		}
		for (StatementUrlVO statementUrlVO : dailyUrlVOList) {
			String url = statementUrlVO.getUrl();
			String fileName = statementUrlVO.getFileName();
			if (StringUtil.isBlank(fileName)){
				fileName = url.substring(url.lastIndexOf('/') + 1);
			}
			try (InputStream in = new URL(url).openStream()) {
				Files.copy(in, tempDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 设置响应头
		String encodedFileName = URLEncoder.encode("结单文件.zip", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", encodedFileName));

		// 压缩文件并写入响应流
		try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
			Files.walk(tempDir)
				.filter(path -> !Files.isDirectory(path))
				.forEach(path -> {
					ZipEntry zipEntry = new ZipEntry(tempDir.relativize(path).toString());
					try (InputStream fis = Files.newInputStream(path)) {
						zos.putNextEntry(zipEntry);
						byte[] buffer = new byte[1024];
						int len;
						while ((len = fis.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
						zos.closeEntry();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 删除临时目录
			try {
				Files.walk(tempDir)
					.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public R<List<StatementHistoryStatisticsVO>> getHistoryStatistics(StatementHistoryListDTO statementHistoryListDTO) {
		ArrayList<StatementHistoryStatisticsVO> statisticsList = new ArrayList<>();
		List<StatementHistoryListVO> list = getStatementHistoryListVOS(null, statementHistoryListDTO);
		int custSize = list.stream()
			.map(StatementHistoryListVO::getCustId)
			.collect(Collectors.toSet()).size();

		log.info("custSize:{}", custSize);
		Map<Integer, List<StatementHistoryListVO>> collect = list.stream().collect(Collectors.groupingBy(StatementHistoryListVO::getStatementType));
		collect.forEach((k, v) -> {
			StatementHistoryStatisticsVO statisticsVO = new StatementHistoryStatisticsVO();
			Integer count = v.stream().mapToInt(StatementHistoryListVO::getSendNum).sum();
			statisticsVO.setStatementSize(count);
			statisticsVO.setCustSize(custSize);
			long failCount = v.stream().filter(item ->
				item.getStatementStatus().equals(StatementEnums.FileSendStatus.SEND_FAIL.getCode())).count();
			statisticsVO.setStatementFalseSize(Math.toIntExact(failCount));
			long unsetCount = v.stream().filter(item ->
				item.getStatementStatus().equals(StatementEnums.FileSendStatus.UN_SET.getCode())).count();
			statisticsVO.setStatementUnSetSize(Math.toIntExact(unsetCount));
			long successCount = v.stream().filter(item ->
				item.getStatementStatus().equals(StatementEnums.FileSendStatus.SEND_SUCCESS.getCode())).count();
			statisticsVO.setStatementSuccessSize(Math.toIntExact(successCount));
			statisticsVO.setStatementType(k);
			statisticsVO.setStartSectionTime(statementHistoryListDTO.getStartTime());
			statisticsVO.setEndSectionTime(statementHistoryListDTO.getEndTime());
			statisticsList.add(statisticsVO);
		});

		return R.data(statisticsList);
	}
}
