package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 平台文档对象 sys_document
 *
 * @author jim
 * @date 2020-05-27
 */
@Data
@TableName("oms_sys_doc")
@ApiModel(value = "SysDocument对象", description = "文档")
@EqualsAndHashCode(callSuper = true)
public class SysDocumentEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

	private String lang;
    /** 标题 */
    private String docTitle;

    /** 索引（与前端对应） */
    private String docKey;

    /** 内容 */
    private String docContent;

    /** 状态（0正常 1关闭） */
    //private String status;
}
