package workhive.app.member.service;

import workhive.app.member.dto.request.CreateMemberRequestDto;
import workhive.app.member.dto.request.UpdateMemberRequestDto;
import workhive.app.member.dto.response.GetMemberResponseDto;
import workhive.app.member.entity.Member;

public interface MemberService {

    /**
     * 회원 ID로 회원 조회
     *
     * @param memberId 회원 ID
     * @return 회원 정보
     */
    Member findMemberById(Long memberId);

    /**
     * 회원 username으로 회원 조회
     *
     * @param username 회원 username
     * @return 회원 정보
     */
    Member findMemberByUsername(String username);

    /**
     * 회원 ID로 회원 조회
     *
     * @param memberId 회원 ID
     * @return 회원 정보
     */
    GetMemberResponseDto getMember(Long memberId);

    /**
     * 회원 정보 생성
     *
     * @param requestDto 회원 생성 요청 DTO
     */
    void saveMember(CreateMemberRequestDto requestDto);

    /**
     * 회원 정보 수정
     *
     * @param requestDto 회원 정보 수정 요청 DTO
     */
    void updateMember(UpdateMemberRequestDto requestDto);

}
