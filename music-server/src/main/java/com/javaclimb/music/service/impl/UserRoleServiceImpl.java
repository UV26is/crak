package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.UserRoleMapper;
import com.javaclimb.music.domain.UserRole;
import com.javaclimb.music.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean insert(UserRole userRole) {
        return userRoleMapper.insert(userRole) > 0;
    }

    @Override
    public boolean update(UserRole userRole) {
        return userRoleMapper.update(userRole) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return userRoleMapper.delete(id) > 0;
    }

    @Override
    public UserRole selectByPrimaryKey(Integer id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserRole> allUserRole() {
        return userRoleMapper.allUserRole();
    }

    @Override
    public List<UserRole> selectByUserId(Integer userId) {
        return userRoleMapper.selectByUserId(userId);
    }
} 