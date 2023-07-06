package com.sc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sc.entity.Post;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService extends IService<Post> {
    ResultBean getPostById(String postId);

    ResultBean getPostInfoByClazzId(String clazzId);

    ResultBean deletePostById(String postId);

    ResultBean saveUserPost(PostParam postParam);

    ResultBean updatePostInfoById(PostParam postParam);

    int updateLikes(String postId,int likes);

    String saveFile(MultipartFile file,String path,String postId);

    String updateFile(MultipartFile file,String fullPath,String path,String postId);

    String getFileType(MultipartFile file);





}
