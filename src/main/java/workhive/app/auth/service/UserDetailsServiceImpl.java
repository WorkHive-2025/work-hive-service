package workhive.app.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import workhive.app.auth.entity.MemberRole;
import workhive.app.auth.entity.Role;
import workhive.app.auth.repository.MemberRoleRepository;
import workhive.app.auth.vo.PermissionVo;
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
    private final RolePermissionServiceImpl rolePermissionCacheService;

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

    public boolean checkRequestPermission(List<String> roles, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (requestURI == null || method == null) {
            return false; // 요청 URI 또는 메서드가 null인 경우 권한 거부
        }

        //1. Redis에서 권한 검사
        boolean redisAuthorized = checkAuthorizationByRedis(roles, requestURI, method);
        if (redisAuthorized) {
            return true; // Redis에서 권한 허용
        }

        //2. DB에서 권한 검사
        boolean dbAuthorized = checkAuthorizationByDB(roles, requestURI, method);
        if (dbAuthorized) {
            return true; // DB에서 권한 허용
        }

        return false; // 권한 거부
    }

    public boolean checkAuthorizationByRedis(List<String> roles,
                                             String requestURI,
                                             String method) {

        AntPathMatcher matcher = new AntPathMatcher();

        //1. 요청 URI와 메서드에 대한 권한 검사
        for (String role : roles) {
            List<PermissionVo> permissions = rolePermissionCacheService.getPermissionsByRoleCodeInRedis(role);
            for (PermissionVo permission : permissions) {
                boolean pathMatch = matcher.match(permission.getRequestPath(), requestURI);
                boolean methodMatch = "*".equals(permission.getHttpMethod()) || permission.getHttpMethod().equalsIgnoreCase(method);

                if (pathMatch && methodMatch) {
                    return true; // 권한 허용
                }
            }
        }

        return false; // 권한 거부
    }

    public boolean checkAuthorizationByDB(List<String> roles,
                                           String requestURI,
                                           String method) {

        AntPathMatcher matcher = new AntPathMatcher();

        //1. 요청 URI와 메서드에 대한 권한 검사
        for (String role : roles) {
            List<PermissionVo> permissions = rolePermissionCacheService.getPermissionsByRoleCodeInDB(role);
            for (PermissionVo permission : permissions) {
                boolean pathMatch = matcher.match(permission.getRequestPath(), requestURI);
                boolean methodMatch = "*".equals(permission.getHttpMethod()) || permission.getHttpMethod().equalsIgnoreCase(method);

                if (pathMatch && methodMatch) {
                    return true; // 권한 허용
                }
            }
        }

        return false; // 권한 거부
    }
}
