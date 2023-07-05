package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        queryWrapper.eq("clazz_id", clazzId);
        /*queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("clazz_id", clazzId);
        List<Post> posts = postMapper.selectList(queryWrapper);*/
        return ResultBean.success("动态加载成功",postMapper.selectList(queryWrapper));
    }

    @Override
    public ResultBean deletePostById(String postId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
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
        post.setPhonenum(postParam.getPhonenum());
        post.setClazz_id(postParam.getClassId());
        post.setTitle(postParam.getTitle());
        post.setDetail(postParam.getPostContent());
        post.setImage(postParam.getImagePath());
        post.setVideo(postParam.getVideoPath());
        post.setPostTime(day);
        post.setLikes(postParam.getLikes());

        this.save(post);

        return ResultBean.success("动态发发布成功!",post);
    }

    @Override
    public ResultBean updatePostInfoById(PostParam postParam) {

        Post post=postMapper.selectById(postParam.getPost_id());

        this.update(Wrappers.lambdaUpdate(post).set(Post::getDetail,postParam.getPostContent())
                .eq(Post::getPost_id,postParam.getPost_id()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getTitle,postParam.getTitle())
                .eq(Post::getPost_id,postParam.getPost_id()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getImage,postParam.getImagePath())
                .eq(Post::getPost_id,postParam.getPost_id()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getVideo,postParam.getVideoPath())
                .eq(Post::getPost_id,postParam.getPost_id()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getLikes,postParam.getLikes())
                .eq(Post::getPost_id,postParam.getPost_id()));

        return ResultBean.success("动态更新成功",post);
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
