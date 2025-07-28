package com.minigod.minigodinformation.dto;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.disclosure.dto.AnnouncementProductDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/10/21 11:16
 * @Version: 1.0
 */
@Data
public class AnnouncementProductDTO {
    /**
     * 产品id
     */
    private String productId;

    /**
     * 产品名字
     */
    private String productName;

    /**
     * 产品isin
     */
    private String productIsin;
}
