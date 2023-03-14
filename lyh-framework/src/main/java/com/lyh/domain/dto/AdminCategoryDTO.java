package com.lyh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/14 22:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCategoryDTO {
    //分类名
    private String name;
    //状态0:正常,1禁用
    private String status;
}
