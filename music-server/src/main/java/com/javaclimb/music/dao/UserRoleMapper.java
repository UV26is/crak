package com.javaclimb.music.dao;

import com.javaclimb.music.domain.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    /**
     * 添加用户角色
     */
    public int insert(UserRole userRole);

    /**
     * 修改用户角色
     */
    public int update(UserRole userRole);

    /**
     * 删除用户角色
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public UserRole selectByPrimaryKey(Integer id);

    /**
     * 查询所有用户角色
     */
    public List<UserRole> allUserRole();

    /**
     * 根据用户ID查询角色
     */
    public List<UserRole> selectByUserId(Integer userId);
} 