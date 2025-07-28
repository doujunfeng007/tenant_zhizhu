package com.minigod.zero.manage.service;

/**
 * @author 掌上智珠
 * @since 2023/9/8 13:45
 */
public interface IAbPositionService {

	//新闻ab岗判定
	boolean newsAbPosition(Integer type,Long lastUpdateUserId);

	//专题ab岗判定
	boolean topicAbPosition(Integer type,Long lastUpdateUserId);

	//敏感词ab岗判定
	boolean sensitiveWordAbPosition(Integer type,Long lastUpdateUserId);

	//简繁语法ab岗判定
	boolean simpleAndComplicatedAbPosition(Integer type,Long lastUpdateUserId);

	boolean baseAbPosition(Integer type,Long lastUpdateUserId);

}
