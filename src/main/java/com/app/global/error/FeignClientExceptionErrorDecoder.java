package com.app.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * FeignClient 요청 시 200번대 HttpStatus 코드를 반환하면 정상으로 판단
 * 그 외의 경우를 처리할 ErrorDecoder 구현
 * 500번대 에러가 발생하면 RetryableException 발생시켜서 재시도하도록 처리
 */
@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("요청 실패 FeignClientExceptionErrorDecoder.decode() methodKey: {}, response: {}", methodKey, response);
        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());
        if (httpStatus.is5xxServerError()) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    null,
                    response.request());
        }
        return errorDecoder.decode(methodKey, response);
    }
}
