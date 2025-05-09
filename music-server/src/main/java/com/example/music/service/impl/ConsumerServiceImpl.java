package com.example.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.api.ApiResult;
import com.example.music.mapper.ConsumerMapper;
import com.example.music.model.dto.UserDTO;
import com.example.music.model.entity.Consumer;
import com.example.music.service.ConsumerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private ConsumerMapper consumerMapper;

    @Value("${resource.base-path}")
    private String basePath;

    @Value("${resource.avatar-path}")
    private String avatarPath;

    @Override
    public ApiResult register(UserDTO userDTO) {
        logger.info("开始注册用户: {}", userDTO.getUsername());
        
        // 检查用户名是否已存在
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        if (consumerMapper.selectOne(queryWrapper) != null) {
            logger.warn("用户名已存在: {}", userDTO.getUsername());
            return ApiResult.error("用户名已存在");
        }

        try {
            // 创建新用户
            Consumer consumer = new Consumer();
            BeanUtils.copyProperties(userDTO, consumer);
            consumer.setPassword(userDTO.getPassword());
            consumer.setCreateTime(new Date());
            consumer.setUpdateTime(new Date());
            consumerMapper.insert(consumer);
            logger.info("用户注册成功: {}", userDTO.getUsername());
            return ApiResult.success();
        } catch (Exception e) {
            logger.error("用户注册失败: {}", e.getMessage(), e);
            return ApiResult.error("注册失败: " + e.getMessage());
        }
    }

    @Override
    public ApiResult login(UserDTO userDTO) {
        // 查询用户
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        Consumer consumer = consumerMapper.selectOne(queryWrapper);

        // 验证用户存在性和密码
        if (consumer == null || !userDTO.getPassword().equals(consumer.getPassword())) {
            return ApiResult.error("用户名或密码错误");
        }

        return ApiResult.success(consumer);
    }

    @Override
    public ApiResult getUserInfo() {
        // 从session中获取用户信息
        Consumer consumer = consumerMapper.selectById(1); // 这里需要根据实际情况获取用户ID
        return ApiResult.success(consumer);
    }

    @Override
    public ApiResult updateUserInfo(UserDTO userDTO) {
        Consumer consumer = consumerMapper.selectById(userDTO.getId());
        if (consumer == null) {
            return ApiResult.error("用户不存在");
        }

        BeanUtils.copyProperties(userDTO, consumer);
        consumer.setUpdateTime(new Date());
        consumerMapper.updateById(consumer);
        return ApiResult.success();
    }

    @Override
    public ApiResult updatePassword(UserDTO userDTO) {
        Consumer consumer = consumerMapper.selectById(userDTO.getId());
        if (consumer == null) {
            return ApiResult.error("用户不存在");
        }

        if (!userDTO.getPassword().equals(consumer.getPassword())) {
            return ApiResult.error("原密码错误");
        }

        consumer.setPassword(userDTO.getPassword());
        consumer.setUpdateTime(new Date());
        consumerMapper.updateById(consumer);
        return ApiResult.success();
    }

    @Override
    public ApiResult uploadAvatar(MultipartFile file) {
        logger.info("开始上传头像文件: {}", file.getOriginalFilename());
        
        if (file.isEmpty()) {
            logger.warn("上传的文件为空");
            return ApiResult.error("文件为空");
        }

        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = basePath + avatarPath + "/" + fileName;
            File dest = new File(filePath);
            
            logger.info("头像保存路径: {}", filePath);

            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            
            file.transferTo(dest);
            logger.info("头像上传成功: {}", fileName);
            
            return ApiResult.success(avatarPath + "/" + fileName);
        } catch (IOException e) {
            logger.error("头像上传失败: {}", e.getMessage(), e);
            return ApiResult.error("文件上传失败: " + e.getMessage());
        }
    }
} 