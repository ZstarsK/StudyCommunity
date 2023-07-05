package com.sc.entity;

import ch.qos.logback.core.db.ConnectionSourceBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户手机")
    @TableField("username")
    private String username;
    @ApiModelProperty("用户名称")
    @TableField("name")
    private String name;
    @ApiModelProperty("密码")
    @TableField("password")
    private String password;
    @ApiModelProperty("班级id")
    @TableField("clazzId")
    private String clazzId;
    @ApiModelProperty("用户性别")
    @TableField("sex")
    private String sex;
    @ApiModelProperty("用户身份")
    @TableField("role")
    private String role;
    @ApiModelProperty("用户学号")
    @TableField("schoolId")
    private String schoolId;
    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;
    @ApiModelProperty("用户背景")
    @TableField("cover")
    private String cover;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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

