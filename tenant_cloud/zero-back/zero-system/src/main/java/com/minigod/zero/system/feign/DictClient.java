
package com.minigod.zero.system.feign;


import com.minigod.zero.system.entity.Dict;
import com.minigod.zero.system.service.IDictService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 * @author Chill
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

    private final IDictService service;

    @Override
    @GetMapping(GET_BY_ID)
    public R<Dict> getById(Long id) {
        return R.data(service.getById(id));
    }

    @Override
    @GetMapping(GET_VALUE)
    public R<String> getValue(String code, String dictKey) {
        return R.data(service.getValue(code, dictKey));
    }

    @Override
    @GetMapping(GET_LABEL)
    public R<String> getLabel(String code, String dictLabel) {
        return R.data(service.getLabel(code, dictLabel));
    }

    @Override
    @GetMapping(GET_LIST)
    public R<List<Dict>> getList(String code) {
        return R.data(service.getList(code));
    }

}
