package com.lyh.service.impl.LinkImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Link;
import com.lyh.domain.vo.LinkVO;
import com.lyh.mapper.LinkMapper;
import com.lyh.service.LinkService;
import com.lyh.utils.BeanCopyUtils;
import com.sun.javafx.logging.PulseLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

