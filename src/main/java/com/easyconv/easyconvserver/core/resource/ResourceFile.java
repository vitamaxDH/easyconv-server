package com.easyconv.easyconvserver.core.resource;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;

@Slf4j
@Data
@NoArgsConstructor
@ToString
public class ResourceFile {

    private ByteArrayResource byteArrayResource;

    public static ResourceFile create(){
        return new ResourceFile();
    }
}
