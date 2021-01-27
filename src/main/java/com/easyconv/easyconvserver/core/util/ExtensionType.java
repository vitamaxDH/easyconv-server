package com.easyconv.easyconvserver.core.util;

public enum ExtensionType {
    PDF("pdf"),
    PDF_WITH_DOT(".pdf"),
    ;
    private final String value;

    ExtensionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
