package com.sc.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@TableName("comment")
@Accessors(chain = true)
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    @TableId(value = "commentId", type = IdType.AUTO)
    private String commentId;

    @ApiModelProperty("对应帖子id")
    @TableField("postId")
    private String postId;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("文章")
    @TableField("detail")
    private String detail;

    @ApiModelProperty("time")
    @TableField("postTime")
    private String postTime;

    @ApiModelProperty("reply")
    @TableField("reply")
    private String reply;
}