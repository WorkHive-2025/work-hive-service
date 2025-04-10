package workhive.app.global.manager;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationManagerImpl implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        Authentication auth = authentication.get();

        // 인증이 안 되어 있으면 거부
        if (auth == null || !auth.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        //TODO : 권한 검사 로직 추가
        // 예시: 특정 URL에 대한 권한 검사
        // boolean result = checkPermission(request, auth);
        // return new AuthorizationDecision(result);

        return new AuthorizationDecision(true);
    }
}
