
package com.minigod.zero.develop.launch;

import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * 启动常量
 *
 * @author Chill
 */
public interface LauncherConstant {

	/**
	 * nacos 配置前缀
	 */
	String NACOS_CONFIG_PREFIX = "zero-cloud";

	/**
	 * nacos 配置文件类型
	 */
	String NACOS_CONFIG_FORMAT = "yaml";

	/**
	 * 服务默认加载的配置
	 *
	 * @return sharedDataIds
	 */
	static String sharedDataId() {
		return NACOS_CONFIG_PREFIX + "." + NACOS_CONFIG_FORMAT;
	}

	/**
	 * 服务默认加载的配置
	 *
	 * @param profile 环境变量
	 * @return sharedDataIds
	 */
	static String sharedDataId(String profile) {
		return NACOS_CONFIG_PREFIX + "-" + profile + "." + NACOS_CONFIG_FORMAT;
	}


	/**
	 * nacos 开发环境地址
	 */
	String NACOS_DEV_ADDR = "192.168.1.248:8848";
	String NACOS_DEV_NAMESPACE = "fc4d23d9-9c8e-4ca7-aebc-0cfb44b0450e";
	/**
	 * nacos STG测试地址
	 */
	String NACOS_STG_ADDR = "192.168.1.248:8848";
	String NACOS_STG_NAMESPACE = "763d1d2b-42aa-448d-9c61-b46bbd1244cb";
	/**
	 * nacos UAT测试地址
	 */
	String NACOS_UAT_ADDR = System.getProperty("nacos.tenant.addr");
	String NACOS_UAT_NAMESPACE = System.getProperty("nacos.tenant.namespace");
	/**
	 * nacos 生产地址
	 */
	String NACOS_PRD_ADDR = "10.10.3.15:8848";
	String NACOS_PRD_NAMESPACE = "f89b8bfc-2433-4d75-b4da-0927a13fd285";


	/**
	 * sentinel 开发环境地址
	 */
	String SENTINEL_DEV_ADDR = "127.0.0.1:8858";

	/**
	 * sentinel 生产地址
	 */
	String SENTINEL_PRD_ADDR = "172.30.0.58:8858";

	/**
	 * sentinel STG测试地址
	 */
	String SENTINEL_SIT_ADDR = "172.30.0.58:8858";

	/**
	 * seata 开发环境地址
	 */
	String SEATA_DEV_ADDR = "127.0.0.1:8091";

	/**
	 * seata 生产地址
	 */
	String SEATA_PRD_ADDR = "172.30.0.68:8091";

	/**
	 * seata STG测试地址
	 */
	String SEATA_SIT_ADDR = "172.30.0.68:8091";

	/**
	 * zipkin 开发环境地址
	 */
	String ZIPKIN_DEV_ADDR = "http://127.0.0.1:9411";

	/**
	 * zipkin 生产地址
	 */
	String ZIPKIN_PRD_ADDR = "http://172.30.0.71:9411";

	/**
	 * zipkin STG测试地址
	 */
	String ZIPKIN_SIT_ADDR = "http://172.30.0.71:9411";

	/**
	 * elk 开发环境地址
	 */
	String ELK_DEV_ADDR = "127.0.0.1:9000";

	/**
	 * elk 生产地址
	 */
	String ELK_PRD_ADDR = "172.30.0.72:9000";

	/**
	 * elk STG测试地址
	 */
	String ELK_SIT_ADDR = "172.30.0.72:9000";

	/**
	 * seata file模式
	 */
	String FILE_MODE = "file";

	/**
	 * seata nacos模式
	 */
	String NACOS_MODE = "nacos";

	/**
	 * seata default模式
	 */
	String DEFAULT_MODE = "default";

	/**
	 * seata group后缀
	 */
	String GROUP_NAME = "-group";

	/**
	 * seata 服务组格式
	 *
	 * @param appName 服务名
	 * @return group
	 */
	static String seataServiceGroup(String appName) {
		return appName.concat(GROUP_NAME);
	}

	/**
	 * 动态获取nacos地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String nacosAddr(String profile) {
		switch (profile) {
			case (AppConstant.PRD_CODE):
				return NACOS_PRD_ADDR;
			case (AppConstant.STG_CODE):
				return NACOS_STG_ADDR;
			case (AppConstant.UAT_CODE):
				return NACOS_UAT_ADDR;
			default:
				return NACOS_DEV_ADDR;
		}
	}
	static String nacosNamespace(String profile) {
		switch (profile) {
			case (AppConstant.PRD_CODE):
				return NACOS_PRD_NAMESPACE;
			case (AppConstant.STG_CODE):
				return NACOS_STG_NAMESPACE;
			case (AppConstant.UAT_CODE):
				return NACOS_UAT_NAMESPACE;
			default:
				return NACOS_DEV_NAMESPACE;
		}
	}

	/**
	 * 动态获取sentinel地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String sentinelAddr(String profile) {
		switch (profile) {
			case (AppConstant.PRD_CODE):
				return SENTINEL_PRD_ADDR;
			case (AppConstant.STG_CODE):
				return SENTINEL_SIT_ADDR;
			case (AppConstant.UAT_CODE):
				return SENTINEL_SIT_ADDR;
			default:
				return SENTINEL_DEV_ADDR;
		}
	}

	/**
	 * 动态获取seata地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String seataAddr(String profile) {
		switch (profile) {
			case (AppConstant.PRD_CODE):
				return SEATA_PRD_ADDR;
			case (AppConstant.STG_CODE):
				return SEATA_SIT_ADDR;
			case (AppConstant.UAT_CODE):
				return SEATA_SIT_ADDR;
			default:
				return SEATA_DEV_ADDR;
		}
	}

	/**
	 * 动态获取zipkin地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String zipkinAddr(String profile) {
		switch (profile) {
			case (AppConstant.PRD_CODE):
				return ZIPKIN_PRD_ADDR;
			case (AppConstant.STG_CODE):
				return ZIPKIN_SIT_ADDR;
			case (AppConstant.UAT_CODE):
				return ZIPKIN_SIT_ADDR;
			default:
				return ZIPKIN_DEV_ADDR;
		}
	}

	/**
	 * 动态获取elk地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String elkAddr(String profile) {
		switch (profile) {
			case (AppConstant.PRD_CODE):
				return ELK_PRD_ADDR;
			case (AppConstant.STG_CODE):
				return ELK_SIT_ADDR;
			case (AppConstant.UAT_CODE):
				return ELK_SIT_ADDR;
			default:
				return ELK_DEV_ADDR;
		}
	}

}
