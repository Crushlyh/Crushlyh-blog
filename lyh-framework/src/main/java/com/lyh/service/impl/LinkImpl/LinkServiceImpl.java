package com.lyh.service.impl.LinkImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Link;
import com.lyh.domain.vo.LinkVO;
import com.lyh.domain.vo.PageVO;
import com.lyh.mapper.LinkMapper;
import com.lyh.service.LinkService;
import com.lyh.utils.BeanCopyUtils;
import com.sun.javafx.logging.PulseLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-02-22 22:47:00
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Autowired
    private LinkService linkService;
    @Override
    public ResponseResult getAlllinks(){
        LambdaQueryWrapper<Link> linkWrapper= new LambdaQueryWrapper<>();
        linkWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(linkWrapper);
        //转换成VO
        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(links, LinkVO.class);
        //返回ResponseResult

        return ResponseResult.okResult(linkVOS);
    }

    @Override
    public ResponseResult listAllLinks(Integer pageNum, Integer pageSize, Link link) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(link.getName()),Link::getName,link.getName());
        wrapper.eq(StringUtils.hasText(link.getStatus()),Link::getStatus, link.getStatus());
        Page<Link> page = new Page<>();
        page=page(page,wrapper);
        page.setSize(pageNum);
        page.setCurrent(pageSize);

        List<Link> links = page.getRecords();
        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(links, LinkVO.class);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(page.getTotal());
        pageVO.setRows(linkVOS);
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkInfo(Long id) {
        Link link = getById(id);
        return ResponseResult.okResult(link);
    }

    @Override
    public ResponseResult updateLink(Link link) {
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }
}

