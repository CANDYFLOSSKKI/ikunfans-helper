package com.ctey.ikunfansdb.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctey.ikunfansdb.Model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
