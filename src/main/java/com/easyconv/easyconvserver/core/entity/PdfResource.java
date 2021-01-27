package com.easyconv.easyconvserver.core.entity;

import com.easyconv.easyconvserver.config.Config;
import com.easyconv.easyconvserver.core.dto.GenericResourceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class PdfResource {

    @Id
    @Column
    private String id;

    @Column
    private String fileName;
    @Column
    private String parentPath;
    @Column
    private String fullPath;
    @Column
    private String requestIp;

    @Column
    private Instant createdTime;

    public static PdfResource create(){
        return new PdfResource();
    }

    public PdfResource setId(String id) {
        this.id = id;
        return this;
    }

    public PdfResource setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public PdfResource setParentPath(String parentPath) {
        this.parentPath = parentPath;
        return this;
    }

    public PdfResource setFullPath(String fullPath) {
        this.fullPath = fullPath;
        return this;
    }

    public PdfResource setRequestIp(String requestIp) {
        this.requestIp = requestIp;
        return this;
    }

    public PdfResource setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public PdfResource mapDtoToEntity(GenericResourceDto dto){
        MultipartFile file = dto.getMultipartFile();
        this.id = UUID.randomUUID().toString();
        this.fileName = file.getName();
        this.parentPath = Config.getProperty("com.easyconv.pdf.file.output");
        this.fullPath = this.parentPath + this.fileName;
        this.createdTime = Instant.now();
        return this;
    }
}
