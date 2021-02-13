package com.easyconv.easyconvserver.core.lab;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.easyconv.easyconvserver.EasyConvServerApplication;
import com.easyconv.easyconvserver.core.conversion.service.ConverterProvider;
import com.easyconv.easyconvserver.core.conversion.service.PdfConvertService;
import com.easyconv.easyconvserver.core.util.ExtensionType;
import com.easyconv.easyconvserver.core.util.FileUtils;
import org.apache.commons.compress.utils.FileNameUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EasyConvServerApplication.class, PdfConvertService.class})
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class ConvertTest {

    @Autowired
    private MockMvc mockMvc;
    private static final Logger log = LoggerFactory.getLogger(ConvertTest.class);

    final String SAMPLE_PATH = "C:/easyConv/resources/input/";
    String fileName = "202012_1.csv";
    String filePath = SAMPLE_PATH + fileName;
    String pdfPath = SAMPLE_PATH + "3.pdf";


    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private PdfConvertService pdfConvertService;
    @Autowired
    ConverterProvider convertConverter;

    File file;
    MockMultipartFile multipartFile;


    @BeforeEach
    public void setUp() throws IOException {
        file = new File(filePath);
        multipartFile = new MockMultipartFile("multipartFile"
                , fileName
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , new FileInputStream(file));
    }

    @Test
    public void index_controller_테스트한다() throws Throwable {
        this.mockMvc.perform(get("/test"))
                .andExpect(content().string("OK"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void convert() throws Throwable {
        this.mockMvc.perform(multipart("/api/convert").file(multipartFile))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void tikaTest() throws IOException {
        File file = new File(filePath);
        String type = TIKA.detect(file);
        log.info("type :: {}", type);
    }

    @Test
    public void creatDirectories() throws IOException {
        File file = FileUtils.createFile("test.txt", Boolean.FALSE);
        log.info("Does file exist? :: {}", file.exists());
    }

    @Test
    public void 프로바이더_테스트() throws Throwable {
        convertConverter.of(multipartFile);
    }

    @Test
    public void 엑셀테스트() throws Throwable {
        Workbook workbook = new Workbook(filePath);

        String baseName = FileNameUtils.getBaseName(fileName);
        File file = FileUtils.getOutputFile(null, baseName);
        workbook.save(file.getAbsolutePath(), SaveFormat.PDF);
    }

    @Test
    public void 워드테스트() throws Throwable {
    }
}
