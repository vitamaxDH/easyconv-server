package com.easyconv.easyconvserver.core.conversion.service;

import com.aspose.html.internal.ms.System.NotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConverterFactory {
    private final WordConverter wordConverter;
    private final CellConverter cellConverter;
    private final HtmlConverter htmlConverter;
    private final SlidesConverter slidesConverter;

    public Convertible of(MultipartFile inputFile) throws IOException {
        return of(inputFile.getBytes(), inputFile.getOriginalFilename());
    }
    public Convertible of(byte[] bytes, String fileName) {
        return getConverter(bytes, fileName);
    }

    public Convertible getConverter(String fileName){
        return getConverter(TIKA.detect(fileName), fileName);
    }

    public Convertible getConverter(byte[] bytes, String fileName) {
        return getConverter(TIKA.detect(bytes), fileName);
    }

    private Convertible getConverter(String type, String fileName){
        log.info("getConverter :: type = {}", type);
        if (contains(type, "octet")){
            return getConverter(fileName);
        }

        String[] cellTargets = {"sheet", "spread", "xls", "csv"};
        String[] wordTargets = {"word"};
        String[] htmlTargets = {"html"};
        String[] slidesTargets = {"powerpoint", "presentation"};

        if (contains(type, cellTargets)){
            log.info("getConverter :: Convert with cellConverter");
            return cellConverter;
        } else if (contains(type, wordTargets)){
            log.info("getConverter :: Convert with wordConverter");
            return wordConverter;
        } else if (contains(type, htmlTargets)){
            log.info("getConverter :: Convert with htmlConverter");
            return htmlConverter;
        }  else if (contains(type, slidesTargets)){
            log.info("getConverter :: Convert with slidesConverter");
            return slidesConverter;
        }
        throw new NotSupportedException();
    }

    private boolean contains(String type, String ...targets){
        for(String target : targets){
            if (type.contains(target)){
                return true;
            }
        }
        return false;
    }
}
