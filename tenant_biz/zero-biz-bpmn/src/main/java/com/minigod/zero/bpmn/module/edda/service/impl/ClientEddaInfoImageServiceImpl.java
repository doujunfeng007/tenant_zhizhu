package com.minigod.zero.bpmn.module.edda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoImageEntity;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaInfoImageMapper;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【client_edda_info_image(客户edda申请图片信息表)】的数据库操作Service实现
* @createDate 2024-05-13 10:36:33
*/
@Slf4j
@Service
@AllArgsConstructor
public class ClientEddaInfoImageServiceImpl extends ServiceImpl<ClientEddaInfoImageMapper, ClientEddaInfoImageEntity>
    implements ClientEddaInfoImageService{

}




