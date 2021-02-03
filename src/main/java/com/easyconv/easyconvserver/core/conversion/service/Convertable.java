package com.easyconv.easyconvserver.core.conversion.service;

import com.easyconv.easyconvserver.core.dto.GenericResourceDto;

import java.io.IOException;

public interface Convertable {

    public GenericResourceDto convert(GenericResourceDto dto) throws Throwable;
    public GenericResourceDto convert(GenericResourceDto dto, String outputPath) throws Throwable;
}
