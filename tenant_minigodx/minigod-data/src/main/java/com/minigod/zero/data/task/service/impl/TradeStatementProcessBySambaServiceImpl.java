package com.minigod.zero.data.task.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.MktConstants;
import com.minigod.zero.data.config.CustomerStatementAccountPdfProperties;
import com.minigod.zero.data.entity.CustomerFile;
import com.minigod.zero.data.enums.CustomerStatementAccountFileEnum;
import com.minigod.zero.data.enums.StatementEnum;
import com.minigod.zero.data.mapper.CustomerFileMapper;
import com.minigod.zero.data.task.service.TradeStatementProcessBySambaService;
import com.minigod.zero.data.utils.SmbFileUtil;
import com.minigod.zero.data.utils.SmbToMultipartFileConversion;
import com.minigod.zero.data.vo.SmbFileVO;
import com.minigod.zero.resource.feign.IOssClient;
import jcifs.smb1.smb1.SmbFile;
import jcifs.smb1.smb1.SmbFileInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author: wengzejie
 * @createTime: 2024/12/26 13:59
 * @description:
 */
@Slf4j
@Service
public class TradeStatementProcessBySambaServiceImpl implements TradeStatementProcessBySambaService {

	@Resource
	private CustomerFileMapper customerFileMapper;
	@Resource
	private IOssClient ossClient;
	@Resource
	private CustomerStatementAccountPdfProperties accountPdfProperties;

	@Value("${statements.smb.host}")
	private String smbHost;
	@Value("${statements.smb.user}")
	private String smbUser;
	@Value("${statements.smb.pwd}")
	private String smbPwd;
	@Value("${statements.smb.publicDir}")
	private String publicDir;
	private static final String DEST_DIR = "download";
	private static final int RANDOM_FILENAME_LEN = 30; // 随机文件名长度

	@Override
	public void processStatementFiles() {
		try {
			SmbFileUtil smbFileUtil = new SmbFileUtil(true, smbHost, publicDir);
			smbFileUtil.login(smbHost, smbUser, smbPwd);
			List<SmbFileVO> smbFileVOS = smbFileUtil.getFileVOList("");
			log.info("processStatementFiles count smbFileVOS:{}", smbFileVOS.size());
			log.info("processStatementFiles count smbFileVOS:{}", smbFileVOS);
			int sCount = 0;
			for (SmbFileVO smbFileVO : smbFileVOS) {
				boolean bool = this.processFile(smbFileVO);
				if (bool) {
					sCount++;
				}
			}
			log.info("processStatementFiles handler successCount: {}", sCount);
		} catch (Exception e) {
			log.error("processStatementFiles handler error is ", e);
		}
	}

	private boolean processFile(SmbFileVO smbFileVO) {
		try {
			String name = smbFileVO.getName();
			if (!StringUtils.endsWithIgnoreCase(name, ".pdf")) {
				// 只处理pdf文件
				log.debug("ignore non-pdf: {}", name);
				return false;
			}
			String[] items = StringUtils.split(name, '-');
			if (items == null || items.length < 3) {
				log.debug("ignore other pdf: {}", name);
				return false;
			}
			String ymd = StringUtils.trim(items[0]);
			String fundAccount = StringUtils.trim(items[1]);
			String attr = StringUtils.trim(items[2]);
			// 解析日期
			DateTime date = parseDate(ymd);
			String from = publicDir + "/" + name;
			String to = buildDestinationFilename(date);

			CommonsMultipartFile multipartFile = SmbToMultipartFileConversion.convertSmbFileToMultipartFile(smbFileVO.getFile());

			String link = null;
			String fileName = null;

			log.info("路径:{}", to);
			// 移动文件，删除源文件
			SmbFile oldFile = smbFileVO.getFile();
			if (oldFile.exists() && oldFile.isFile()) {
				// pdf上传到云端下载
				Integer type = parseType(from, attr);
				String message = "";
				if (type.equals(StatementEnum.DAY.getCode())) {
					message = CustomerStatementAccountFileEnum.HLD_BOND_DAILY_STATEMENT_ACCOUNT_TEMPLATE.getMessage();
				} else if (type.equals(StatementEnum.MONTH.getCode())) {
					message = CustomerStatementAccountFileEnum.HLD_BOND_MONTH_STATEMENT_ACCOUNT_TEMPLATE.getMessage();
				}
				String smbNewname = fundAccount + "-" + ymd + "-" + message + ".pdf";
				R<ZeroFile> uploadRes = ossClient.uploadMinioFile(multipartFile, smbNewname);
				if (!uploadRes.isSuccess()) {
					log.info("活利得、债券易日结单pdf上传失败 ,{}", uploadRes.getMsg());
					return true;
				}
				link = uploadRes.getData().getLink();
				fileName = uploadRes.getData().getOriginalName();
			}
			// 入库
			saveStatementDb(date, fundAccount, from, link, fileName, attr);
			oldFile.delete();
		} catch (IOException e) {
			log.error("结单处理失败:", e);
			return false;
		}
		return true;
	}

	public String makeOutputPath(String tenantId) {
		return accountPdfProperties.getStockPath() + "/" + tenantId + "/" + System.currentTimeMillis() + "/";
	}

	private void saveStatementDb(DateTime dateTime, String fundAccount, String from, String link, String fileName, String attr) {
		// 移除文件路径的目录前缀
		if (StringUtils.startsWith(from, publicDir)) {
			from = from.substring(publicDir.length());
		}
		Date date = dateTime.toDate();
		Integer type = parseType(from, attr);
		// 拼装数据Bean
		CustomerFile customerFile = new CustomerFile();
		// 股票存“资金账号”
		customerFile.setCustId(Long.valueOf(fundAccount));
		customerFile.setDate(DateUtil.beginOfDay(date));
		customerFile.setFileName(fileName);
		customerFile.setUrl(link);
		customerFile.setType(type);
		customerFile.setStatementType(StatementEnum.StatementType.STOCK.getType());

		LambdaQueryWrapper<CustomerFile> queryWrapper3 = new LambdaQueryWrapper<>();
		queryWrapper3.eq(CustomerFile::getCustId, customerFile.getCustId());
		queryWrapper3.eq(CustomerFile::getDate, customerFile.getDate());
		queryWrapper3.eq(CustomerFile::getType, customerFile.getType());
		queryWrapper3.eq(CustomerFile::getStatementType, customerFile.getStatementType());
		CustomerFile customerFile1 = customerFileMapper.selectOne(queryWrapper3);
		if (customerFile1 != null) {
			customerFile.setId(customerFile1.getId());
			customerFile.setUpdateTime(new Date());
			customerFileMapper.updateById(customerFile);
		} else {
			customerFile.setCreateTime(new Date());
			customerFile.setUpdateTime(new Date());
			customerFile.setStatus(StatementEnum.FileSendStatus.UN_SET.getCode());
			customerFileMapper.insert(customerFile);
		}
	}

	/**
	 * 解析结单类型
	 *
	 * @param from
	 * @param attr
	 * @return
	 */
	private Integer parseType(String from, String attr) {
		int type = MktConstants.StatementType.DAILY;
		if (attr == null) {
			log.error("attr is null, from: {}", from);
			return type;
		}
		if (attr.startsWith("D")) {
			type = MktConstants.StatementType.DAILY;
		} else if (attr.startsWith("M")) {
			type = MktConstants.StatementType.MONTHLY;
		}
		return type;
	}

	/**
	 * 解析日期部分
	 * 日结单的日期是yyyyMMdd
	 * 月结单的日期是yyyyMM
	 *
	 * @param ymd
	 * @return
	 */
	private DateTime parseDate(String ymd) {
		String y = ymd.substring(0, 4);
		String m = ymd.substring(4, 6);
		String d = null;
		if (ymd.length() >= 8) {
			d = ymd.substring(6, 8);
		}
		int iy = Integer.parseInt(y);
		int im = Integer.parseInt(m);
		DateTime dt = DateTime.now();
		dt = dt.withTime(0, 0, 0, 0);
		dt = dt.withYear(iy).withMonthOfYear(im);
		if (d == null) {
			// 取当月最后一天
			dt = dt.dayOfMonth().withMaximumValue();
		} else {
			int id = Integer.parseInt(d);
			dt = dt.withDayOfMonth(id);
		}
		return dt;
	}

	/**
	 * 构建目标文件名，含目标路径
	 *
	 * @param date
	 * @return
	 * @throws IOException
	 */
	private String buildDestinationFilename(DateTime date) throws IOException {
		String year = "" + date.getYear();
		int m = date.getMonthOfYear();
		String month = m < 10 ? ("0" + m) : ("" + m);
		String path = DEST_DIR + "/" + year + "/" + month;

		String smb = "smb://" + smbUser + ":" + smbPwd + "@" + smbHost + "/" + publicDir;
		String to = smb + path + "/";


		return to;
	}
}
