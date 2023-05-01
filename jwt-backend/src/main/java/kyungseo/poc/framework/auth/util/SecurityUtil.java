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

package kyungseo.poc.framework.auth.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import kyungseo.poc.framework.auth.core.persistence.entity.User;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class SecurityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";

    private SecurityUtil() {
        //
    }

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()
                .equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }

    private static Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return authentication;
    }

    public static User getCurrentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    public static String getCurrentUsername() {
        return getAuthentication().getName();
    }

    public static String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getClientIP(final HttpServletRequest request) {
        final String xfHeader = request.getHeader(X_FORWARDED_FOR);
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            //LOGGER.debug("getClientIP > getRemoteAddr: " + request.getRemoteAddr());
            return request.getRemoteAddr();
        }
        //LOGGER.debug("getClientIP > getHeader(\"X-FORWARDED-FOR\"): " + xfHeader.split(",")[0]);
        return xfHeader.split(",")[0];
        // return "128.101.101.101"; // for testing 미국
    }

}