package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.dto.PhillipProtocolSaveDTO;
import com.minigod.zero.bpm.entity.PhillipProtocolLog;
import com.minigod.zero.bpm.vo.PhillipProtocolVo;

/**
 * <p>
 * 暗盘协议记录表 服务类
 * </p>
 *
 * @author zxw
 * @since 2023-10-07
 */
public interface PhillipProtocolLogService extends IService<PhillipProtocolLog> {

    void savePhProtocol(PhillipProtocolSaveDTO saveDTO);

    PhillipProtocolVo findPhProtocolLog();

}
