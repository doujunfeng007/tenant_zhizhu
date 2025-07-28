/**
  * Copyright 2024 bejson.com
  */
package com.minigod.minigodinformation.dto.http;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2024-11-07 15:12:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class ExchangeRData<T> {

    private List<T> records;
    private int total;
    private int size;
    private int current;
    private List<String> orders;
    private boolean optimizeCountSql;
    private boolean searchCount;
    private String countId;
    private int maxLimit;
    private int pages;


}
