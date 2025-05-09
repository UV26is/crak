package com.example.music.controller;

import com.example.music.common.api.ApiResult;
import com.example.music.model.dto.UserDTO;
import com.example.music.model.entity.Consumer;
import com.example.music.model.entity.Musician;
import com.example.music.model.entity.UserPreference;
import com.example.music.service.ConsumerService;
import com.example.music.service.MusicianService;
import com.example.music.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private UserPreferenceService userPreferenceService;

    @PostMapping("/register")
    public ApiResult register(@RequestBody UserDTO userDTO) {
        return consumerService.register(userDTO);
    }

    @PostMapping("/login")
    public ApiResult login(@RequestBody UserDTO userDTO) {
        return consumerService.login(userDTO);
    }

    @GetMapping("/info")
    public ApiResult getUserInfo() {
        return consumerService.getUserInfo();
    }

    @PutMapping("/info")
    public ApiResult updateUserInfo(@RequestBody UserDTO userDTO) {
        return consumerService.updateUserInfo(userDTO);
    }

    @PutMapping("/password")
    public ApiResult updatePassword(@RequestBody UserDTO userDTO) {
        return consumerService.updatePassword(userDTO);
    }

    @PostMapping("/avatar")
    public ApiResult uploadAvatar(@RequestParam("file") MultipartFile file) {
        return consumerService.uploadAvatar(file);
    }

    @PostMapping("/musician/apply")
    public ApiResult applyMusician(@RequestBody Musician musician) {
        return musicianService.apply(musician);
    }

    @GetMapping("/musician/info")
    public ApiResult getMusicianInfo() {
        return musicianService.getMusicianInfo();
    }

    @PutMapping("/musician/info")
    public ApiResult updateMusicianInfo(@RequestBody Musician musician) {
        return musicianService.updateMusicianInfo(musician);
    }

    @GetMapping("/preference")
    public ApiResult getUserPreference() {
        return userPreferenceService.getUserPreference();
    }

    @PutMapping("/preference")
    public ApiResult updateUserPreference(@RequestBody UserPreference preference) {
        return userPreferenceService.updateUserPreference(preference);
    }
} 