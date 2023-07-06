package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Comment;

import com.sc.entity.Post;
import com.sc.mapper.CommentMapper;
import com.sc.mapper.PostMapper;
import com.sc.service.CommentService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public Comment getCommentByCommentId(String commentId) {
        return commentMapper.selectById(commentId);
    }

    @Override
    public ResultBean getCommentByPostId(String postId) {
        List<Comment> comments=new ArrayList<>();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId);
        String[] commentIds=commentMapper.selectOne(queryWrapper).getCommentId().split(";");
        for (String s:commentIds){
            comments.add(getCommentByCommentId(s));
        }
        return ResultBean.success("评论获取成功",comments);
    }

    @Override
    public ResultBean deleteCommentByCommentId(String commentId,String postId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentId", commentId);
        commentMapper.delete(queryWrapper);

        //将post表中对应commentId删除
        UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("postId", postId);
        String updatedCommentId= postMapper.selectOne(updateWrapper)
                .getCommentId().replaceFirst(";"+commentId,"");
        updateWrapper.set("commentId",updatedCommentId);
        postMapper.update(null,updateWrapper);
        return ResultBean.success("评论删除成功");
    }

    @Override
    public ResultBean saveUserComment(CommentParam commentParam) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day = format.format(date);

        Comment comment=new Comment();
        comment.setCommentId(commentParam.getCommentId());
        comment.setPostId(comment.getPostId());
        comment.setUsername(commentParam.getUsername());
        comment.setDetail(commentParam.getDetail());
        comment.setPostTime(day);
        this.save(comment);

        //将commentId保存到post库中
        String postId=commentParam.getPostId();
        UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("postId", postId);

        StringBuilder commentId=new StringBuilder(postMapper
                .selectById(postId).getCommentId());
        commentId.append(";").append(commentParam.getCommentId());

        updateWrapper.set("commentId",commentId);
        postMapper.update(null,updateWrapper);

        return ResultBean.success("评论发发布成功!",comment);
    }

    @Override
    public ResultBean updateCommentInfo(CommentParam commentParam) {
        String commentId=commentParam.getCommentId();
        UpdateWrapper<Comment> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("commentId",commentId);

        updateWrapper.set("detail",commentParam.getDetail());
        commentMapper.update(null,updateWrapper);

        return ResultBean.success("评论修改成功",getCommentByCommentId(commentId));
    }
}
