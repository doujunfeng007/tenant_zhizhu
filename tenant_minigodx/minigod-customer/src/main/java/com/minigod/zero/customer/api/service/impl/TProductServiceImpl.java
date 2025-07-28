package com.minigod.zero.customer.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.customer.entity.TProductEntity;
import com.minigod.zero.customer.api.service.TProductService;
import com.minigod.zero.customer.mapper.TProductMapper;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【t_product(产品表)】的数据库操作Service实现
* @createDate 2024-05-23 23:36:48
*/
@Service
public class TProductServiceImpl extends ServiceImpl<TProductMapper, TProductEntity>
    implements TProductService{

}




