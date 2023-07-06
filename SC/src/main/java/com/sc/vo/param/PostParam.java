package com.sc.vo.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.security.PrivateKey;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "Post对象",description = "")
public class PostParam {

    private String postId;
    private String username;
    private String classId;
    private String title;
    private String postContent;
    private MultipartFile image;
    private String imagePath;
    private MultipartFile video;
    private String videoPath;
    private String commentsId;
    private int likes;
}
