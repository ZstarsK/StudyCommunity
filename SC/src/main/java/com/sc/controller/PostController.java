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
    @PostMapping("/post/save")
    public ResultBean saveUserPost(@RequestBody PostParam postParam){
        System.out.println("run");
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
        if (clazzId!=null&&!clazzId.isEmpty()) return postService.getPostInfo(clazzId,true);
        String username=getUserByToken.getUserByToken(token).getUsername();
        if (username!=null&&!username.isEmpty()) return postService.getPostInfo(username,false);
        return ResultBean.error("动态加载失败");
    }

    @ApiOperation(value = "更新动态信息")
    @PutMapping("/post/update")
    public ResultBean updatePostInfoById(@RequestBody PostParam postParam){
        MultipartFile imageFile=postParam.getImage();
        MultipartFile videoFile=postParam.getVideo();
        String postId = postParam.getPostId();
        postParam.setImagePath(postService.updateFile(imageFile,postParam.getImagePath(),picPath,postId));
        postParam.setVideoPath(postService.updateFile(videoFile,postParam.getVideoPath(),vidPath,postId));
        return postService.updatePostInfoById(postParam);
    }

    @ApiOperation(value = "更新动态点赞信息")
    @PutMapping("/post/likes/update")
    public int  updatePostLikes(@RequestBody PostParam postParam){
        return postService.updateLikes(postParam.getPostId(),postParam.getLikes());
    }
    @ApiOperation(value="删除动态")
    @DeleteMapping("/post/delete")
    public ResultBean deletePostById(@RequestParam("postId") String postId){
        return postService.deletePostById(postId);
    }

}
