package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/2/23 19:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVO {
    private Long id;

    private String name;

    private String logo;

    //网站地址
    private String address;
}
