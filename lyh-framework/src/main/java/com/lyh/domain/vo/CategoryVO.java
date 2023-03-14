package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/2/21 21:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {
    private Long id;
    private String name;
    //描述
    private String description;
    private String status;
}
