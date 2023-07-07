package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Comment;
import com.sc.entity.Post;
import com.sc.mapper.CommentMapper;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sc.Util.DataUtil.*;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentServiceImpl commentService;

    @Value("${pic_storage.ip}")
    private String ip;

    @Value("${vid_storage.port}")
    private String port;


    @Override
    public ResultBean getPostById(String postId) {
        return ResultBean.success("动态获取成功",postMapper.selectById(postId));
    }

    @Override
    public ResultBean getPostInfo(String queryField,boolean isHome) {
        if (isHome) return ResultBean.success("动态加载成功",postMapper.selectList(
                getQueried(Post.class,"clazzId",queryField)));
        else return ResultBean.success("动态加载成功",postMapper.selectList(
                getQueried(Post.class,"username",queryField)));
    }

    @Override
    public ResultBean deletePostById(String postId) {
        //删除服务器中的文件
        deleteFile(postMapper.selectById(postId).getVideo());
        deleteFile(postMapper.selectById(postId).getVideo());

        postMapper.delete(getQueried(Post.class,"postId",postId));
        //删除动态对应的评论和回复
        List<Comment> list= commentMapper.selectList(
                getQueried(Comment.class,"postId",postId));
        for (Comment comment:list) commentService.deleteCommentByCommentId(comment.getCommentId());




        return ResultBean.success("删除成功");
    }

    @Override
    public ResultBean saveUserPost(PostParam postParam) {
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String day = format.format(date);

        Post post=new Post();
        post.setPostId(postParam.getPostId());
        post.setUsername(postParam.getUsername());
        post.setClazzId(postParam.getClassId());
        post.setTitle(postParam.getTitle());
        post.setDetail(postParam.getPostContent());
        post.setImage(postParam.getImagePath());
        post.setVideo(postParam.getVideoPath());
        post.setPostTime(postParam.getPostTime());
        post.setLikes(postParam.getLikes());

        this.save(post);

        return ResultBean.success("动态发发布成功!",post);
    }

    @Override
    public ResultBean updatePostInfoById(PostParam postParam) {

        Post post=postMapper.selectById(postParam.getPostId());

        if (postParam.getImagePath()==null)deleteFile(post.getImage());
        if (postParam.getVideoPath()==null)deleteFile(post.getVideo());

        this.update(Wrappers.lambdaUpdate(post).set(Post::getDetail,postParam.getPostContent())
                .eq(Post::getPostId,postParam.getPostId()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getTitle,postParam.getTitle())
                .eq(Post::getPostId,postParam.getPostId()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getImage,postParam.getImagePath())
                .eq(Post::getPostId,postParam.getPostId()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getVideo,postParam.getVideoPath())
                .eq(Post::getPostId,postParam.getPostId()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getPostTime,postParam.getPostTime())
                .eq(Post::getPostId,postParam.getPostId()));
        this.update(Wrappers.lambdaUpdate(post).set(Post::getLikes,postParam.getLikes())
                .eq(Post::getPostId,postParam.getPostId()));

        return ResultBean.success("动态更新成功",post);
    }

    @Override
    public int updateLikes(String postId,int likes) {
        int l=likes;
        UpdateWrapper<Post> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("postId",postId);
        updateWrapper.set("likes",++l);
        postMapper.update(null,updateWrapper);
        return l;
    }

    @Override
    public void deleteFile(String fullPath) {
        String filePath = fullPath.replaceAll(".*?(/.*)", "$1");
        File originFile = new File(filePath);
        originFile.delete();
    }

    //@Override
    //public String saveFile(MultipartFile file,String path,String postId) {
    //    String pType = getFileType(file);
    //    String filePath = path + "/"+postId + pType;
    //    File outFile = new File(filePath);
    //    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
    //        outFile.getParentFile().mkdirs();
    //    }
    //    try {
    //        file.transferTo(new File(filePath));
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    return "http://" + ip + ":" + port + "/" + filePath;
    //}


    //@Override
    //public String updateFile(String fullPath,String path, String postId) {
    //    //用户第一次上传文件,fullPath=null;
//
    //    //用户非第一次上传文件
    //     {
    //        //fullPath="Http://ip:port/path/postId.pType";
    //        //正则表达式，用于截取“/path/postId.pType”;
    //        String filePath = fullPath.replaceAll(".*?(/.*)", "$1");
    //        File originFile = new File(filePath);
    //        //动态之前包含文件，用户删除文件
    //        if (originFile.exists()) {
    //            if (file.isEmpty()) {
    //                originFile.delete();
    //                return null;
    //            }
    //        }
    //        //用户修改文件
    //        if (!file.isEmpty()) {
    //            try {
    //                file.transferTo(new File(filePath));
    //                return fullPath;
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    //    return null;
    //}

    //@Override
    //public String getFileType(MultipartFile file) {
    //    String pType = file.getContentType();
    //    pType = pType.substring(pType.indexOf("/") + 1);
    //    if ("jpeg".equals(pType)) {
    //        pType = "jpg";
    //    }
    //    return pType;
    //}


}
