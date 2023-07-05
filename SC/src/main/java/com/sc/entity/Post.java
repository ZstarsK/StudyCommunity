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
@TableName("post")
@Accessors(chain = true)
@ApiModel(value = "Post对象", description = "")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("帖子id")
    @TableId(value = "postId", type = IdType.AUTO)
    private String postId;

    @ApiModelProperty("评论者手机")
    @TableField("phonenum")
    private String phonenum;

    @ApiModelProperty("班级id")
    @TableField("clazzId")
    private String clazzId;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("文章")
    @TableField("detail")
    private String detail;

    @ApiModelProperty("时间")
    @TableField("postTime")
    private String postTime;

    @ApiModelProperty("图片")
    @TableField("image")
    private String image;
    @ApiModelProperty("视频")
    @TableField("video")
    private String video;
    @ApiModelProperty("赞数")
    @TableField("likes")
    private int likes;

}