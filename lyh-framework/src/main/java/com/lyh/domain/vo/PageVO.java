package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/2/21 22:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {
    private List rows;
    private Long total;
}
