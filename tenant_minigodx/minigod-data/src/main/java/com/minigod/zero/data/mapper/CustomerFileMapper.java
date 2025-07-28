package com.minigod.zero.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.data.entity.CustomerFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author dell
* @description 针对表【customer_file】的数据库操作Mapper
* @createDate 2024-05-31 14:57:06
* @Entity com.minigod.zero.customer.entity.CustomerFile
*/
@DS("slave")
@Mapper
@Component
public interface CustomerFileMapper extends BaseMapper<CustomerFile> {

}




