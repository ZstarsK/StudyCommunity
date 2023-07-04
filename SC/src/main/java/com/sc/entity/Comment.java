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
    @TableId(value = "comment_id", type = IdType.AUTO)
    private String comment_id;

    @ApiModelProperty("对应帖子id")
    @TableField("post_id")
    private String post_id;

    @ApiModelProperty("评论者手机")
    @TableField("phonenum")
    private String phonenum;

    @ApiModelProperty("文章")
    @TableField("detail")
    private String detail;


}