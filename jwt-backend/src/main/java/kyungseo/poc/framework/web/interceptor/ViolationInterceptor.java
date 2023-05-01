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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.exception.ViolationException;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class ViolationInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    // TODO 차후 REDIS로 변경할 것
    private List<String> requestIds = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader(AppConstants.REQUEST_ID);
        if (ObjectUtils.isEmpty(requestId)) return true;
        LOGGER.info("[preHandle][" + requestId + "]");

        if (requestIds.contains(requestId)) {
            //String errorMessage = messages.getMessage("error.req.violation", null, "중복된 요청", Locale.KOREA);
            throw new ViolationException("중복된 요청");
        }

        requestIds.add(requestId);

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

}
