package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.Comment;
import com.sc.mapper.CommentMapper;
import com.sc.mapper.PostMapper;
import com.sc.service.CommentService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.sc.Util.DataUtil.*;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public ResultBean getCommentByPostId(String postId) {
        List<Comment> list=commentMapper.selectList(getQueried(Comment.class,"postId",postId));
        Collections.reverse(list);
        return ResultBean.success("评论获取成功",list);
    }

    @Override
    public ResultBean deleteCommentByCommentId(String commentId) {
        //删除评论
        commentMapper.delete(getQueried(Comment.class,"commentId",commentId));
        //删除对应回复
        commentMapper.delete(getQueried(Comment.class,"reply",commentId));

        return ResultBean.success("删除成功");
    }

    @Override
    public ResultBean saveUserCommentOrReply(CommentParam commentParam) {
        return ResultBean.success("发布成功!",saveValues(commentParam));
    }

    @Override
    public ResultBean updateCommentInfo(CommentParam commentParam) {

        UpdateWrapper<Comment> updateWrapper=getUpdated(Comment.class,
                "commentId", commentParam.getCommentId(),
                "detail",commentParam.getDetail());
        commentMapper.update(null,updateWrapper);

        return ResultBean.success("评论修改成功",commentMapper.selectOne(updateWrapper));
    }

//    @Override
//    public ResultBean getReplyByCommentId(String commentId) {
//        return ResultBean.success("评论获取成功",commentMapper.selectList(
//                getQueried(Comment.class,"reply",commentId)));
//    }


    @Override
    public Comment saveValues(CommentParam commentParam) {
        String commentId= commentParam.getCommentId();
        Comment comment=new Comment();
        if (commentId!=null&&!commentId.isEmpty()) comment.setReply(commentParam.getCommentId());
        comment.setPostId(commentParam.getPostId());
        comment.setUsername(commentParam.getUsername());
        comment.setDetail(commentParam.getDetail());
        comment.setPostTime(commentParam.getPostTime());
        this.save(comment);
        return comment;
    }
}
