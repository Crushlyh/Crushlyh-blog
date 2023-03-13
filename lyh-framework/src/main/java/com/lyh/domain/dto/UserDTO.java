package com.lyh.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/13 22:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "查询角色列表dto")
public class UserDTO {
    //用户名
    private String userName;
    //手机号
    private String phonenumber;
    //账号状态（0正常 1停用）
    private String status;

}
