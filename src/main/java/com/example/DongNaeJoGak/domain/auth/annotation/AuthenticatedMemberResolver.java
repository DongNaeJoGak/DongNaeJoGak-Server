package com.example.DongNaeJoGak.domain.auth.annotation;

import com.example.DongNaeJoGak.domain.member.entity.Member;
import com.example.DongNaeJoGak.global.apiPayload.code.status.error.TokenErrorStatus;
import com.example.DongNaeJoGak.global.apiPayload.exception.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
// controller 메서드에서 @AuthenticatedMember가 붙은 파라미터 처리
public class AuthenticatedMemberResolver implements HandlerMethodArgumentResolver {

    private final SecurityContextRepository securityContextRepository;

    // 어떤 파라미터를 처리할지 결정
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class) && parameter.getParameterType().equals(Member.class);
    }

    // 어떻게 값을 꺼낼지 결정
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // secuirtyContext에서 인증정보 꺼냄
        Authentication authentication = securityContextRepository.loadDeferredContext(webRequest.getNativeRequest(HttpServletRequest.class)).get().getAuthentication();

        // 인증 안 된 사용자인 경우
        if (authentication == null || !(authentication.getPrincipal() instanceof Member)) {
            throw new TokenException(TokenErrorStatus.INVALID_TOKEN);
        }

        return authentication.getPrincipal();
    }
}
