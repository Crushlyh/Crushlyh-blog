package com.lyh.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author:crushlyh
 * Date:2023/3/9 21:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "添加标签dto")
public class AddTagdto {


    //备注
    private String remark;
    //标签名
    private String name;
}
