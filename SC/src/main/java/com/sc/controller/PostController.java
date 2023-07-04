package com.sc.controller;

import com.sc.entity.Post;
import com.sc.service.PostService;
import com.sc.service.UserService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "PostController")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @ApiOperation(value = "保存用户的动态")
    @PutMapping("/post/save")
    public ResultBean userPost(@RequestBody PostParam postParam){

        return postService.saveUserComment(postParam);
    }

    @ApiOperation(value = "获取动态信息")
    @GetMapping("/post/info")
    public Post getPostInfoById(@RequestBody Integer id){
        return postService.getPostById(id);
    }

}
