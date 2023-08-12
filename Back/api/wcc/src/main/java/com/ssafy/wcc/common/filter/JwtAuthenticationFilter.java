package com.ssafy.wcc.common.filter;

import com.ssafy.wcc.common.exception.Error;
import com.ssafy.wcc.domain.jwt.application.service.TokenService;
import com.ssafy.wcc.domain.member.application.service.MemberServiceImpl;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("memberInfoResponse service 진입");

        if("OPTIONS".equalsIgnoreCase(((HttpServletRequest)request).getMethod())) {
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }
        // 헤더에서 accessToken을 받아옵니다.
        String token = tokenService.resolveToken((HttpServletRequest) request);

        Enumeration<String> headerNames = ((HttpServletRequest) request).getHeaderNames();
        StringBuilder headers = new StringBuilder();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = ((HttpServletRequest) request).getHeader(headerName);
            headers.append(headerName).append(": ").append(headerValue).append("\n");
        }

        logger.info(headers.toString());
        logger.info("대문자"+((HttpServletRequest) request).getHeader("Access-Token"));
        // 유효한 토큰인지 확인합니다.
        if (token != null && tokenService.checkToken(token)) {
            logger.info("유효한 토큰");
            String s = tokenService.getToken(token);
            if (s == null) {
                logger.info("Black List에 등록되지 않은 토큰");
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = tokenService.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }



        chain.doFilter(request, response);
    }
}
