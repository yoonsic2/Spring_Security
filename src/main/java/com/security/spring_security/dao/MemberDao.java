package com.security.spring_security.dao;

import com.security.spring_security.dto.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberDao {
    @Insert("INSERT INTO MEMBER VALUES (#{username}, #{password}, #{role}, #{email})")
    void join(MemberDto memberDto);

    @Select("SELECT * FROM MEMBER WHERE USERNAME = #{username}")
    MemberDto selectMemberInfo(String username);
}
