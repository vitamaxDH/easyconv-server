package com.easyconv.easyconvserver.core.conversion.service;

import com.easyconv.easyconvserver.core.util.ExtensionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConverterProvider {
    private final WordConverter wordConverter;
    private final CellConverter cellConverter;

    public Convertible of(MultipartFile inputFile) throws IOException {
        return of(inputFile.getBytes(), inputFile.getOriginalFilename());
    }
    public Convertible of(byte[] bytes, String fileName) throws IOException {
        return getConverter(ExtensionType.getType(bytes, fileName));
    }
    public Convertible of(InputStream is) throws IOException {
        return getConverter(ExtensionType.getType(is));
    }

    private Convertible getConverter(ExtensionType type) {
        if (ExtensionType.WORD.equals(type)){
            log.info("Convert with a WordConverter");
            return wordConverter;
        } else if (ExtensionType.CELL.equals(type)) {
            log.info("Convert with a CellConverter");
            return cellConverter;
        }
        return wordConverter;
    }
}
