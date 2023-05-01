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

package kyungseo.poc.framework.auth.jwt.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import kyungseo.poc.framework.auth.jwt.compoenent.LoggedOutJwtTokenCache;
import kyungseo.poc.framework.auth.jwt.event.OnUserLogoutSuccessEvent;
import kyungseo.poc.framework.auth.jwt.payload.request.DeviceInfo;

@Component
public class UserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final LoggedOutJwtTokenCache tokenCache;

    public UserLogoutSuccessEventListener(LoggedOutJwtTokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    /**
     * 사용자 로그아웃 이벤트가 완료되면 backlist 판별을 위하여 tokenCache에 저장
     */
    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
        if (null != event) {
            DeviceInfo deviceInfo = event.getLogOutRequest().getDeviceInfo();
            LOGGER.info(String.format("디바이스 [%s]에 대해 사용자 [%s]에 대해 수신된 로그아웃 성공 이벤트", deviceInfo, event.getUserEmail()));
            tokenCache.markLogoutEventForToken(event); // backlist에 추가
        }
    }

}
