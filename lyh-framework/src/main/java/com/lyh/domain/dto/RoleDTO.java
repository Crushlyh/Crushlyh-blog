package com.lyh.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/13 20:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "角色列表dto")
public class RoleDTO {
    //角色ID
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
}
