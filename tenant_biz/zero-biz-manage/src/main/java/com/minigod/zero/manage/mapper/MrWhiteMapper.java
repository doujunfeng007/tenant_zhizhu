package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.MrWhiteEntity;
import com.minigod.zero.manage.vo.MrWhiteVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MR白名单 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface MrWhiteMapper extends BaseMapper<MrWhiteEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mrWhite
	 * @return
	 */
	List<MrWhiteVO> selectMrWhitePage(IPage page, @Param("mrWhite") MrWhiteVO mrWhite);


}
