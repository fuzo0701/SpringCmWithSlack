package kr.ccos.springcmwithslack.exception;

import kr.ccos.springcmwithslack.dto.ErrorResultDto;
import kr.ccos.springcmwithslack.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final SlackService slackService;

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handdleCustomException(final RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        sendSlackMessage(e, errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeResponseBody(errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorCode errorCode = makeErrorCode(ex);
        sendSlackMessage(ex, errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeResponseBody(errorCode));
    }

    private ErrorCode makeErrorCode(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return CommonErrorCode.BAD_REQUEST;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return CommonErrorCode.METHOD_NOT_ALLOWED;
        } else if (ex instanceof NoHandlerFoundException) {
            return CommonErrorCode.NOT_FOUND;
        } else if (ex instanceof HttpMessageNotReadableException) {
            return CommonErrorCode.BAD_REQUEST;
        }

        return CommonErrorCode.INTERNAL_SERVER_ERROR;
    }

    private ErrorResultDto makeResponseBody(ErrorCode errorCode) {
        return new ErrorResultDto(errorCode.getHttpStatus(), errorCode.getMessage());
    }

    private void sendSlackMessage(Exception e, ErrorCode errorCode) {
        HashMap<String, String> message = new HashMap<>();
        message.put("에러 로그", e.getMessage());
        slackService.sendMessage(errorCode.getMessage(), message);
    }
}
