package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Link;
import com.lyh.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.DescriptorKey;
import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/14 22:59
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult listAllLinks(Integer pageNum, Integer pageSize, Link link){
        return linkService.listAllLinks(pageNum,pageSize,link);
    }
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return linkService.addLink(link);
    }
    @GetMapping("{id}")
    public ResponseResult getLinkInfo(@PathVariable("id")Long id){
        return linkService.getLinkInfo(id);
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.updateLink(link);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") List<Long> ids){
        return linkService.deleteLink(ids);
    }
}
