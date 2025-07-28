package com.minigod.zero.manage.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.ImageSize;
import com.minigod.zero.cust.apivo.excel.CustInfoExcel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/file")
public class FileUploadController {

	@Resource
	private FileUtil fileUtil;

	@SneakyThrows
	@PostMapping("/upload")
	public R<ZeroFile> putFile(@RequestParam MultipartFile file) {
		if (null == file) {
			return R.fail("上传失败: 文件不能为空");
		}
		try {
			List<ZeroFile> list = fileUtil.uploadFile(null, file);
			return R.data(null != list && list.size() > 0 ? list.get(0) : null);
		} catch (Exception e) {
			return R.fail("上传失败:" + e.getMessage());
		}
	}

	@SneakyThrows
	@PostMapping("/upload-compress")
	public R<ZeroFile> putFileCompress(@RequestParam MultipartFile file,@RequestParam int width,@RequestParam int height) {
		if (null == file) {
			return R.fail("上传失败: 文件不能为空");
		}
		try {
			List<ImageSize> whs = new ArrayList<>();
			ImageSize is = new ImageSize();
			is.setHeight(width);
			is.setWidth(height);
			whs.add(is);
			List<ZeroFile> list = fileUtil.uploadFile(whs, file);
			return R.data(null != list && list.size() > 0 ? list.get(0) : null);
		} catch (Exception e) {
			return R.fail("上传失败:" + e.getMessage());
		}
	}

	@PostMapping("importCustInfo")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "导入", notes = "传入excel")
	public R<Object> importUser(@RequestParam MultipartFile file) {
		try {
			List<CustInfoExcel> list = ExcelUtil.readWebExcelData(file, CustInfoExcel.class);
			return R.data(list);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}
}
