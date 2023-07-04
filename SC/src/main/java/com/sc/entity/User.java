package com.sc.entity;

import ch.qos.logback.core.db.ConnectionSourceBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@ToString
@TableName("userdata")
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "")
public class User implements  Serializable,UserDetails {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("用户手机")
    @TableId(value = "phonenum",type = IdType.ASSIGN_UUID)//UUID 全局唯一ID（UUID）
    private String phonenum;
    @ApiModelProperty("用户名称")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("班级id")
    private String clazz_id;
    @ApiModelProperty("用户性别")
    private String sex;
    @ApiModelProperty("用户身份")
    private String role;
    @ApiModelProperty("用户学号")
    private String school_id;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("用户背景")
    private String cover;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

