package com.sc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sc.entity.Post;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService extends IService<Post> {
    Post getPostById(String postId);

    List<Post> getPostInfoByClazzId(String clazzId);

    ResultBean deletePostById(String postId);

    ResultBean saveUserPost(PostParam commentParam);


}