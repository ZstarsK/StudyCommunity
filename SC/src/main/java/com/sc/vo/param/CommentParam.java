package com.sc.vo.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "Comment对象",description = "")
public class CommentParam {
    @ApiModelProperty("评论id")
    private String commentId;

    @ApiModelProperty("对应帖子id")
    private String postId;

    @ApiModelProperty("评论者手机")
    private String username;

    @ApiModelProperty("文章")
    private String detail;

    @ApiModelProperty("time")
    private int postTime;
}
