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
import java.io.IOException;
import static com.sc.Util.DataUtil.*;

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

    @ApiOperation(value = "保存用户的动态（除文件）")
    @PostMapping("/post/save")
    public ResultBean saveUserPost(@RequestBody PostParam postParam) throws IOException {
        if (postParam.getPostId()==null||postParam.getPostId().isEmpty()) {
            //用户未上传图片，数据库直接插入
            return  ResultBean.success("动态保存成功",postService.saveUserPost(postParam));
        }else {
            //用户上传过图片，通过postId更新数据库对应行
            return ResultBean.success("动态保存成功",postService.updatePostInfoById(postParam));

        }
    }

    @ApiOperation(value = "保存用户动态里的文件")
    @PostMapping("/post/file/save")
    public ResultBean savePostFile(@RequestParam("postId") String postId,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        //用户上传图片后数据库插入记录并返回postId
        String url=saveFiles(file,picPath,postId);
        String[] urlAndPostId={url,postService.savePostUrl(url)};
        return ResultBean.success("动态更新成功",urlAndPostId);
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
        return ResultBean.error("动态加载失败");
    }

    @ApiOperation(value = "获取某个用户的动态信息")
    @GetMapping("/user/post")
    public ResultBean getPostInfoByUserName(@RequestParam("username") String username){
        if (username!=null&&!username.isEmpty()) return postService.getPostInfo(username,false);
        return ResultBean.error("动态加载失败");
    }

    @ApiOperation(value = "更新动态信息")
    @PutMapping("/post/update")
    public ResultBean updatePostInfo(@RequestBody PostParam postParam) throws IOException {
        return postService.updatePostInfoById(postParam);
    }

    @ApiOperation(value = "更新用户动态里的文件")
    @PutMapping("/post/file/update")
    public ResultBean saveAndUpdatePostFile(@RequestParam("postId") String postId,@RequestParam("url") String url,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        String recUrl=updatePostFile(file,url,picPath,postId);
        postService.updateUrl("image",recUrl,postId);
        return ResultBean.success("动态更新成功",recUrl);
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
