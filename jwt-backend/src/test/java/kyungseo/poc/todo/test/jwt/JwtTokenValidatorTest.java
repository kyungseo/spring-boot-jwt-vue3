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

package kyungseo.poc.todo.test.jwt;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtTokenProvider;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtTokenValidator;
import kyungseo.poc.framework.auth.jwt.compoenent.LoggedOutJwtTokenCache;
import kyungseo.poc.framework.auth.jwt.event.OnUserLogoutSuccessEvent;

class JwtTokenValidatorTest {

    private static final String jwtSecret = "kyungseo-poc-secret";
    private static final long jwtExpiryInMs = 25000;
    private static final long jwtRefreshExpirationInMs = 25000;
    private static final String jwtCookie = "kyungseo-poc-jwt";
    private static final String jwtRefreshCookie = "kyungseo-poc-jwt-refresh";
    private static final String jwtIssuer = "Kyungseo.Park@gmail.com";

    @Mock
    private LoggedOutJwtTokenCache loggedOutTokenCache;

    private JwtTokenProvider tokenProvider;

    private JwtTokenValidator tokenValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.tokenProvider = new JwtTokenProvider(
                jwtSecret, jwtExpiryInMs, jwtRefreshExpirationInMs, jwtCookie, jwtRefreshCookie, jwtIssuer);
        this.tokenValidator = new JwtTokenValidator(jwtSecret, loggedOutTokenCache);
    }

    @Test
    void testValidateTokenThrowsExceptionWhenTokenIsDamaged() {
        String token = tokenProvider.generateTokenFromUserId(100L);
        OnUserLogoutSuccessEvent logoutEvent = stubLogoutEvent("U1", token);
        when(loggedOutTokenCache.getLogoutEventForToken(token)).thenReturn(logoutEvent);

        InvalidTokenRequestException ex = assertThrows(InvalidTokenRequestException.class,
                () -> tokenValidator.validateToken(token + "-Damage"));
        assertTrue(ex.getMessage().contains("Incorrect signature"));
    }

    @Test
    void testValidateTokenThrowsExceptionWhenTokenIsExpired() throws InterruptedException {
        String token = tokenProvider.generateTokenFromUserId(123L);
        TimeUnit.MILLISECONDS.sleep(jwtExpiryInMs);
        OnUserLogoutSuccessEvent logoutEvent = stubLogoutEvent("U1", token);
        when(loggedOutTokenCache.getLogoutEventForToken(token)).thenReturn(logoutEvent);

        InvalidTokenRequestException ex = assertThrows(InvalidTokenRequestException.class,
                () -> tokenValidator.validateToken(token));
        assertTrue(ex.getMessage().contains("Token이 만료됨. 갱신 필요"));
    }

    @Test
    void testValidateTokenThrowsExceptionWhenItIsPresentInTokenCache() {
        String token = tokenProvider.generateTokenFromUserId(124L);
        OnUserLogoutSuccessEvent logoutEvent = stubLogoutEvent("U2", token);
        when(loggedOutTokenCache.getLogoutEventForToken(token)).thenReturn(logoutEvent);

        InvalidTokenRequestException ex = assertThrows(InvalidTokenRequestException.class,
                () -> tokenValidator.validateToken(token));
        assertTrue(ex.getMessage().contains("이미 로그아웃 하였습니다."));
    }

    @Test
    void testValidateTokenWorksWhenItIsNotPresentInTokenCache() {
        String token = tokenProvider.generateTokenFromUserId(100L);
        tokenValidator.validateToken(token);
        verify(loggedOutTokenCache, times(1)).getLogoutEventForToken(token);
    }

    private OnUserLogoutSuccessEvent stubLogoutEvent(String email, String token) {
        return new OnUserLogoutSuccessEvent(email, token, null);
    }

}
