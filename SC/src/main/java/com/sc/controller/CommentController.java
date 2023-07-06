package com.sc.controller;

import com.sc.entity.Comment;
import com.sc.service.CommentService;
import com.sc.service.impl.CommentServiceImpl;
import com.sc.vo.ResultBean;
import com.sc.vo.param.CommentParam;
import com.sc.vo.param.PostParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "CommentController")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "获取动态的评论")
    @GetMapping("/comment/get")
    public ResultBean getUserComment(@RequestParam("postId") String postId){
        return commentService.getCommentByPostId(postId);
    }
    @ApiOperation(value = "保存用户的评论")
    @PutMapping("/comment/save")
    public ResultBean saveUserComment(@RequestBody CommentParam commentParam){
        return commentService.saveUserComment(commentParam);
    }

    @ApiOperation(value = "删除用户的评论")
    @DeleteMapping("/comment/delete")
    public ResultBean deleteUserComment(@RequestParam("commentId") String commentId,
                                        @RequestParam("postId")String postId){
        return commentService.deleteCommentByCommentId(commentId,postId);
    }

    @ApiOperation(value = "更新用户的评论")
    @PutMapping("/comment/update")
    public ResultBean updateUserComment(@RequestBody CommentParam commentParam){
        return commentService.updateCommentInfo(commentParam);
    }



}
