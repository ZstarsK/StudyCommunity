package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Comment;
import com.sc.entity.Post;
import com.sc.entity.User;
import com.sc.mapper.CommentMapper;
import com.sc.mapper.PostMapper;
import com.sc.mapper.UserMapper;
import com.sc.service.PostService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
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

    @Autowired
    private UserMapper userMapper;

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

        List<Post> list;
        if (isHome){
            list = postMapper.selectList(getQueried(Post.class, "clazzId", queryField));
        }
        else {
            list = postMapper.selectList(getQueried(Post.class, "username", queryField));
        }
        Collections.reverse(list);
        return ResultBean.success("动态加载成功",list);
    }

    @Override
    public ResultBean deletePostById(String postId) {
        //删除服务器中的文件
        deleteFile(postMapper.selectById(postId).getImage());
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

//        if (postParam.getImagePath()==null||postParam.getImagePath().isEmpty())deleteFile(post.getImage());
//        if (postParam.getVideoPath()==null||postParam.getVideoPath().isEmpty())deleteFile(post.getVideo());


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
        int l=++likes;
        postMapper.update(null,getUpdated(Post.class,"postId",
                postId,"likes",l));
        return l;
    }

    @Override
    public void deleteFile(String fullPath) {
        int index = fullPath.indexOf("/pic/") + 5;
        String filePath ="/StudyCommunity/"+ fullPath.substring(index);
        File originFile = new File(filePath);
        originFile.delete();
    }

    @Override
    public String updateFile(MultipartFile file, String fullPath, String path, String postId) {
        //用户第一次上传文件,fullPath=null;
        if (!file.isEmpty()&&fullPath.length()>0) {
            saveFiles(file,path,postId);
        }
        //用户非第一次上传文件
         else{
            //fullPath="Http://ip:port/pic/path/postId.pType";
            //正则表达式，用于截取“path/postId.pType”;

            int index = fullPath.indexOf("/pic/") + 5;
            String filePath ="/StudyCommunity/"+ fullPath.substring(index);
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
                    return filePath;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }




}
