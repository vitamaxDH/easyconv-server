package com.easyconv.easyconvserver.core.conversion.service;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.easyconv.easyconvserver.core.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class CellConverter implements Convertible {

    @Override
    public void convert(MultipartFile inputFile) throws Exception {
        File file = FileUtils.createFileToday(inputFile.getOriginalFilename());
        inputFile.transferTo(file);
        convert(file.getAbsolutePath());
    }

    @Override
    public void convert(String inputFilePath) throws Exception {
        convert(inputFilePath, null);
    }

    @Override
    public void convert(String inputFilePath, String outputFilePath) throws Exception {
        File outputFile = FileUtils.getOutputFile(outputFilePath, inputFilePath);
        Workbook workbook = new Workbook(inputFilePath);
        workbook.save(outputFile.getAbsolutePath(), SaveFormat.PDF);
    }

}
