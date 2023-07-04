package com.sc.vo.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "UserInfoUpdate对象",description = "")
public class PostParam {

    private String post_id;
    private String phoneNum;
    private String classId;
    private String title;
    private String imagePath;
    private String videoPath;
    private String postContent;
    private int likes;
}
