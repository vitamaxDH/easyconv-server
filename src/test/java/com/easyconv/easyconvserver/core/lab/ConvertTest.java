package com.easyconv.easyconvserver.core.lab;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;

public class ConvertTest {

    private static final Logger log = LoggerFactory.getLogger(ConvertTest.class);

    final String SAMPLE_PATH = "D:/sample/test/";
    String docPath = SAMPLE_PATH + "abc.xlsx";
    String pdfPath = SAMPLE_PATH + "3.pdf";

    @Test
    public void convert(){

        File inputWord = new File(docPath);
        File outputFile = new File(pdfPath);
        try  (
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
                ){

            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.HTML).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tikaTest() throws IOException {
        File file = new File(docPath);
        String type = TIKA.detect(file);
        log.info("type :: {}", type);
    }
}
