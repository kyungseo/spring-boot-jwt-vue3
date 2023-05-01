/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 *
 * Original Code: https://github.com/isopropylcyanide/Jwt-Spring-Security-JPA
 * modified by Kyungseo Park
 * ----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================= */

package kyungseo.poc.framework.auth.jwt.compoenent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.auth.jwt.service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String[] NO_FILTER_URI = {
        AppConstants.API_JWT_URI_PREFIX + "/auth/login",
        AppConstants.API_JWT_URI_PREFIX + "/auth/refresh"
    };

    @Value("${token.jwt.header}")
    private String tokenRequestHeader; // "Authorization"

    @Value("${token.jwt.header.prefix}")
    private String tokenRequestHeaderPrefix; // "Bearer" (space 없음!)

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            boolean isNoFilterURI = Arrays.asList(NO_FILTER_URI).contains(request.getRequestURI());
            String accessToken = getJwtFromRequest(request);

            //LOGGER.debug("doFilterInternal \n\n" +
            //        "\trequestURI: " + request.getRequestURI() + "\n" +
            //        "\tisNoFiterURI: " + isNoFilterURI + "\n" +
            //        "\taccessToken: " + accessToken + "\n");

            // 1. Access Token이 유효한 지 체크
            // 2. 추가적으로 validateToken(accessToken) 메서드 내부에서 accessToken이 blacklist에 있는지 여부도 함께 확인
            if (!isNoFilterURI && StringUtils.hasText(accessToken) && jwtTokenValidator.validateToken(accessToken)) {
                Long userId = jwtTokenProvider.getUserIdFromJWT(accessToken);
                LOGGER.info(userId + " 사용자를 위한 Authentication 설정");

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesFromJWT(accessToken);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, accessToken, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 Authentication을 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception ex) {
            LOGGER.error("Security Context에 사용자 인증을 설정하지 못했습니다. - ", ex);
            throw ex;
        }

        filterChain.doFilter(request, response);
    }

    // Authorization request header에서 Access Token 추출
    //   ex) "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyNUBraWNvLmNvLmtyIiwiaXNzIjoiS3l1bmdzZW8uUGFya0BnbWFpbC5jb20iLCJqdGkiOiI1IiwiaWF0IjoxNjc2MTg2MzU1LCJleHAiOjE2NzYxODk5NTV9.dQJDFFE31mMjfjHThwPah7mnV2Mglh4G_3QCj6690Cc"
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenRequestHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix + " ")) {
            LOGGER.info("Extracted Token: " + bearerToken);
            return bearerToken.replace(tokenRequestHeaderPrefix + " ", "");
        }
        return null;
    }

}
