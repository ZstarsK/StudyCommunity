package com.sc.Util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc.entity.Comment;

import java.util.List;

public class DataUtil {
    //获取对应表中的搜索条件
    public static <T> QueryWrapper<T> getQueried( Class<T> clazz,
                                                String tableField, String id) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(tableField, id);
        return queryWrapper;
    }


}
