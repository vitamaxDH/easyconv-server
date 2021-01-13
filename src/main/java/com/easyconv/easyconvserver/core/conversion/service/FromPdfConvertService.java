package com.easyconv.easyconvserver.core.conversion.service;

import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
@Service
public class FromPdfConvertService extends Converter{

    @Override
    public void convert(File inputFile) throws DocumentException, FileNotFoundException {

    }

    @Override
    public void convert(MultipartFile MultipartFile) throws DocumentException, IOException {

    }

    @Override
    public void convert(String inputPath, String outputPath) throws IOException, ParserConfigurationException {

        PDDocument pdf = PDDocument.load(new File(inputPath));
        Writer output = new PrintWriter(outputPath, "utf-8");
        new PDFDomTree().writeText(pdf, output);

        output.close();
    }

    public void toImage(MultipartFile multipartFile) throws DocumentException, IOException {
        PDDocument document = PDDocument.load(multipartFile.getInputStream());
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(
                    page, 300, ImageType.RGB);
            ImageIOUtil.writeImage(
                    bim, String.format("D:/sample/output/image.png", page + 1, "png"), 300);
        }
        document.close();
    }
}
