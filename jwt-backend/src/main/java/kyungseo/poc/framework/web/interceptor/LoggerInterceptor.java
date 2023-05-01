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

package kyungseo.poc.framework.web.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import kyungseo.poc.framework.auth.util.SecurityUtil;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class LoggerInterceptor implements HandlerInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/view") || request.getRequestURI().startsWith("/api")) {
            //LOGGER.info("[" + request.getMethod() + "]" + request.getRequestURI() + getParameters(request));
            LOGGER.info("[" + request.getMethod() + "]" + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        //LOGGER.info("[postHandle][" + request + "]");
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        if (ex != null) {
            ex.printStackTrace();
        }
        //LOGGER.info("[afterCompletion][" + request + "][exception: " + ex + "]");
    }

    private String getParameters(final HttpServletRequest request) {
        final StringBuffer posted = new StringBuffer();
        final Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }

        while (e != null && e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            final String curr = (String) e.nextElement();
            posted.append(curr).append("=");

            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }

        final String ip = request.getHeader(SecurityUtil.X_FORWARDED_FOR);
        final String ipAddr = (ip == null) ? SecurityUtil.getClientIP(request) : ip;
        if (!Strings.isNullOrEmpty(ipAddr)) {
            posted.append("&_psip=" + ipAddr);
        }

        return posted.toString();
    }

}
