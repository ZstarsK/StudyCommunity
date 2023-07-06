package com.sc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sc.entity.Comment;
import com.sc.vo.ResultBean;
import com.sc.vo.param.CommentParam;
import com.sc.vo.param.PostParam;
import org.springframework.stereotype.Service;

@Service
public interface CommentService extends IService<Comment> {
    Comment getCommentByCommentId(String commentId);

    ResultBean getCommentByPostId(String postId);

    ResultBean deleteCommentByCommentId(String commentId,String postId);

    ResultBean saveUserComment(CommentParam commentParam);

    ResultBean updateCommentInfo(CommentParam commentParam);
}
