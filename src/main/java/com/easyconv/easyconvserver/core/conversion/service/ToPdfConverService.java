package com.easyconv.easyconvserver.core.conversion.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ToPdfConverService extends Converter{

    @Override
    public void convert(File inputFile) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(new File("D:/sample/output/html.pdf")));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(inputFile));
        document.close();
    }

    @Override
    public void convert(MultipartFile inputFile) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(new File("D:/sample/output/html.pdf")));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                inputFile.getInputStream());
        document.close();
    }

    @Override
    public void convert(String input, String output) throws IOException, ParserConfigurationException {

    }


}
