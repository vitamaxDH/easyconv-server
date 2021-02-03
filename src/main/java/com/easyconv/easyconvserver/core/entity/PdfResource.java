package com.easyconv.easyconvserver.core.entity;

import com.easyconv.easyconvserver.core.dto.GenericResourceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
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
    private Instant createdTime = Instant.now();

    public static PdfResource create(){
        return new PdfResource();
    }

    public PdfResource mapDtoToEntity(GenericResourceDto dto){
        File file = dto.getConvertedFile();
        this.id = UUID.randomUUID().toString();
        this.fileName = file.getName();
        this.parentPath = file.getParent();
        this.fullPath = file.getAbsolutePath();
        return this;
    }
}
