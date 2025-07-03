package com.example.DongNaeJoGak.global.security.filter;

import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.global.security.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter  {

    public final JwtTokenUtil jwtTokenUtil;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);

        if (token != null && jwtTokenUtil.isValidateToken(token)) {
            Long userId = jwtTokenUtil.getUserIdFromToken(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length());
        }
        return null;
    }

    // 인증 객체를 만들어서 이후 spring security가 사용자의 인증 상태를 판단하는데 사용
    private Authentication createAuthentication(Member member) {
        return UsernamePasswordAuthenticationToken.authenticated(
                member,         // 사용자
                null,           // 비밀번호 등 인증 수단은 Null (이미 인증된 상태이기에)
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));    // 권한 부여
    }

    // 인증된 사용자 정보를 securityContext에 저장
    // 저장된 후에는 @AuthenticationPrincipal 로 꺼내서 사용 가능
    private void setAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
        securityContext.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
    }

}
