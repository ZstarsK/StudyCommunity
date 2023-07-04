package com.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
