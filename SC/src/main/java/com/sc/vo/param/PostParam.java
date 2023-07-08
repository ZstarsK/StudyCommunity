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
    private String clazzId;
    private String title;
    private String detail;
    private String image;
    //private String video;
    private String postTime;
    private int likes;
}
