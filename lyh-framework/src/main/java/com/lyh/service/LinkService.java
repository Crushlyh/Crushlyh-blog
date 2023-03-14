package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Link;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-02-22 22:46:59
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAlllinks();

    ResponseResult listAllLinks(Integer pageNum, Integer pageSize, Link link);

    ResponseResult addLink(Link link);

    ResponseResult getLinkInfo(Long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLink(List<Long> ids);
}

