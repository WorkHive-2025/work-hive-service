package workhive.app.member.service;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import workhive.app.exception.GeneralException;
import workhive.app.exception.enums.ErrorCode;
import workhive.app.member.dto.request.CreateMemberRequestDto;
import workhive.app.member.dto.request.UpdateMemberRequestDto;
import workhive.app.member.dto.response.GetMemberResponseDto;
import workhive.app.member.entity.Member;
import workhive.app.member.repository.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long memberId) {
        // 1. 해당 회원 ID로 Member 정보를 리턴
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("MemberService.findMemberById - Member not found with id: {}", memberId);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "존재하지 않는 회원입니다.");
                });
    }

    @Override
    public Member findMemberByUsername(String username) {
        // 1. 해당 회원 Username 으로 Member 정보를 리턴
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("MemberService.findMemberByUsername - Member not found with username: {}", username);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "존재하지 않는 회원입니다.");
                });
    }

    @Override
    public GetMemberResponseDto getMember(Long memberId) {
        // 1. 해당 회원 ID로 Member Response Dto 리턴
        return memberRepository.findById(memberId)
                .map(GetMemberResponseDto::fromEntity)
                .orElseThrow(() -> {
                    log.error("MemberService.getMember - Member not found with id: {}", memberId);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "존재하지 않는 회원입니다.");
                });
    }

    @Override
    public void saveMember(CreateMemberRequestDto requestDto) {
        // 1. 해당 회원 ID로 Member 정보가 이미 존재하는지 확인
        if (memberRepository.existsByUsername(requestDto.getUsername())) {
            log.error("MemberService.saveMember - Member already exists with username: {}", requestDto.getUsername());
            throw new GeneralException(ErrorCode.DUPLICATE_PARAMETER_ERROR, "이미 존재하는 회원입니다.");
        }

        // 2. Member 정보 생성
        memberRepository.save(requestDto.toEntity());
    }

    @Override
    public void updateMember(UpdateMemberRequestDto requestDto) {
        // 1. 해당 회원 ID로 Member 정보가 존재하는지 확인
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> {
                    log.error("MemberService.updateMember - Member not found with id: {}", requestDto.getMemberId());
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "존재하지 않는 회원입니다.");
                });

        // 2. Member 정보 업데이트
        memberRepository.save(requestDto.update(member));
    }
}
