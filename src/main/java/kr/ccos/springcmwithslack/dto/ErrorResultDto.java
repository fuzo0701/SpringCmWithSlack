package kr.ccos.springcmwithslack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResultDto {

    private HttpStatus httpStatus;
    private String message;
}
