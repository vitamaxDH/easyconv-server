package com.easyconv.easyconvserver.core.conversion.service;

import com.easyconv.easyconvserver.core.dto.GenericResourceDto;
import com.easyconv.easyconvserver.core.entity.ConvertHistory;
import com.easyconv.easyconvserver.core.entity.PdfResource;
import com.easyconv.easyconvserver.core.repository.ConvertHistoryRepository;
import com.easyconv.easyconvserver.core.repository.PdfResourceRepository;
import com.easyconv.easyconvserver.core.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Slf4j
@RequiredArgsConstructor
@Service
public class PdfConvertService {

    private final PdfResourceRepository pdfResourceRepository;
    private final ConvertHistoryRepository convertHistoryRepository;
    private final ConverterProvider converterProvider;

    public GenericResourceDto convert(GenericResourceDto dto) throws Throwable {
        return convert(dto, null);
    }

    public GenericResourceDto convert(GenericResourceDto dto, String outputPath) throws Throwable {
        log.info("convert :: START");
        MultipartFile inputFile = dto.getMultipartFile();
        File outputFile = FileUtils.getOutputFile(outputPath, inputFile.getOriginalFilename());
        log.info("convert :: outputFile {}", outputFile.getAbsolutePath());
        String extension = FileNameUtils.getExtension(inputFile.getOriginalFilename());
        Convertible converter = converterProvider.of(inputFile);

        converter.convert(inputFile);

        if (true){
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
