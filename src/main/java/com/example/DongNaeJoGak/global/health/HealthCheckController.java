package com.example.DongNaeJoGak.global.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "헬스 체크 API", description = "서버 상태 확인용 API")
public class HealthCheckController {

    @Operation(
            summary = "헬스 체크",
            description = "서버가 정상적으로 실행 중인지 간단한 OK 응답을 반환합니다."
    )
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

}
