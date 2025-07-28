package com.minigod.zero.dbs.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: DbsHeader
 * @Description:
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Data
public class DbsHeader {
    /**
     * Unique Message ID
     */
    @JsonProperty("msgId")
    private String msgId;

    /**
     * Organisation ID provided by DBS
     */
    @JsonProperty("orgId")
    private String orgId;

    /**
     * DBS Country/Region where the account is held
     */
    @JsonProperty("ctry")
    private String ctry;

    /**
     * Date and time when incoming credit transaction is received.
     * Format: YYYY-MM-DDTHH:MM:SS.sss.
     * E.g. 2017-03-04T15:07:26.123
     */
    @JsonProperty("timeStamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date timeStamp;
}
