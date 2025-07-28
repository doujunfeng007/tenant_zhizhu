
package com.minigod.zero.user.excel;

import com.minigod.zero.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import com.minigod.zero.core.excel.support.ExcelImporter;

import java.util.List;

/**
 * 用户数据导入类
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class UserImporter implements ExcelImporter<UserExcel> {

	private final IUserService service;
	private final Boolean isCovered;

	@Override
	public void save(List<UserExcel> data) {
		service.importUser(data, isCovered);
	}
}
