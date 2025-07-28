package com.minigod.zero.trade.service.options;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.trade.entity.options.OptionsOrderInfo;
import com.minigod.zero.trade.mapper.options.OptionsOrderInfoMapper;

/**
 * <p>
 * 期权订单信息 服务实现类
 * </p>
 *
 * @author chen
 * @since 2024-08-27 18:58:40
 */
@Service
public class OptionsOrderInfoServiceImpl extends ServiceImpl<OptionsOrderInfoMapper, OptionsOrderInfo> implements IOptionsOrderInfoService {

}
