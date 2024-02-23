package com.app.api.exception;

import com.app.api.exception.dto.ExceptionTestDto;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.BusinessException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class ExceptionTestController {

    @GetMapping("/exception-test/bindresult")
    public String exceptionTestBindResult(@Validated ExceptionTestDto exceptionTestDto) {
        return "exception-test/bindresult";
    }

    @GetMapping("/exception-test/bindresult11")
    public ResponseEntity<?> exceptionTestBindResult11(@RequestParam(required = false) int value) {

        return ResponseEntity.ok("exception-test/bindresult0");
    }


    @GetMapping("/exception-test/bindresult0")
    public ResponseEntity<?> exceptionTestBindResult0(String ex) {
        if (ex != null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return ResponseEntity.ok("exception-test/bindresult0");
    }

    @GetMapping("/exception-test/bindresult1")
    public ResponseEntity<?> exceptionTestBindResult1(
            @RequestParam @NotBlank(message = "Username is mandatory") String username,
            @RequestParam @Max(value = 10, message = "Age must be at most 10") int age) {

        // 유효성 검사를 통과한 경우 정상적인 응답 반환
        return ResponseEntity.ok("exception-test/bindresult1");
    }
    @PostMapping("/exception-test/bindresult2")
    public String exceptionTestBindResult2(@Validated @RequestBody ExceptionTestDto exceptionTestDto) {
        return "exception-test/bindresult2";
    }

    @PostMapping("/exception-test/bindresult3")
    public ResponseEntity<?> exceptionTestBindResult3(@Validated @RequestBody ExceptionTestDto exceptionTestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 에러가 있을 경우 에러 정보를 반환
            return ResponseEntity.badRequest().body(bindingResult);
        }
        // 에러가 없을 경우 성공 메시지 반환
        return ResponseEntity.ok("exception-test/bindresult3");
    }

    @PostMapping("/exception-test/bindresult4")
    public ResponseEntity<ExceptionTestDto> exceptionTestBindResult4(@Validated @RequestBody ExceptionTestDto exceptionTestDto) {
        return ResponseEntity.ok(exceptionTestDto);
    }
}
