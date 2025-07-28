package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.TProduct;
import com.minigod.zero.data.vo.ProductCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author dell
 * @description 针对表【t_product(产品表)】的数据库操作Mapper
 * @createDate 2024-10-29 13:48:54
 * @Entity com.minigod.zero.data.entity.TProduct
 */
@Mapper
public interface TProductMapper {

	int deleteByPrimaryKey(Long id);

	int insert(TProduct record);

	int insertSelective(TProduct record);

	TProduct selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TProduct record);

	int updateByPrimaryKey(TProduct record);

	ProductCountVO countProduct();

	/**
	 * 正在IPO阶段的产品数量
	 *
	 * 审核通过且，日期发行日大于当前日期的产品
	 * @return
	 */
	Long ipoCountProduct();

	/**
	 * 统计不同产品类型的上下架数量
	 *
	 * @return
	 */
	List<Map<String, Object>> countProductByTypeAndStatus();

	/**
	  * 活利得利率变更统计
	  *
	  * @param
	  * @return
	  */
	List<String> hldRateChange(@Param("month") Integer month);

	/**
	  * 产品到期提醒
	  *
	  * @param
	  * @return
	  */
	Integer countProductExpired(@Param("month") Integer month);

	/**
	  * 产品到期提醒编码集合
	  *
	  * @param
	  * @return
	  */
	List<String> productExpiredIsin(@Param("month") Integer month);

	/**
	  * 债券易派息提醒数量
	  *
	  * @param
	  * @return
	  */
	Integer countBondDividend(@Param("month") Integer month);

	/**
	  * 债券易派息提醒产品编码集合
	  *
	  * @param
	  * @return
	  */
	List<String> bondDividendIsin(@Param("month") Integer month);
}
