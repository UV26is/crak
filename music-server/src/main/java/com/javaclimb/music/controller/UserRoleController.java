package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.UserRole;
import com.javaclimb.music.service.UserRoleService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加用户角色
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUserRole(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId").trim();
        String roleName = request.getParameter("roleName").trim();

        UserRole userRole = new UserRole();
        userRole.setUserId(Integer.parseInt(userId));
        userRole.setRoleName(roleName);

        boolean flag = userRoleService.insert(userRole);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }

    /**
     * 修改用户角色
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateUserRole(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String userId = request.getParameter("userId").trim();
        String roleName = request.getParameter("roleName").trim();

        UserRole userRole = new UserRole();
        userRole.setId(Integer.parseInt(id));
        userRole.setUserId(Integer.parseInt(userId));
        userRole.setRoleName(roleName);

        boolean flag = userRoleService.update(userRole);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    /**
     * 删除用户角色
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteUserRole(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        boolean flag = userRoleService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        return userRoleService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询所有用户角色
     */
    @RequestMapping(value = "/allUserRole", method = RequestMethod.GET)
    public Object allUserRole(HttpServletRequest request) {
        return userRoleService.allUserRole();
    }

    /**
     * 根据用户ID查询角色
     */
    @RequestMapping(value = "/userRoleOfUserId", method = RequestMethod.GET)
    public Object userRoleOfUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();
        return userRoleService.selectByUserId(Integer.parseInt(userId));
    }
} 