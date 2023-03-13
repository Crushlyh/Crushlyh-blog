package com.lyh.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/13 21:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "更改角色dto")
public class ChangeRoleDTO {
    //角色ID
    private Long id;
    //角色状态（0正常 1停用）
    private String status;
}
