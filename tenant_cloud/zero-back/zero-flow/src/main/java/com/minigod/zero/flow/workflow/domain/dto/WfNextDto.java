package com.minigod.zero.flow.workflow.domain.dto;

import com.minigod.zero.user.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 动态人员、组
 * @author zsdp
 * @createTime 2022/3/10 00:12
 */
@Data
public class WfNextDto implements Serializable {

    private String type;

    private String vars;

    private List<User> userList;

}
