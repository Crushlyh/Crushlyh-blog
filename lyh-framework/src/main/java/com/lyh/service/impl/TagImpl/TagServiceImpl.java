package com.lyh.service.impl.TagImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.Exception.SystemException;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddTagdto;
import com.lyh.domain.dto.TaglistDTO;
import com.lyh.domain.dto.UpdateTagdto;
import com.lyh.domain.entity.LoginUser;
import com.lyh.domain.entity.Tag;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.domain.vo.PageVO;
import com.lyh.domain.vo.TagVo;
import com.lyh.mapper.TagMapper;
import com.lyh.service.TagService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:52:58
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagService tagService;
    @Override
    public ResponseResult getTaglist(Integer pageNum, Integer pageSize, TaglistDTO taglistDTO) {
        //分页查询
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(taglistDTO.getName()),Tag::getName,taglistDTO.getName());
        wrapper.like(StringUtils.hasText(taglistDTO.getRemark()),Tag::getRemark,taglistDTO.getRemark());
        Page<Tag> page=new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, wrapper);
        List<Tag> tags = page.getRecords();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        //封装数据返回
        PageVO pageVo=new PageVO(tagVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(AddTagdto addTagdto) {
        Tag tag=BeanCopyUtils.copyBean(addTagdto,Tag.class);
        if(!StringUtils.hasText(addTagdto.getName())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(UpdateTagdto updateTagdto) {
        Tag tag = BeanCopyUtils.copyBean(updateTagdto, Tag.class);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long id = loginUser.getUser().getId();
        tag.setUpdateBy(id);

        Date date=new Date();
        tag.setUpdateTime(date);
        updateById(tag);
        return ResponseResult.okResult(tag);
    }
    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> tags= list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

