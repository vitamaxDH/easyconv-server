package com.easyconv.easyconvserver.core.exception;

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
