package com.umc.refit.web.config;

import com.nimbusds.jose.jwk.RSAKey;
import com.umc.refit.web.filter.authentication.CustomUserDetailsService;
import com.umc.refit.web.filter.authentication.JwtAuthenticationFilter;
import com.umc.refit.web.filter.authentication.JwtKakaoAuthenticationFilter;
import com.umc.refit.web.filter.entrypoint.CustomAuthenticationEntryPoint;
import com.umc.refit.web.service.MemberService;
import com.umc.refit.web.signature.RSASecuritySigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuth2ResourceServer {

    @Autowired
    private RSASecuritySigner rsaSecuritySigner;

    @Autowired
    private RSAKey rsaKey;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private MemberService memberService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //세션을 사용하지 않음
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //인증을 거치지 않을 URL 처리 및 인증, 인가 예외 EntryPoint 등록
        http.authorizeRequests((requests) ->
                requests.antMatchers("/auth/logout", "/auth/join").permitAll()
                .anyRequest().authenticated())
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //사용자 정보 로드해서 객체 생성
        http.userDetailsService(userDetailsService);

        //일반 로그인 URL 설정
        JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(http, rsaSecuritySigner, rsaKey, memberService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        //카카오 로그인 URL 설정
        JwtKakaoAuthenticationFilter jwtKakaoAuthenticationFilter =
                new JwtKakaoAuthenticationFilter(http, rsaSecuritySigner, rsaKey, memberService);
        jwtKakaoAuthenticationFilter.setFilterProcessesUrl("/auth/kakao");

        return http.build();
    }

    //패스워드 인코드 하지 않음
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
