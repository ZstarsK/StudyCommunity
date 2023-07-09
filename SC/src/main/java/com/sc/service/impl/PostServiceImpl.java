package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Comment;
import com.sc.entity.Post;
import com.sc.mapper.CommentMapper;
import com.sc.mapper.PostMapper;
import com.sc.mapper.UserMapper;
import com.sc.service.PostService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.PostParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        String imagePath=postMapper.selectById(postId).getImage();
        String videoPath=postMapper.selectById(postId).getVideo();
        System.out.println(imagePath);
        if(imagePath!=null&&!imagePath.isEmpty()) deleteFile(imagePath);
        if(videoPath!=null&&!videoPath.isEmpty()) deleteFile(videoPath);

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

        post.setUsername(postParam.getUsername());
        post.setClazzId(postParam.getClazzId());
        post.setTitle(postParam.getTitle());
        post.setDetail(postParam.getDetail());
        post.setImage(postParam.getImage());
        //post.setVideo(postParam.getVideoPath());
        post.setPostTime(postParam.getPostTime());
        post.setLikes(postParam.getLikes());

        this.save(post);

        return ResultBean.success("动态发发布成功!",post);
    }

    @Override
    public String savePostUrl(String url) {
        Post post=new Post();
        post.setImage(url);
        postMapper.insert(post);
        return postMapper.selectOne(getQueried(Post.class,"image",url)).getPostId();
    }

    @Override
    public ResultBean updatePostInfoById(PostParam postParam) {
        String postId=postParam.getPostId();
        Post post=postMapper.selectById(postId);

//        if (postParam.getImagePath()==null||postParam.getImagePath().isEmpty())deleteFile(post.getImage());
//        if (postParam.getVideoPath()==null||postParam.getVideoPath().isEmpty())deleteFile(post.getVideo());
        this.update(getUpdated(Post.class,"postId",postId,
                "title",postParam.getTitle()));
        this.update(getUpdated(Post.class, "postId",postId,
                "detail",postParam.getDetail()));
        this.update(getUpdated(Post.class, "postId",postId,
                "username",postParam.getUsername()));
        this.update(getUpdated(Post.class, "postId",postId,
                "clazzId",postParam.getClazzId()));
        this.update(getUpdated(Post.class, "postId",postId,
                "image",postParam.getImage()));
        this.update(getUpdated(Post.class, "postId",postId,
                "postTime",postParam.getPostTime()));
        this.update(getUpdated(Post.class, "postId",postId,
                "likes",postParam.getLikes()));




//        this.update(Wrappers.lambdaUpdate(post).set(Post::getVideo,postParam.getVideoPath())
//                .eq(Post::getPostId,postParam.getPostId()));


        return ResultBean.success("动态更新成功",post);
    }

    @Override
    public void updateUrl(String tableField, String url, String postId) {
        this.update(new UpdateWrapper<Post>().set("image",url).eq("postId",postId));
        // this.update(new UpdateWrapper<User>().set("portrait",pathDB).eq("id",id));

    }
    @Override
    public int updateLikes(String postId,int likes) {
        postMapper.update(null,getUpdated(Post.class,"postId",
                postId,"likes", likes));
        return likes;
    }

    @Override
    public void deleteFile(String fullPath) {
        int index = fullPath.indexOf("/pic/") + 5;
        String filePath ="/StudyCommunity/"+ fullPath.substring(index);
        File originFile = new File(filePath);
        originFile.delete();
    }







}
