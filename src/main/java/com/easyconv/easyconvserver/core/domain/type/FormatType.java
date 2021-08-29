package com.easyconv.easyconvserver.core.domain.type;

import com.aspose.html.internal.ms.System.NotSupportedException;
import com.easyconv.easyconvserver.core.domain.service.Convertible;
import com.easyconv.easyconvserver.core.domain.service.cell.CellConverter;
import com.easyconv.easyconvserver.core.domain.service.html.HtmlConverter;
import com.easyconv.easyconvserver.core.domain.service.word.WordConverter;
import com.easyconv.easyconvserver.core.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.easyconv.easyconvserver.core.util.StringUtils.contains;

@Slf4j
@AllArgsConstructor
public enum FormatType {

    HTML("HTML", HtmlConverter.class),
    CELL("XL, XLS", CellConverter.class),
    WORD("WORD", WordConverter.class),
    SLIDE("POWERPOINT", WordConverter.class),
//    PDF("PDF", PdfConvertService.class),

    ;

    @Getter
    private final String desc;

    @Getter
    private final Class<? extends Convertible> convertible;

    public static FormatType getFormatType(String type, String fileName) {
        log.info("getConverter :: type = {}", type);

        String[] cellTargets = {"sheet", "spread", "xls", "csv"};
        String[] wordTargets = {"word"};
        String[] htmlTargets = {"html"};
        String[] slidesTargets = {"powerpoint", "presentation"};

        if (contains(type, cellTargets)) {
            log.info("getConverter :: Convert with cellConverter");
            return CELL;
        } else if (contains(type, wordTargets)) {
            log.info("getConverter :: Convert with wordConverter");
            return WORD;
        } else if (contains(type, htmlTargets)) {
            log.info("getConverter :: Convert with htmlConverter");
            return HTML;
        } else if (contains(type, slidesTargets)) {
            log.info("getConverter :: Convert with slidesConverter");
            return SLIDE;
        }
        throw new NotSupportedException();
    }


}
