package com.javaclimb.music.service;

import com.javaclimb.music.domain.UserRole;

import java.util.List;

public interface UserRoleService {
    /**
     * 添加用户角色
     */
    public boolean insert(UserRole userRole);

    /**
     * 修改用户角色
     */
    public boolean update(UserRole userRole);

    /**
     * 删除用户角色
     */
    public boolean delete(Integer id);

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