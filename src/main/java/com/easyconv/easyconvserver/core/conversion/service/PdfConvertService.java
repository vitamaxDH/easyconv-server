package com.easyconv.easyconvserver.core.conversion.service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.easyconv.easyconvserver.core.dto.GenericResourceDto;
import com.easyconv.easyconvserver.core.entity.ConvertHistory;
import com.easyconv.easyconvserver.core.entity.PdfResource;
import com.easyconv.easyconvserver.core.repository.ConvertHistoryRepository;
import com.easyconv.easyconvserver.core.repository.PdfResourceRepository;
import com.easyconv.easyconvserver.core.util.ExtensionType;
import com.easyconv.easyconvserver.core.util.FileUtils;
import com.easyconv.easyconvserver.core.util.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;


@Slf4j
@RequiredArgsConstructor
@Service
public class PdfConvertService implements Convertable {

    private final PdfResourceRepository pdfResourceRepository;
    private final ConvertHistoryRepository convertHistoryRepository;

    @Override
    public GenericResourceDto convert(GenericResourceDto dto) throws IOException {
        return convert(dto, null);
    }
    @Override
    public GenericResourceDto convert(GenericResourceDto dto, String outputPath) throws IOException {
        log.info("convert :: START");
        MultipartFile inputFile = dto.getMultipartFile();
        File outputFile = this.getOutputFile(inputFile.getOriginalFilename(), outputPath);

        boolean result = convert(inputFile.getInputStream(), outputFile);
        if (result){
            PdfResource resource = PdfResource.create().mapDtoToEntity(dto);
            ConvertHistory convertHistory = ConvertHistory.builder()
                                                          .clientIp(dto.getIp())
                                                          .build();
            pdfResourceRepository.save(resource);
            convertHistoryRepository.save(convertHistory);
        }
        return dto;
    }

    private boolean convert(InputStream inputStream, String outputFilePath){
        return convert(inputStream, new File(outputFilePath));
    }

    private boolean convert(InputStream inputStream, File outputFile){
        boolean result = false;
        try (
                OutputStream outputStream = new FileOutputStream(outputFile);
        ){
            DocumentType documentType = getDocumentType(inputStream);
            IConverter converter = LocalConverter.builder().build();
            result = converter.convert(inputStream)
                    .as(documentType)
                    .to(outputStream)
                    .as(DocumentType.PDF)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private File getOutputFile(String fileName, String outputPath) throws IOException {
        fileName = FileNameUtils.getBaseName(fileName);
        if (ObjectUtils.isEmpty(outputPath)){
            return FileUtils.createFile(fileName + ExtensionType.PDF_WITH_DOT.getValue(), Boolean.TRUE);
        } else {
            return FileUtils.createFile(outputPath);
        }
    }

    public DocumentType getDocumentType(InputStream inputStream) throws  IOException {
        // todo excel 은 다 안먹는것 같으니 다른 라이브러리를 찾아봐야 할 듯

        String mimeType = TIKA.detect(inputStream);
        return DocumentTypes.getDocumentType(mimeType);
    }

    enum DocumentTypes {
        DOCX(MimeTypeUtils.APPLICATION_MSWORD_VALUE, DocumentType.MS_WORD),
        DOC(MimeTypeUtils.APPLICATION_VND_MSWORD_VALUE, DocumentType.MS_WORD),
        XLSX(MimeTypeUtils.APPLICATION_VND_SPREADSHEETML_VALUE, DocumentType.MS_EXCEL),
        EXCEL(MimeTypeUtils.APPLICATION_VND_ANY_MSEXCEL_VALUE, DocumentType.MS_EXCEL),
        XLS(MimeTypeUtils.APPLICATION_VND_MSEXCEL_VALUE, DocumentType.MS_EXCEL),
        MSOFFICE(MimeTypeUtils.APPLICATION_X_TIKA_MSOFFICE, DocumentType.MS_EXCEL),
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
