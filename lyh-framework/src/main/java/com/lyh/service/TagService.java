package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddTagdto;
import com.lyh.domain.dto.TaglistDTO;
import com.lyh.domain.dto.UpdateTagdto;
import com.lyh.domain.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:52:57
 */

public interface TagService extends IService<Tag> {

    ResponseResult getTaglist(Integer pageNum, Integer pageSize, TaglistDTO taglistDTO);

    ResponseResult addTag(AddTagdto addTagdto);

    ResponseResult deleteTag(List<Long> id);

    ResponseResult updateTag(UpdateTagdto updateTagdto);

    ResponseResult listAllTag();
}

