package workhive.app.global.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TokenDto(String accessToken,
                       String refreshToken,
                       LocalDateTime accessTokenExpiredAt,
                       LocalDateTime refreshTokenExpiredAt) {
}
