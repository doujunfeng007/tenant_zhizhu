package com.minigod.zero.biz.common.enums;

public enum EAdInfoFlag {
	ADISCLOSE(1L << 0), // 广告关闭判断
	DESIGNATE(1L << 1), // 是否指定用户可看判断
	ADDADVISER(1L << 2), // 好友是投顾个数大于3判断
	NEWUSER(1L << 3), // 新用户判断
	ADVISERCANSEE(1L << 4), // 认证投顾可看
	HAVE_JOIN(1L << 5), // 参与活动的用户可看
	NEED_LOGIN(1L << 6), // 是否需要登录
	APP_VERSION(1L << 7), // 版本控制
	USER_GUEST(1L << 8), // 游客可看
	USER_UNOPEN(1L << 9), // 未开户可看
	USER_UNGOLD(1L << 10), // 未入金且开户可看
	USER_GOLD(1L << 11), // 已入金且开户可看
	USER_OPEN(1L << 12), // 已开户可看
    USER_TRADE(1L << 14); // 交易用户可看
//	USER_ALL(1l << 13); // 所有用户可看
	//却  未开户 /未入金 /未交易 /已交易
	// 属性的顺序，从0开始
	private long index;

	// 构造函数
	private EAdInfoFlag(long index) {
		this.index = index;
	}

	// 判断当前属性字段位与后是否为真
	public boolean isFlag(Long flag) {
		return (flag & this.getIndex()) != 0;
	}

	// 获取当前枚举值的顺序
	public long getIndex() {
		return index;
	}


	public static void main(String[] args) {
		System.out.println(1L << 0);
		System.out.println(1L << 1);
		System.out.println(1L << 2);
		System.out.println(1L << 3);
		System.out.println(1L << 4);
		System.out.println(1L << 5);
		System.out.println(1L << 6);
		System.out.println(1L << 7);
		System.out.println(1L << 8);
		System.out.println(1L << 9);
		System.out.println(1L << 10);
		System.out.println(1L << 11);
		System.out.println(1L << 12);
		System.out.println(1L << 13);
        System.out.println(1L << 14);
//		System.out.println(EAdInfoFlag.USER_UNGOLD.isFlag(512l));

        System.out.println();
    }

}
