package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Post;
import com.sc.entity.User;
import com.sc.mapper.PostMapper;
import com.sc.service.PostService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private PostMapper postMapper;
    

    @Value("${pic_storage.ip}")
    private String ip;

    @Value("${vid_storage.port}")
    private String port;
    


    @Override
    public ResultBean getPostById(String postId) {
        return ResultBean.success("动态获取成功",postMapper.selectById(postId));
    }

    @Override
    public ResultBean getPostInfoByClazzId(String clazzId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clazzId", clazzId);
        return ResultBean.success("动态加载成功",postMapper.selectList(queryWrapper));
    }

    @Override
    public ResultBean deletePostById(String postId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId);
        postMapper.delete(queryWrapper);
        return ResultBean.success("删除成功");
    }

    @Override
    public ResultBean saveUserPost(PostParam postParam) {
        Post post = Post.createPostFromParam(postParam);
        postMapper.insert(post);
        return ResultBean.success("动态发发布成功!",post);
    }

    

    @Override
    public ResultBean updatePostInfoById(PostParam postParam) {
        // 创建一个UpdateWrapper实例
        UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_id", postParam.getPostId())
                .set("detail", postParam.getPostContent())
                .set("title", postParam.getTitle())
                .set("image", postParam.getImagePath())
                .set("video", postParam.getVideoPath())
                .set("likes", postParam.getLikes());

        // 执行更新操作
        postMapper.update(null, updateWrapper);

        // 重新获取更新后的Post对象
        Post updatedPost = postMapper.selectById(postParam.getPostId());

        // 返回成功响应
        return ResultBean.success("动态更新成功", updatedPost);
    }

    @Override
    public int updateLikes(String postId,int likes) {
        int l=likes;
        UpdateWrapper<Post> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("postId",postId);
        updateWrapper.set("likes",l++);
        return l;
    }

    @Override
    public String saveFile(MultipartFile file,String path,String postId) {
        String pType = getFileType(file);
        String filePath = path + "/"+postId + pType;
        File outFile = new File(filePath);
        if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
            outFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://" + ip + ":" + port + "/" + filePath;
    }


    @Override
    public String updateFile(MultipartFile file, String fullPath,String path, String postId) {
        //用户第一次上传文件,fullPath=null;
        if (!file.isEmpty()&&fullPath==null){
            return saveFile(file,path,postId);
        }
        //用户非第一次上传文件
        else {
            //fullPath="Http://ip:port/path/postId.pType";
            //正则表达式，用于截取“/path/postId.pType”;
            String filePath = fullPath.replaceAll(".*?(/.*)", "$1");
            File originFile = new File(filePath);
            //动态之前包含文件，用户删除文件
            if (originFile.exists()) {
                if (file.isEmpty()) {
                    originFile.delete();
                    return null;
                }
            }
            //用户修改文件
            if (!file.isEmpty()) {
                try {
                    file.transferTo(new File(filePath));
                    return fullPath;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String getFileType(MultipartFile file) {
        String pType = file.getContentType();
        pType = pType.substring(pType.indexOf("/") + 1);
        if ("jpeg".equals(pType)) {
            pType = "jpg";
        }
        return pType;
    }


}
