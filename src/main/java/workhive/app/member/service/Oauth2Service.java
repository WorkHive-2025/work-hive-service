package workhive.app.member.service;

import workhive.app.member.dto.request.CreateOauth2RequestDto;
import workhive.app.member.entity.Oauth2;

public interface Oauth2Service {

    /**
     * 회원 ID로 Oauth2 조회
     *
     * @param memberId 회원 ID
     */
    Oauth2 findOauth2ByMemberId(Long memberId);

    /**
     * Oauth2 플랫폼과 키로 Oauth2 조회
     *
     * @param platform Oauth2 플랫폼
     * @param key Oauth2 키
     */
    Oauth2 findOauth2ByPlatformAndKey(String platform, String key);

    /**
     * Oauth2 정보 생성
     *
     * @param requestDto Oauth2 생성 요청 DTO
     */
    void saveOauth2(CreateOauth2RequestDto requestDto);
}
