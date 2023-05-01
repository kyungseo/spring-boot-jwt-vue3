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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 인증 처리 과정에서 예외가 발생한 경우 Exception Handling
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        LOGGER.error("사용자가 승인되지 않았습니다. Entry Point(진입점)에서 라우팅 처리함. Message - {}", ex.getMessage());

        if (request.getAttribute("javax.servlet.error.exception") != null) {
            Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
            resolver.resolveException(request, response, null, (Exception) throwable);
        }

        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
    }

    /*
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e)
                             throws IOException, ServletException {
        logger.error("Unauthorized error. Message - {}", e.getMessage());

        // [1] API(ajax) 요청일 경우 상태코드 전송
        if (StringUtils.contains(request.getHeader(HttpHeaders.ACCEPT), MediaType.APPLICATION_JSON_VALUE)) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.flushBuffer();
        // [2] 일반 요청일 경우 세션만료(403) 페이지로 리다이렉트
        } else {
            response.sendRedirect("/view/common/error/403");
        }
    }
    */

}
