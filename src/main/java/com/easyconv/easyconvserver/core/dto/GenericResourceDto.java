package com.easyconv.easyconvserver.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResourceDto {

    private MultipartFile multipartFile;
    private String ip;

    public static GenericResourceDto create(){
        return new GenericResourceDto();
    }

    public GenericResourceDto setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        return this;
    }

    public GenericResourceDto setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
