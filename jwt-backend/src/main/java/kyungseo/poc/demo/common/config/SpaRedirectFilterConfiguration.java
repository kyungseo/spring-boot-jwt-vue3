/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
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

package kyungseo.poc.demo.common.config;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Configuration
public class SpaRedirectFilterConfiguration {

    private final Logger LOGGER = LoggerFactory.getLogger(SpaRedirectFilterConfiguration.class);

    private final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/h2/**",
            "/actuator/*"
    };

    @Bean
    public FilterRegistrationBean spaRedirectFiler() {
        FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(createRedirectFilter());
        registration.addUrlPatterns("/*");
        registration.setName("frontendRedirectFilter");
        registration.setOrder(1);

        return registration;
    }

    private OncePerRequestFilter createRedirectFilter() {
        return new OncePerRequestFilter() {
            // 다음 경로들을 제외하고 모든 경로를 Forward
            private final String REGEX =
                    "(?!/configuration|/api-docs|/v3/api-docs|/swagger-resources|/configuration|/swagger-ui|/swagger-ui\\.html" +
                    "|/h2|/actuator|/api|/_nuxt|/static|/index\\.html|/200\\.html|/favicon\\.ico|/sw\\.js).*$";
            private Pattern pattern = Pattern.compile(REGEX);

            @Override
            protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
                if (pattern.matcher(req.getRequestURI()).matches() && !req.getRequestURI().equals("/")) {
                    // pattern이 일치하고 '/'이 아닌 경우 '/'로 Delegate/Forward
                    // Vue 기반의 frontend 라우팅에서 ''mode: history'를 사용하기 때문...
                    LOGGER.info("URL {}이 Browser에 직접 입력, redirecting...", req.getRequestURI());
                    RequestDispatcher rd = req.getRequestDispatcher("/");
                    rd.forward(req, res);
                } else {
                    chain.doFilter(req, res);
                }
            }
        };
    }

}
