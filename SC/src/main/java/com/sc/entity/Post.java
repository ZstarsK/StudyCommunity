package com.sc.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sc.vo.param.PostParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    @TableField("username")
    private String username;

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

    @ApiModelProperty("评论")
    @TableField("commentId")
    private String commentId;

    @ApiModelProperty("赞数")
    @TableField("likes")
    private int likes;

    
    public static Post createPostFromParam(PostParam postParam) {
        return new Post()
                .setPostId(postParam.getPostId())
                .setUsername(postParam.getUsername())
                .setClazzId(postParam.getClassId())
                .setTitle(postParam.getTitle())
                .setDetail(postParam.getPostContent())
                .setImage(postParam.getImagePath())
                .setVideo(postParam.getVideoPath())
                .setPostTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setLikes(postParam.getLikes());
    }
    
    

}