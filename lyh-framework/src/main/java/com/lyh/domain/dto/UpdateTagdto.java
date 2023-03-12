package com.lyh.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/9 22:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "修改标签dto")
public class UpdateTagdto {
    //备注
    private String remark;
    //标签名
    private String name;
    //id
    private Long id;
}
