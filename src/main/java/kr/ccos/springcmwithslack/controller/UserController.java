package kr.ccos.springcmwithslack.controller;

import kr.ccos.springcmwithslack.dto.UserRequestDto;
import kr.ccos.springcmwithslack.exception.CommonErrorCode;
import kr.ccos.springcmwithslack.exception.RestApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/un-authorized")
    public ResponseEntity<Object> unauthorized() {
        throw new RestApiException(CommonErrorCode.UNAUTHORIZED);
    }

    @GetMapping("/expired-refresh-token")
    public ResponseEntity<Object> expiredRefreshToken() {
        throw new RestApiException(CommonErrorCode.EXPIRED_REFRESH_TOKEN);
    }

    // 잘못된 파라미터 요청
    @GetMapping("/parameter/{id}")
    public void parameterError(@PathVariable("id") int id) {
    }

    // 잘못된 데이터 바인딩 요청
    @PostMapping("/join")
    public void bindingError(@RequestBody UserRequestDto userRequestDto) {

    }
}
