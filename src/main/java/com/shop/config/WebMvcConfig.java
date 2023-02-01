package com.shop.config;

import org.codehaus.groovy.control.io.ReaderSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}") // application.properties 설정한 uploadPath
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                // images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을
                // 읽어오도록 설정 업로드패스를이용한 이미지를 가져온다.
                // 절대경로를 상대경로로 바꾸었다.
                // 아까 shop1에있던 것을 "/images/**"라고 치면 나올것이다
                //UploadPath = "D:/shop
                // images/item/xxx.jpg
                //경로를 image로 바꾸어준것
                .addResourceLocations(uploadPath); //로컬 컴퓨터에서 root 결로를 설정
    }

}
