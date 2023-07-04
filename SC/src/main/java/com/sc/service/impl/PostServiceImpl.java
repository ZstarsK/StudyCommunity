package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Post;
import com.sc.entity.User;
import com.sc.mapper.PostMapper;
import com.sc.service.PostService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    QueryWrapper<Post> queryWrapper;


    @Override
    public Post getPostById(String postId) {
        return postMapper.selectById(postId);
    }

    @Override
    public ResultBean deletePostById(String postId) {

        queryWrapper.eq("post_id", postId);
        postMapper.delete(queryWrapper);
        return ResultBean.success("删除成功");
    }

    @Override
    public ResultBean saveUserPost(PostParam postParam) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day = format.format(date);

        Post post=new Post();
        post.setPost_id(postParam.getPost_id());
        post.setPhonenum(postParam.getPhoneNum());
        post.setClazz_id(postParam.getClassId());
        post.setTitle(postParam.getTitle());
        post.setDetail(postParam.getPostContent());
        post.setImage(postParam.getImagePath());
        post.setVideo(postParam.getVideoPath());
        post.setPostTime(day);
        post.setLikes(postParam.getLikes());

        this.save(post);

        return ResultBean.success("评论成功！");
    }


}
