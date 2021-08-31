package com.easyconv.easyconvserver.web.payload.response;

import com.easyconv.easyconvserver.core.exception.type.ResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Response<T>{

    protected String resultCode;
    protected String resultMessage;
    protected LocalDateTime systemDt = LocalDateTime.now();
    private T data;

    @Builder
    public Response(String resultCode, String resultMessage, LocalDateTime systemDt, T data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.systemDt = systemDt;
        this.data = data;
    }

    public static Response create(){
        return new Response();
    }

    public static Response of(ResultCode resultCode){
        return Response.builder()
                .resultCode(resultCode.getResultCode())
                .resultMessage(resultCode.getResultMessage())
                .build();
    }

    public static Response ok(){
        return of(ResultCode.RESULT_0000);
    }

    public static <T> Response ok(T data){
        return ok().data(data);
    }

    public static Response fail(){
        return of(ResultCode.RESULT_9999);
    }

    public static <T> Response fail(T data){
        return fail().data(data);
    }

    private Response data(T data){
        this.data = data;
        return this;
    }

}
