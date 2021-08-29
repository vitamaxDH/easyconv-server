package com.easyconv.easyconvserver.core.domain.service.cell;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.easyconv.easyconvserver.core.domain.service.Convertible;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
public class CellConverter implements Convertible {

    @Override
    public String convert(InputStream is, File outputFile) throws Exception {
        String outputPath = outputFile.getAbsolutePath();
        Workbook workbook = new Workbook(is);
        workbook.save(outputPath, SaveFormat.PDF);
        log.info("convert :: outputPath : {}", outputPath);
        return outputPath;
    }
}
