package workhive.app.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import workhive.app.global.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        //white list
        MvcRequestMatcher[] permits = {
                mvc.pattern("/api/login"),
                mvc.pattern("/api/signup"),
                mvc.pattern("/api/refresh"),
                mvc.pattern("/api/logout")
        };

        // 1. 로그인 폼 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // 2. 로그아웃 비활성화
        http.logout(AbstractHttpConfigurer::disable);

        // 3. CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 4. 세션 미사용
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 5. filter 설정
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // 6. 예외 처리 설정
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );

        // 7. 인증 및 인가 요청 설정
        http.authorizeHttpRequests(authentication -> authentication
                .requestMatchers(permits).permitAll()
                //.anyRequest().access(customAuthorizationManager)  // -> 개발 중 비활성화
                .anyRequest().permitAll()
        );

        return http.build();
    }


}
