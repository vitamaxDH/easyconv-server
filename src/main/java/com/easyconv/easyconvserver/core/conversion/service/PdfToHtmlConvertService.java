package com.easyconv.easyconvserver.core.conversion.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@Slf4j
@Service
public class PdfToHtmlConvertService extends Converter{

    @Override
    public Converter convert(String inputPath, String outputPath) throws IOException, ParserConfigurationException {

        PDDocument pdf = PDDocument.load(new File(inputPath));
        Writer output = new PrintWriter(outputPath, "utf-8");
        new PDFDomTree().writeText(pdf, output);

        output.close();
        return null;
    }

    @Override
    public Converter convert(File file) {


        return this;
    }
}
