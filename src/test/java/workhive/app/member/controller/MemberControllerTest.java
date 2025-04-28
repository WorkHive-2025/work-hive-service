package workhive.app.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import workhive.app.global.filter.JwtAuthFilter;
import workhive.app.member.dto.response.GetMemberResponseDto;
import workhive.app.member.service.MemberService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthFilter.class)
        }
)
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("GET / memberId 로 회원 조회 성공")
    @WithMockUser
    void givenMemberId_whenFindMemberById_thenSuccess() throws Exception {
        // given
        Long memberId = 1L;
        GetMemberResponseDto memberResponseDto = GetMemberResponseDto.builder()
                .memberId(1L)
                .username("test")
                .name("testuser")
                .isActive(true)
                .build();
        given(memberService.getMember(anyLong())).willReturn(memberResponseDto);

        // when&then
        mvc.perform(
                get("/api/members/get/{memberId}", memberId)
                        .content(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.memberId").value(memberResponseDto.getMemberId()))
                .andExpect(jsonPath("$.data.username").value(memberResponseDto.getUsername()))
                .andExpect(jsonPath("$.data.name").value(memberResponseDto.getName()));
    }
}