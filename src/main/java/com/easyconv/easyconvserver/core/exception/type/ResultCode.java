package com.easyconv.easyconvserver.core.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    RESULT_0000("0000", "SUCCESS"),
    RESULT_9999("9999", "FAIL"),

    ;
    private String resultCode;
    private String resultMessage;

}
