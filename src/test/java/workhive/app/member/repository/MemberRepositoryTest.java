package workhive.app.member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import workhive.app.global.config.JpaAuditingConfig;
import workhive.app.global.config.ObjectMapperConfig;
import workhive.app.global.config.QueryDSLConfig;
import workhive.app.member.entity.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@Import({
        QueryDSLConfig.class,
        JpaAuditingConfig.class,
        ObjectMapperConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member initMember;

    @BeforeEach
    void setup() {
        initMember = Member.builder()
                .username("testUser")
                .password("testPassword")
                .name("Test User")
                .build();
    }

    @Test
    @DisplayName("Member 저장 성공")
    void givenMember_whenSave_thenSuccess() {
        // given
        Member member = initMember;

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertNotNull(savedMember.getId());
        assertEquals(member.getUsername(), savedMember.getUsername());
        assertEquals(member.getName(), savedMember.getName());
    }


}