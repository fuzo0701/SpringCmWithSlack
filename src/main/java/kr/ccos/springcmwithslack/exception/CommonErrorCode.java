package kr.ccos.springcmwithslack.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 유저입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh Token 이 만료되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 파라미터 타입입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 메서드를 찾을 수 없습니다."),
    BAD_DTO_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 DTO 객체 바인딩 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
