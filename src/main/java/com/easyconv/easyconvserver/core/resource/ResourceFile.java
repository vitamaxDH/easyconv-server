package com.easyconv.easyconvserver.core.resource;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@Data
@NoArgsConstructor
@ToString
public class ResourceFile {

    private File resourceFile;
    private String filePath;

    public static ResourceFile create(){
        return new ResourceFile();
    }
}
