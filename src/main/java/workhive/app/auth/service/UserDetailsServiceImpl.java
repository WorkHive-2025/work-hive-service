package workhive.app.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import workhive.app.auth.entity.MemberRole;
import workhive.app.auth.entity.Role;
import workhive.app.auth.repository.MemberRoleRepository;
import workhive.app.auth.vo.UserDetailsImpl;
import workhive.app.member.entity.Member;
import workhive.app.member.service.MemberService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberService memberService;
    private final MemberRoleRepository memberRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. username으로 Member 조회
        Member member = memberService.findMemberByUsername(username);

        //2. Member가 존재할 경우 MemberRole 목록 조회
        List<MemberRole> memberRoles = memberRoleRepository.findByMemberId(member.getId());

        //3. MemberRole 목록에서 Role 이름을 추출하여 List<String>으로 변환
        List<String> roles = memberRoles.stream()
                .map(MemberRole::getRole)
                .map(Role::getRoleCode)
                .toList();

        //4. UserDetailsImpl 객체 생성 및 반환
        return new UserDetailsImpl(member, roles);
    }
}
