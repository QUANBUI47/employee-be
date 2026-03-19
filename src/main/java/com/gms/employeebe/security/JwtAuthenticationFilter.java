package com.gms.employeebe.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthUserDetailsService authUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            String bearer = request.getHeader("Authorization");
            log.debug("Authorization header: {}", bearer);

            if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
                String token = bearer.substring(7);
                if (jwtUtils.validate(token)) {
                    Long userId = jwtUtils.getUserId(token); // <-- subject = userId
                    log.debug("JWT valid, subject(userId) = {}", userId);

                    UserDetails user = authUserDetailsService.loadUserById(userId);
                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth); // <-- BẮT BUỘC
                } else {
                    log.warn("JWT validate = false");
                }
            } else {
                log.debug("No Bearer token present");
            }
        } catch (Exception ex) {
            log.error("JWT filter error", ex);
        }
        chain.doFilter(request, response);
    }
}