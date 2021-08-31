package com.easyconv.easyconvserver.core.exception;

import com.easyconv.easyconvserver.core.exception.type.ResultCode;

public class UnsupportedFormatException extends RuntimeException {

    public UnsupportedFormatException() {
        super();
    }

    public UnsupportedFormatException(String message) {
        super(message);
    }

    public UnsupportedFormatException(ResultCode resultCode){
        super(resultCode.getResultMessage());
    }

    public UnsupportedFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedFormatException(Throwable cause) {
        super(cause);
    }

}
