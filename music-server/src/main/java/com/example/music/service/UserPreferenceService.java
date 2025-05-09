package com.example.music.service;

import com.example.music.common.api.ApiResult;
import com.example.music.model.entity.UserPreference;

public interface UserPreferenceService {
    ApiResult getUserPreference();
    ApiResult updateUserPreference(UserPreference preference);
    ApiResult getRecommendedSongs();
    ApiResult getUserHistory();
    ApiResult clearUserHistory();
} 