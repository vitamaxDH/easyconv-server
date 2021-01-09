package com.easyconv.easyconvserver.core.conversion.service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public abstract class Converter<T> {

    abstract Converter convert(File file);
    abstract Converter convert(String input, String output) throws IOException, ParserConfigurationException;

}
