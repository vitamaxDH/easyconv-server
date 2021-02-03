package com.easyconv.easyconvserver.core.util;

public enum ExtensionType {
    PDF("pdf"), PDF_WITH_DOT(".pdf"),
    XLS("xls"), XLS_WITH_DOT(".xls"),
    XLSX("xlsx"), XLSX_WITH_DOT(".xlsx"),
    ;
    private final String value;

    ExtensionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
