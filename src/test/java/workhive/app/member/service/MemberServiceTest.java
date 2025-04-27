package workhive.app.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import workhive.app.member.entity.Member;
import workhive.app.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원 username 으로 회원 조회 성공")
    void givenUsername_whenFindMemberByUsername_thenSuccess() {
        // given
        String username = "testUser";
        Member member = Member.builder()
                .username("testUser")
                .password("testPassword")
                .name("Test User")
                .build();

        given(memberRepository.findByUsername(anyString())).willReturn(Optional.of(member));

        // when
        Member findMemberByUsername = memberService.findMemberByUsername(username);

        // then
        assertThat(findMemberByUsername.getUsername()).isEqualTo("testUser");
        assertThat(findMemberByUsername.getName()).isEqualTo("Test User");
    }

}