package com.example.music.service;

import com.example.music.common.api.ApiResult;
import com.example.music.model.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ConsumerService {
    ApiResult register(UserDTO userDTO);
    ApiResult login(UserDTO userDTO);
    ApiResult getUserInfo();
    ApiResult updateUserInfo(UserDTO userDTO);
    ApiResult updatePassword(UserDTO userDTO);
    ApiResult uploadAvatar(MultipartFile file);
} 