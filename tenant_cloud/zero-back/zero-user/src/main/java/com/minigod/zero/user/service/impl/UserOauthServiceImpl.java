
package com.minigod.zero.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.user.mapper.UserOauthMapper;
import com.minigod.zero.user.entity.UserOauth;
import lombok.AllArgsConstructor;
import com.minigod.zero.user.service.IUserOauthService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

}
