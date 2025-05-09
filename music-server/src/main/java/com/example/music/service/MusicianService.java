package com.example.music.service;

import com.example.music.common.api.ApiResult;
import com.example.music.model.entity.Musician;

public interface MusicianService {
    ApiResult apply(Musician musician);
    ApiResult getMusicianInfo();
    ApiResult updateMusicianInfo(Musician musician);
    ApiResult getMusicianList(Integer pageNum, Integer pageSize);
    ApiResult approveMusician(Integer id);
    ApiResult rejectMusician(Integer id);
} 