package com.easyconv.easyconvserver.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
public class FileUtils {

    public final static Tika tika = new Tika();

    public static File convertMultipartFileToFile(MultipartFile multipartFile){
        // TODO 추가 로직 필요
        return new File("");
    }
}
