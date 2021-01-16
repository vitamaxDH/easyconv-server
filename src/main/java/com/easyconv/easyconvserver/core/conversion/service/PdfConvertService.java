package com.easyconv.easyconvserver.core.conversion.service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.easyconv.easyconvserver.config.Config;
import com.easyconv.easyconvserver.core.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;


@Slf4j
@RequiredArgsConstructor
@Service
public class PdfConvertService implements Convertable {

    private final String TEMP_PATH = Config.getProperty("com.easyconv.pdf.file.path");

    @Override
    public void convert(File inputFile) throws IOException {
    }

    @Override
    public void convert(MultipartFile inputFile) throws IOException {
    }

    public byte[] convertAndSend(MultipartFile inputFile) throws IOException {
        File outputFile = new File(TEMP_PATH + "temp");
        try  (
                OutputStream outputStream = new FileOutputStream(outputFile);
        ){
            DocumentType documentType = getDocumentType(inputFile.getInputStream());
            IConverter converter = LocalConverter.builder().build();
            converter.convert(inputFile.getInputStream()).as(documentType).to(outputStream).as(DocumentType.PDF).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FileUtils.readFileToByteArray(outputFile);
    }

    @Override
    public void convert(String inputPath, String output) throws IOException {

    }

    public DocumentType getDocumentType(InputStream inputStream) throws  IOException {
        String mimeType = TIKA.detect(inputStream);
        return DocumentTypes.getDocumentType(mimeType);
    }

    enum DocumentTypes {
        DOCX("application/msword", DocumentType.MS_WORD),
        DOC("application/vnd.openxmlformats-officedocument.wordprocessingml.document", DocumentType.MS_WORD),
        XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", DocumentType.MS_EXCEL),
        EXCEL("application/vnd.com.documents4j.any-msexcel", DocumentType.MS_EXCEL),
        XLS("application/vnd.ms-excel", DocumentType.MS_EXCEL),
        CSV("text/csv", DocumentType.CSV),
        AXML("application/xml", DocumentType.XML),
        TXML("text/xml", DocumentType.XML),
        HTML("text/html", DocumentType.HTML),
        PDF("application/pdf", DocumentType.PDF),
        PDFA("html", DocumentType.PDFA),
        TEXT("text/plain", DocumentType.TEXT),
        ;
        private String mimeType;
        private DocumentType documentType;
        private static Map<String, DocumentType> map = new HashMap<>();

        static {
            for(DocumentTypes types: values()){
                map.put(types.mimeType, types.documentType);
            }
        }
        DocumentTypes(String mimeType, DocumentType documentType) {
            this.mimeType = mimeType;
            this.documentType = documentType;
        }

        public String getMimeType() {
            return mimeType;
        }

        public DocumentType getDocumentType() {
            return documentType;
        }

        public static DocumentType getDocumentType(String mimeType){
            return map.get(mimeType);
        }
    }

}
