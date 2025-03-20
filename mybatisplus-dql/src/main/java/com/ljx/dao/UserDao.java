package com.ljx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljx.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
