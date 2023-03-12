package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddTagdto;
import com.lyh.domain.dto.TaglistDTO;
import com.lyh.domain.dto.UpdateTagdto;
import com.lyh.domain.entity.Tag;
import com.lyh.domain.vo.PageVO;
import com.lyh.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/6 20:58
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVO> list(Integer pageNum, Integer pageSize, TaglistDTO taglistDTO){
        return tagService.getTaglist(pageNum,pageSize,taglistDTO);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody AddTagdto addTagdto){

        return tagService.addTag(addTagdto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable List<Long> ids){
        return tagService.deleteTag(ids);
    }
    //先获取信息
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }
    //再修改标签
    @PutMapping
    public ResponseResult updateTag(@RequestBody UpdateTagdto updateTagdto){
        return tagService.updateTag(updateTagdto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
