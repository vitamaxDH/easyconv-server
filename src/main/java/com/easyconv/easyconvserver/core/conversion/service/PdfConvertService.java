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
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;


@Slf4j
@RequiredArgsConstructor
@Service
public class PdfConvertService implements Convertable {

    private final PdfResourceRepository pdfResourceRepository;
    private final ConvertHistoryRepository convertHistoryRepository;

    @Override
    public GenericResourceDto convert(GenericResourceDto dto) throws Throwable {
        return convert(dto, null);
    }
    @Override
    public GenericResourceDto convert(GenericResourceDto dto, String outputPath) throws Throwable {
        log.info("convert :: START");
        MultipartFile inputFile = dto.getMultipartFile();
        File outputFile = getOutputFile(outputPath, inputFile.getOriginalFilename());
        log.info("convert :: outputFile {}", outputFile.getAbsolutePath());

        String extension = FileNameUtils.getExtension(inputFile.getOriginalFilename());
        if (FileUtils.isExcel(extension)){

            createPdf(inputFile);
            return dto;
        } else {
            boolean result = convert(inputFile.getInputStream(), outputFile);
            if (result){
                dto.setConvertedFile(outputFile);
                PdfResource resource = PdfResource.create().mapDtoToEntity(dto);
                ConvertHistory convertHistory = ConvertHistory.builder()
                                                              .clientIp(dto.getIp())
                                                              .build();
                pdfResourceRepository.save(resource);
                convertHistoryRepository.save(convertHistory);
            }
            return dto;
        }
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

    private File getOutputFile(String outputPath, String fileName) throws IOException {
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

    public void createPdf(MultipartFile inputFile) throws Throwable {
        createPdf(inputFile, null);
    }

    public void createPdf(MultipartFile inputFile, String outputPath) throws Throwable {
        String fileName = FileNameUtils.getBaseName(inputFile.getOriginalFilename());
        File outputFile = new File(outputPath, fileName);
        if (!StringUtils.hasLength(outputPath)){
            outputFile = getOutputFile(null, fileName);
        }
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        readWrite(document, inputFile.getInputStream());
        document.close();
    }

    public void readWrite(Document document, InputStream input) throws Throwable {
        try (Workbook workbook = WorkbookFactory.create(input)) {
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            int sheetnum = 0;
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                workbook.getSheetAt(sheetnum);
                sheetnum++;

                DataFormatter dataFormatter = new DataFormatter();

                PdfPTable table = new PdfPTable(7);
                Paragraph p;
                Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12);
                boolean title = true;

                for (Row row : sheet) {
                    String cellValue;
                    if (4 <= row.getRowNum() && row.getRowNum() <= 155) {
                        for (Cell cell : row) {
                            cellValue = dataFormatter.formatCellValue(cell);
                            table.addCell(cellValue);
                        }
                    } else if (row.getRowNum() < 4) {
                        for (Cell cell : row) {
                            cellValue = dataFormatter.formatCellValue(cell);

                            p = new Paragraph(cellValue, title ? normal : normal);
                            p.setAlignment(Element.ALIGN_JUSTIFIED);
                            document.add(p);
                        }
                    }
                }
                document.add(new Paragraph(" "));
                float[] columnWidths = new float[]{5f, 0f, 35f, 7f, 7f, 5f, 15f};
                table.setWidths(columnWidths);
                table.setTotalWidth(550);
                table.setLockedWidth(true);
                document.add(table);
                for (Row row : sheet) {
                    if (row.getRowNum() >154) {
                        for (Cell cell : row) {

                            String cellValue = dataFormatter.formatCellValue(cell);

                            p = new Paragraph(cellValue, title ? normal : normal);
                            p.setAlignment(Element.ALIGN_JUSTIFIED);
                            document.add(p);
                        }
                    }
                }
            }
        }
    }
}
