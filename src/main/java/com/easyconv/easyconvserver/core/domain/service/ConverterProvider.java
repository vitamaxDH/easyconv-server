package com.easyconv.easyconvserver.core.domain.service;

import com.easyconv.easyconvserver.core.domain.type.FormatType;
import com.easyconv.easyconvserver.core.exception.type.ResultCode;
import com.easyconv.easyconvserver.core.exception.UnsupportedFormatException;
import com.easyconv.easyconvserver.core.util.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;

@Slf4j
@Service
public class ConverterProvider {
    private final Map<FormatType, Convertible> convertibleMap = new HashMap<>();

    public ConverterProvider(){
        init();
    }

    private void init() {
        for (FormatType type: FormatType.values()){
            convertibleMap.put(type, BeanContext.getBean(type.getConvertible()));
        }
    }

    public Convertible provide(FormatType formatType){
        Convertible convertible = convertibleMap.get(formatType);
        if (convertible != null){
            return convertible;
        }
        throw new UnsupportedFormatException(ResultCode.RESULT_9999);
    }

    public Convertible of(MultipartFile inputFile) throws IOException {
        return of(inputFile.getBytes(), inputFile.getOriginalFilename());
    }

    public Convertible of(byte[] bytes, String fileName) {
        return provide(bytes, fileName);
    }

    public Convertible provide(byte[] bytes, String fileName) {
        FormatType formatType = FormatType.getFormatType(TIKA.detect(bytes), fileName);
        return provide(formatType);
    }

}
