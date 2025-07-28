package com.minigod.zero.bpmn.module.common.controller;

import com.minigod.zero.bpmn.module.common.dto.SysItemConfigDTO;
import com.minigod.zero.bpmn.module.common.dto.SysSubItemConfigDTO;
import com.minigod.zero.bpmn.module.common.service.impl.SysItemConfigService;
import com.minigod.zero.bpmn.module.common.vo.SysItemAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysItemsAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.common.vo.SysSubItemConfigVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.enums.LanguageType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统通用配置
 *
 * @author eric
 * @since 2024-06-20 11:12:05
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/system-config")
@Api(value = "系统通用配置", tags = "系统通用配置接口")
public class SysItemConfigController {

	private final SysItemConfigService sysItemConfigService;

	@ApiOperation("添加Item项")
	@PostMapping("/save/item")
	public R<Void> insertItemSelective(@RequestBody SysItemConfigDTO itemConfig) {
		int i = sysItemConfigService.insertItemSelective(itemConfig);
		if (i > 0) {
			return R.success("添加Item项成功!");
		}
		return R.fail("添加Item项失败!");
	}

	@ApiOperation("添加SubItem项")
	@PostMapping("/save/subitem")
	public R<Void> insertSubItemSelective(@RequestBody SysSubItemConfigDTO subItemConfig) {
		int i = sysItemConfigService.insertSubItemSelective(subItemConfig);
		if (i > 0) {
			return R.success("添加SubItem项成功!");
		}
		return R.fail("添加SubItem项失败!");
	}

	@ApiOperation("删除Item项")
	@DeleteMapping("/remove/item")
	public R<Void> deleteItemByPrimaryKey(@RequestParam("id") Long id) {
		int i = sysItemConfigService.deleteItemByPrimaryKey(id);
		if (i > 0) {
			return R.success("删除Item项成功!");
		}
		return R.fail("删除Item项失败!");
	}

	@ApiOperation("删除SubItem项")
	@DeleteMapping("/remove/subitem")
	public R<Void> deleteSubItemByPrimaryKey(@RequestParam("id") Long id) {
		int i = sysItemConfigService.deleteSubItemByPrimaryKey(id);
		if (i > 0) {
			return R.success("删除SubItem项成功!");
		}
		return R.fail("删除SubItem项失败!");
	}

	@ApiOperation("更新Item项")
	@PutMapping("/update/item")
	public R<Void> updateItemByPrimaryKeySelective(@RequestBody SysItemConfigDTO itemConfig) {
		int i = sysItemConfigService.updateItemByPrimaryKeySelective(itemConfig);
		if (i > 0) {
			return R.success("更新Item项成功!");
		}
		return R.fail("更新Item项失败!");
	}

	@ApiOperation("更新SubItem项")
	@PutMapping("/update/subitem")
	public R<Void> updateSubItemByPrimaryKeySelective(@RequestBody SysSubItemConfigDTO subItemConfig) {
		int i = sysItemConfigService.updateSubItemByPrimaryKeySelective(subItemConfig);
		if (i > 0) {
			return R.success("更新SubItem项成功!");
		}
		return R.fail("更新SubItem项失败!");
	}

	@ApiOperation("查询指定ID的Item项")
	@GetMapping("/select/item")
	public R<SysItemConfigVO> selectItemByPrimaryKey(@RequestParam("id") Long id) {
		SysItemConfigVO itemConfigVO = sysItemConfigService.selectItemByPrimaryKey(id);
		return R.data(itemConfigVO);
	}

	@ApiOperation("查询指定ID的SubItem项")
	@GetMapping("/select/subitem")
	public R<SysSubItemConfigVO> selectSubItemByPrimaryKey(@RequestParam("id") Long id) {
		SysSubItemConfigVO subItemConfigVO = sysItemConfigService.selectSubItemByPrimaryKey(id);
		return R.data(subItemConfigVO);
	}

	@ApiOperation("查询指定item-type的Item项")
	@GetMapping("/select/item/type")
	public R<List<SysItemConfigVO>> selectItemByItemType(@RequestParam("itemType") String itemType, @RequestParam("lang") String lang) {
		List<SysItemConfigVO> itemConfigVOS = sysItemConfigService.selectItemByItemType(itemType, lang);
		return R.data(itemConfigVOS);
	}

	@ApiOperation("查询指定item-type和item-id的SubItem项")
	@GetMapping("/select/subitem/type-and-id")
	public R<List<SysSubItemConfigVO>> selectSubItemByItemTypeAndItemId(@RequestParam("itemType") String itemType, @RequestParam("itemId") Long itemId, @RequestParam("lang") String lang) {
		List<SysSubItemConfigVO> subItemConfigVOS = sysItemConfigService.selectSubItemByItemTypeAndItemId(itemType, itemId, lang);
		return R.data(subItemConfigVOS);
	}

	@ApiOperation("查询指定item-type的SubItem项")
	@GetMapping("/select/subitem/type")
	public R<List<SysSubItemConfigVO>> selectSubItemByItemType(@RequestParam("itemType") String itemType, @RequestParam("lang") String lang) {
		List<SysSubItemConfigVO> subItemConfigVOS = sysItemConfigService.selectSubItemByItemType(itemType, lang);
		return R.data(subItemConfigVOS);
	}

	/**
	 * 根据配置项类型查询配置项和配置子项
	 *
	 * @param itemId
	 * @return
	 */
	@ApiOperation("根据配置项类型查询配置项和配置子项")
	@GetMapping("/select/item-subitems/id")
	public R<SysItemAndSubItemConfigVO> selectItemAndSubItemByItemType(@RequestParam("itemId") Long itemId, @RequestParam("lang") String lang) {
		return R.data(sysItemConfigService.selectItemAndSubItemByItemType(itemId, lang));
	}

	/**
	 * 根据配置项类型查询配置项和配置子项
	 *
	 * @param itemType
	 * @return
	 */
	@ApiOperation("根据配置项类型查询配置项和配置子项")
	@GetMapping("/select/items-subitems/type-lang")
	public R<SysItemsAndSubItemConfigVO> selectItemsAndSubItemByItemType(@RequestParam("itemType") String itemType) {
		return R.data(sysItemConfigService.selectItemsAndSubItemByItemType(itemType, LanguageType.ZH_HANS.getCode()));
	}
}
