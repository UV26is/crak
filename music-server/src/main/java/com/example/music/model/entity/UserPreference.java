package com.example.music.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("user_preference")
public class UserPreference {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String preferredGenres;
    private String preferredArtists;
    private String preferredLanguages;
    private String preferredMoods;
    private String playlistPreferences;
    private Date createTime;
    private Date updateTime;

    public UserPreference() {
    }

    public UserPreference(Integer id, Integer userId, String preferredGenres, String preferredArtists, String preferredLanguages, String preferredMoods, String playlistPreferences, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.preferredGenres = preferredGenres;
        this.preferredArtists = preferredArtists;
        this.preferredLanguages = preferredLanguages;
        this.preferredMoods = preferredMoods;
        this.playlistPreferences = playlistPreferences;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPreferredGenres() {
        return preferredGenres;
    }

    public void setPreferredGenres(String preferredGenres) {
        this.preferredGenres = preferredGenres;
    }

    public String getPreferredArtists() {
        return preferredArtists;
    }

    public void setPreferredArtists(String preferredArtists) {
        this.preferredArtists = preferredArtists;
    }

    public String getPreferredLanguages() {
        return preferredLanguages;
    }

    public void setPreferredLanguages(String preferredLanguages) {
        this.preferredLanguages = preferredLanguages;
    }

    public String getPreferredMoods() {
        return preferredMoods;
    }

    public void setPreferredMoods(String preferredMoods) {
        this.preferredMoods = preferredMoods;
    }

    public String getPlaylistPreferences() {
        return playlistPreferences;
    }

    public void setPlaylistPreferences(String playlistPreferences) {
        this.playlistPreferences = playlistPreferences;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
} 