package workhive.app.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import workhive.app.global.response.ApiSuccessResponse;
import workhive.app.member.dto.request.CreateMemberRequestDto;
import workhive.app.member.dto.request.UpdateMemberRequestDto;
import workhive.app.member.dto.response.GetMemberResponseDto;
import workhive.app.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/get/{id}")
    public ApiSuccessResponse<GetMemberResponseDto> findMemberById(@PathVariable("id") Long memberId) {
        // 1. 해당 회원 ID로 Member Response Dto 리턴
        GetMemberResponseDto memberResponseDto = memberService.getMember(memberId);

        // 2. ApiSuccessResponse 리턴
        return new ApiSuccessResponse<>(memberResponseDto);
    }

    @PostMapping("/save")
    public ApiSuccessResponse<Void> saveMember(@Valid @RequestBody CreateMemberRequestDto requestDto) {
        // 1. Member 정보 생성
        memberService.saveMember(requestDto);

        // 2. ApiSuccessResponse 리턴
        return new ApiSuccessResponse<>();
    }

    @PatchMapping("/udpate")
    public ApiSuccessResponse<Void> updateMember(@Valid @RequestBody UpdateMemberRequestDto requestDto) {
        // 1. Member 정보 업데이트
        memberService.updateMember(requestDto);

        // 2. ApiSuccessResponse 리턴
        return new ApiSuccessResponse<>();
    }

}
