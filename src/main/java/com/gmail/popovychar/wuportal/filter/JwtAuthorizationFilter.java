package com.gmail.popovychar.wuportal.filter;

import com.gmail.popovychar.wuportal.utility.JWTTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.gmail.popovychar.wuportal.constant.SecurityConstant.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

//filter to authorizer any request/it does once
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JWTTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) { //if its option ,it will go thru (option send before any req
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION); //we take authHeader
            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) { //!check if authHeader doesnt start with
                filterChain.doFilter(request, response); //passing the req res
                return;
            }
            //get header and remove header token
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token); //getting a username
            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) { //checks if req process done for them
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request); //once we get auth
                SecurityContextHolder.getContext().setAuthentication(authentication); //we set in securityContext
            } else {
                SecurityContextHolder.clearContext(); //if the rest fails it make sure its clear for user
            }
        }
        filterChain.doFilter(request, response);
    }
}
