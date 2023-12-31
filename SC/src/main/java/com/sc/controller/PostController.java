package com.sc.controller;

import com.sc.Util.GetUserByToken;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Api(tags = "PostController")
@RestController
public class PostController {

    @Value("${pic_storage.path}")
    private String picPath;

    @Value("${vid_storage.path}")
    private String vidPath;

    @Autowired
    private PostService postService;

    @Autowired
    private GetUserByToken getUserByToken;

    @ApiOperation(value = "保存用户的动态")
    @PutMapping("/post/save")
    public ResultBean saveUserPost(@RequestBody PostParam postParam){
        MultipartFile imageFile=postParam.getImage();
        MultipartFile videoFile=postParam.getVideo();
        String postId = postParam.getPostId();
        if (!imageFile.isEmpty()) postParam.setImagePath(postService.saveFile(imageFile,picPath,postId));
        if (!videoFile.isEmpty()) postParam.setVideoPath(postService.saveFile(videoFile,vidPath,postId));
        return postService.saveUserPost(postParam);
    }

    @ApiOperation(value = "获取动态信息")
    @GetMapping("/post/get")
    public ResultBean getPostInfoById(@RequestParam("postId") String postId){
        return postService.getPostById(postId);
    }

    @ApiOperation(value = "获取主页动态信息")
    @GetMapping("/home")
    public ResultBean getPostInfoByClassId(@RequestParam("token") String token){
        String clazzId = getUserByToken.getUserByToken(token).getClazzId();
        return postService.getPostInfoByClazzId(clazzId);
    }

    @ApiOperation(value = "更新动态信息")
    @PostMapping("/post/update")
    public ResultBean updatePostInfoById(@RequestBody PostParam postParam){

        MultipartFile imageFile=postParam.getImage();
        MultipartFile videoFile=postParam.getVideo();
        String postId = postParam.getPostId();
        postParam.setImagePath(postService.updateFile(imageFile,postParam.getImagePath(),picPath,postId));
        postParam.setVideoPath(postService.updateFile(videoFile,postParam.getVideoPath(),vidPath,postId));
        return postService.updatePostInfoById(postParam);
    }

    @ApiOperation(value = "更新动态信息")
    @PostMapping("/post/likes/update")
    public int  updatePostInfoById(@RequestParam("postId") String postId,
                                         @RequestParam("likes") int likes){
        return postService.updateLikes(postId,likes);
    }
    @ApiOperation(value="删除动态")
    @DeleteMapping("/post/delete")
    public ResultBean deletePostById(@RequestBody String postId){
        return postService.deletePostById(postId);
    }

}
