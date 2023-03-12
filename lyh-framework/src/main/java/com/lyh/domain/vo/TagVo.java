package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:crushlyh
 * Date:2023/3/8 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {
    private String name;
    private String remark;
    private Long id;
}
