package workhive.app.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import workhive.app.exception.GeneralException;
import workhive.app.exception.enums.ErrorCode;
import workhive.app.global.utils.UniqueKeyGenerator;
import workhive.app.member.dto.request.CreateOauth2RequestDto;
import workhive.app.member.entity.Member;
import workhive.app.member.entity.Oauth2;
import workhive.app.member.repository.Oauth2Repository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2ServiceImpl implements Oauth2Service {

    private final Oauth2Repository oauth2Repository;
    private final UniqueKeyGenerator uniqueKeyGenerator;

    @Override
    public Oauth2 findOauth2ByMemberId(Long memberId) {
        // 1. 해당 회원 ID로 Oauth2 정보를 리턴
        return oauth2Repository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    log.error("Oauth2Service.findOauth2ByMemberId - Oauth2 not found with memberId: {}", memberId);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "존재하지 않는 회원입니다.");
                });
    }

    @Override
    public Oauth2 findOauth2ByPlatformAndKey(String platform, String key) {
        // 1. 해당 플랫폼과 키로 Oauth2 정보를 리턴
        return oauth2Repository.findByPlatformAndKey(platform, key)
                .orElseThrow(() -> {
                    log.error("Oauth2Service.findOauth2ByPlatformAndKey - Oauth2 not found with platform: {}, key: {}", platform, key);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "존재하지 않는 회원입니다.");
                });
    }

    @Override
    public void saveOauth2(CreateOauth2RequestDto requestDto) {
        // 1. 해당 플랫폼과 키로 Oauth2가 이미 존재하는지 확인
        if (oauth2Repository.existsByPlatformAndKey(requestDto.getPlatform(), requestDto.getKey())) {
            log.error("Oauth2Service.saveOauth2 - Oauth2 already exists with platform: {}, key: {}", requestDto.getPlatform(), requestDto.getKey());
            throw new GeneralException(ErrorCode.DUPLICATE_PARAMETER_ERROR, "이미 존재하는 회원입니다.");
        }

        // 2. Oauth2 정보 생성
        Member member = Member.builder()
                .username(uniqueKeyGenerator.generateUniqueKey())
                .build();
        oauth2Repository.save(requestDto.toEntity(member));
    }
}
