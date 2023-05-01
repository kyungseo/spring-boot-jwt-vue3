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

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kyungseo.poc.framework.auth.jwt.event.OnUserLogoutSuccessEvent;
import net.jodah.expiringmap.ExpiringMap;

/**
 * 로그아웃 요청 시 아직 만료되지 않은 Token을
 * Token의 TTL 만료 시점까지 남은 시간(초) 만큼 Cache 하는 로직을 구현
 */
@Component
public class LoggedOutJwtTokenCache {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    // https://github.com/jhalterman/expiringmap
    private ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;

    private JwtTokenProvider tokenProvider;

    public LoggedOutJwtTokenCache(@Value("${token.cache.logoutToken.maxSize}") int maxSize, @Lazy JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(maxSize)
                .build();
    }

    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
        String token = event.getToken();

        if (tokenEventMap.containsKey(token)) {
            LOGGER.info(String.format("사용자 [%s]의 Logout token이 이미 캐시에 존재합니다.", event.getUserEmail()));
        }
        else {
            Date tokenExpiryDate = tokenProvider.getTokenExpiryFromJWT(token);
            long ttlForToken = getTTLForToken(tokenExpiryDate);
            LOGGER.info(String.format("사용자 [%s]에 대한 Logout token이 캐시에 저장([%s] 초의 TTL)되었습니다. Token은 [%s]에 만료됩니다.", event.getUserEmail(), ttlForToken, tokenExpiryDate));
            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
        }
    }

    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
        return tokenEventMap.get(token);
    }

    private long getTTLForToken(Date date) {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }

}
