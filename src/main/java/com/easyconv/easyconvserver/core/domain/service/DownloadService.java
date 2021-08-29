package com.easyconv.easyconvserver.core.domain.service;

import com.easyconv.easyconvserver.core.entity.PdfResource;
import com.easyconv.easyconvserver.core.repository.PdfResourceRepository;
import com.easyconv.easyconvserver.core.util.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadService {

    private final PdfResourceRepository pdfResourceRepository;

    public ResponseEntity download(String fileId) throws Exception {
        PdfResource resource = pdfResourceRepository.findById(fileId)
                .orElseThrow(() -> new Exception("The file does not exist."));

        File file = new File(resource.getFullPath());
        FileInputStream fis = new FileInputStream(file);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = TIKA.detect(file);
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + MimeTypeUtils.CHARSET_UTF_8;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFileName() + "\"")
                .body(resource);
    }
}
