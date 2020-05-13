package com.it.shiro.mapper;

import com.it.shiro.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    public User getUser(String username);
}
