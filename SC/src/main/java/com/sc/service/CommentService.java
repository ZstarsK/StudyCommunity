package com.sc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sc.entity.Comment;
import com.sc.vo.ResultBean;
import com.sc.vo.param.CommentParam;
import com.sc.vo.param.PostParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService extends IService<Comment> {

    ResultBean getCommentByPostId(String postId);

    ResultBean deleteCommentByCommentId(String commentId);

    ResultBean saveUserCommentOrReply(CommentParam commentParam);

    ResultBean updateCommentInfo(CommentParam commentParam);

    ResultBean getReplyByCommentId(String commentId);




    Comment saveValues(CommentParam commentParam);
}
