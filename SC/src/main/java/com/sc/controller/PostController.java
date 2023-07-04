package com.sc.controller;

import com.sc.entity.Post;
import com.sc.service.PostService;
import com.sc.service.UserService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "PostController")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @ApiOperation(value = "保存用户的动态")
    @PutMapping("/post/save")
    public ResultBean userPost(@RequestBody PostParam postParam){

        return postService.saveUserPost(postParam);
    }

    @ApiOperation(value = "获取动态信息")
    @GetMapping("/post/get")
    public Post getPostInfoById(@RequestBody String id){
        return postService.getPostById(id);
    }

    @ApiOperation(value = "获取主页动态信息")
    @GetMapping("/home")
    public List<Post> getPostInfoByClassId(@RequestBody String clazzId){
        return postService.getPostInfoByClazzId(clazzId);
    }

    @ApiOperation("value=删除动态")
    @DeleteMapping("/post/delete")
    public ResultBean deletePostById(@RequestBody String postId){
        return postService.deletePostById(postId);
    }

}
