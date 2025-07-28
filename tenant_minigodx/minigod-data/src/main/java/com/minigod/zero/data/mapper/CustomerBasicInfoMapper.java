package com.minigod.zero.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.minigod.zero.data.entity.CustomerBasicInfoEntity;
import com.minigod.zero.data.vo.CustomerBasicInfoIdKindCountVO;

/**
* @author dell
* @description 针对表【customer_basic_info(客户开户详细资料表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.CustomerBasicInfo
*/
@Mapper
public interface CustomerBasicInfoMapper {
    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(CustomerBasicInfoEntity record);

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    int insertSelective(CustomerBasicInfoEntity record);
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    CustomerBasicInfoEntity selectByPrimaryKey(Long id);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CustomerBasicInfoEntity record);
    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CustomerBasicInfoEntity record);

    /**
     * 统计客户证件类型
     * @return
     */
    List<CustomerBasicInfoIdKindCountVO> statisticsByIdKind();


	/**
	 * 查询开户信息
	 * @param cusId
	 * @return
	 */
	CustomerBasicInfoEntity selectByCustId(Long cusId);

}
