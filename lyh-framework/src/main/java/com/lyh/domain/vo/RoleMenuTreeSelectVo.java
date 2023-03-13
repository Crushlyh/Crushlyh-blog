package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/13 22:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeSelectVo {
    private List<Long> checkedKeys;

    private List<MenuTreeVo> menus;
}
