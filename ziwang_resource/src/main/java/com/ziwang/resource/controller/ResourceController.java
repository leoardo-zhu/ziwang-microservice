package com.ziwang.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.ziwang.resource.pojo.Resource;
import com.ziwang.resource.service.ResourceService;


import entity.Result;
import entity.StatusCode;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查询全部数据
     *
     * @return 返回最多下载(most) 最新下载(new)
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", resourceService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", resourceService.findById(id));
    }

    @GetMapping("/hot")
    public Result findHotResources(){
        return new Result(true,StatusCode.OK,"查询成功",resourceService.findByHot());
    }

    /**
     * 增加
     *
     * @param resource
     */
    @PostMapping
    public Result add(@RequestBody Resource resource) {
        resourceService.add(resource);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    @GetMapping("/type")
    public Result getType() {
        return new Result(true, StatusCode.OK, "查询成功", resourceService.getType());
    }

    @GetMapping("/type/{type1Id}/{type2Id}")
    public Result getType(@PathVariable Integer type1Id, @PathVariable Integer type2Id) {
        return new Result(true, StatusCode.OK, "查询成功", resourceService.getTypeById(type1Id, type2Id));
    }

}
