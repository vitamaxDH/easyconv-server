package com.easyconv.easyconvserver.core.conversion.service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.easyconv.easyconvserver.core.util.ExtensionType;
import com.easyconv.easyconvserver.core.util.FileUtils;
import com.easyconv.easyconvserver.core.util.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Override
    public void convert(File inputFile) throws IOException {
    }

    @Override
    public void convert(MultipartFile inputFile) throws IOException {
    }

    public ByteArrayResource convertAsByteArrayResource(MultipartFile inputFile) throws IOException {
        return new ByteArrayResource(convertAsByteArray(inputFile));
    }

    public byte[] convertAsByteArray(MultipartFile inputFile) throws IOException {
        File outputFile = FileUtils.createFile(inputFile.getName() + ExtensionType.PDF_WITH_DOT, Boolean.TRUE);
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

    public ResponseEntity<? extends Resource> getByteArrayResource(MultipartFile multipartFile) throws IOException {
        Resource resource = convertAsByteArrayResource(multipartFile);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = TIKA.detect(multipartFile.getInputStream());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    enum DocumentTypes {
        DOCX(MimeTypeUtils.APPLICATION_MSWORD_VALUE, DocumentType.MS_WORD),
        DOC(MimeTypeUtils.APPLICATION_VND_MSWORD_VALUE, DocumentType.MS_WORD),
        XLSX(MimeTypeUtils.APPLICATION_VND_SPREADSHEETML_VALUE, DocumentType.MS_EXCEL),
        EXCEL(MimeTypeUtils.APPLICATION_VND_ANY_MSEXCEL_VALUE, DocumentType.MS_EXCEL),
        XLS(MimeTypeUtils.APPLICATION_VND_MSEXCEL_VALUE, DocumentType.MS_EXCEL),
        CSV(MimeTypeUtils.TEXT_CSV_VALUE, DocumentType.CSV),
        AXML(MimeTypeUtils.APPLICATION_XML_VALUE, DocumentType.XML),
        TXML(MimeTypeUtils.TEXT_XML_VALUE, DocumentType.XML),
        HTML(MimeTypeUtils.TEXT_HTML_VALUE, DocumentType.HTML),
        XHTML(MimeTypeUtils.APPLICATION_XHTML_XML_VALUE, DocumentType.HTML),
        PDF(MimeTypeUtils.APPLICATION_PDF_VALUE, DocumentType.PDF),
        TEXT(MimeTypeUtils.TEXT_PLAIN_VALUE, DocumentType.TEXT),
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
