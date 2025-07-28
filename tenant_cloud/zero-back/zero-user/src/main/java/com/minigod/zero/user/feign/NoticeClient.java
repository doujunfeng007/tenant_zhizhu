package com.minigod.zero.user.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.user.entity.Notice;
import com.minigod.zero.user.service.INoticeService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.mp.support.ZeroPage;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tenant.annotation.NonDS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Notice Feign
 *
 * @author Chill
 */
@NonDS
@ApiIgnore()
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

	private final INoticeService service;

	@Override
	@GetMapping(TOP)
	public ZeroPage<Notice> top(Integer current, Integer size) {
		Query query = new Query();
		query.setCurrent(current);
		query.setSize(size);
		IPage<Notice> page = service.page(Condition.getPage(query));
		return ZeroPage.of(page);
	}

}
