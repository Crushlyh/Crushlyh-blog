package com.lyh.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/8 22:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "标签列表dto")
public class TaglistDTO {
    private String name;
    private String remark;
    private Long id;
}
