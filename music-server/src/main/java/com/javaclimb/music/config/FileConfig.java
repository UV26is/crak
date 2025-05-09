package com.javaclimb.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位各种文件或头像地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //歌手头像地址
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:/Users/cyan/Desktop/crakkel/musicfinal/ziyuanwenjian/img/singerPic/"
        );
        //歌单图片地址
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:/Users/cyan/Desktop/crakkel/musicfinal/ziyuanwenjian/img/songListPic/"
        );
        //歌曲图片地址
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:/Users/cyan/Desktop/crakkel/musicfinal/ziyuanwenjian/img/songPic/"
        );
        //歌曲地址
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:/Users/cyan/Desktop/crakkel/musicfinal/ziyuanwenjian/song/"
        );
        //前端用户头像地址
        registry.addResourceHandler("/avatorImages/**").addResourceLocations(
                "file:/Users/cyan/Desktop/crakkel/musicfinal/ziyuanwenjian/avatorImages/"
        );
        //用户头像默认地址
        registry.addResourceHandler("/img/**").addResourceLocations(
                "file:/Users/cyan/Desktop/crakkel/musicfinal/ziyuanwenjian/img/"
        );
    }
}

















