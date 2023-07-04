package com.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

}