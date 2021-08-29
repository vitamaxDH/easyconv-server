package com.easyconv.easyconvserver.core.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResourceDto implements Serializable {

    private static final long serialVersionUID = 8214370405210237516L;
    private MultipartFile multipartFile;
    private File convertedFile;
    private String ip;

}
