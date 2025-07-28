package com.minigod.zero.biz.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author chen
 * @ClassName CompanyProfileInfoResp.java
 * @Description TODO
 * @createTime 2022年08月09日 11:43:00
 */
@Data
public class CompanyProfileInfoResp implements Serializable {

    private static final long serialVersionUID = 1549821824933981519L;

    private CompanyProfile companyProfile;

    private List<CornerstoneInvestor> cornerstoneInvestor;

    private List<Sponsor> sponsor;

    private List<Underwriter> underwriter;

    @Data
    public static class CompanyProfile {

        private String boardName;

        private String companyDirector;

        private String companyProfile;

        private String industryName;

        private String majorShareholders;

        private String symbol;
    }

    @Data
    public static class CornerstoneInvestor {

        private String code;

        private BigDecimal firstDayGoUp;

        private String investor;

        private Integer nearlyYearNum;

        private BigDecimal proportion;

        private String restrictedEndDate;

        private BigDecimal subscribedNum;

    }

    @Data
    private static class Sponsor {

        private String code;

        private Integer down;

        private BigDecimal firstDayGoUp;

        private Integer flat;

        private Integer nearlyYearNum;

        private String sponsor;

        private Integer up;

    }

    @Data
    private static class Underwriter {

        private String code;

        private BigDecimal firstDayGoUp;

        private String listedDate;

        private Integer nearlyYearNum;

        private String underwriter;

    }
}
