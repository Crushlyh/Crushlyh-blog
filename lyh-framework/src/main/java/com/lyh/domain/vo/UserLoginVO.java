package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/2/23 20:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {
    private String token;
    private UserInfoVO userInfo;
}
