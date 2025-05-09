package com.example.music.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("musician")
public class Musician {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String biography;
    private String genre;
    private Date birthDate;
    private String nationality;
    private String instruments;
    private String achievements;
    private Date createTime;
    private Date updateTime;

    public Musician() {
    }

    public Musician(Integer id, String name, String biography, String genre, Date birthDate, String nationality, String instruments, String achievements, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.genre = genre;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.instruments = instruments;
        this.achievements = achievements;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getInstruments() {
        return instruments;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
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