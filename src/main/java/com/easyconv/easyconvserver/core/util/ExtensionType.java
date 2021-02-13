package com.easyconv.easyconvserver.core.util;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.easyconv.easyconvserver.core.util.FileUtils.TIKA;

public enum ExtensionType {
    PDF("pdf"), PDF_WITH_DOT(".pdf"),
    XLS("xls"), XLS_WITH_DOT(".xls"),
    XLSX("xlsx"), XLSX_WITH_DOT(".xlsx"),
    WORD("word"), CELL("cell"),
    ;
    private final String value;

    ExtensionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ExtensionType getType(String fileName){
        return getExtensionType(TIKA.detect(fileName));
    }

    public static ExtensionType getType(InputStream is) throws IOException {
        return getExtensionType(TIKA.detect(is));
    }
    public static ExtensionType getType(byte[] bytes, String fileName) throws IOException {
        return getExtensionType(TIKA.detect(bytes), fileName);
    }

    public static ExtensionType getExtensionType(String type){
        return getExtensionType(type, null);
    }

    private static ExtensionType getExtensionType(String type, String fileName){
        if (contains(type, "octet")){
            System.out.println("octet");
            return getExtensionType(fileName);
        }
        System.out.println("getExtensionType :: " + type);
        String[] cellTargets = {"sheet", "spread", "xls"};
        String[] wordTargets = {"word"};
        if (contains(type, cellTargets)){
            return CELL;
        } else if (contains(type, wordTargets)){
            return WORD;
        }
        return WORD;
    }

    private static boolean contains(String type, String ...targets){
        for(String target : targets ){
            if (type.contains(target)){
                return true;
            }
        }
        return false;
    }
}
