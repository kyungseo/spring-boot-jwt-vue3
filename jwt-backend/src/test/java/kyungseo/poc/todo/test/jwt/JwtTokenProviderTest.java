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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import kyungseo.poc.framework.auth.core.model.RoleName;
import kyungseo.poc.framework.auth.core.persistence.entity.Role;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtTokenProvider;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;

public class JwtTokenProviderTest {

    private static final String jwtSecret = "kyungseo-poc-secret";
    private static final long jwtExpiryInMs = 25000;
    private static final long jwtRefreshExpirationInMs = 25000;
    private static final String jwtCookie = "kyungseo-poc-jwt";
    private static final String jwtRefreshCookie = "kyungseo-poc-jwt-refresh";
    private static final String jwtIssuer = "Kyungseo.Park@gmail.com";

    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.tokenProvider = new JwtTokenProvider(
                jwtSecret, jwtExpiryInMs, jwtRefreshExpirationInMs, jwtCookie, jwtRefreshCookie, jwtIssuer);
    }

    @Test
    void testGetUserIdFromJWT() {
        String token = tokenProvider.generateToken(stubCustomUser());
        assertEquals(100, tokenProvider.getUserIdFromJWT(token).longValue());
    }

    @Test
    void testGetTokenExpiryFromJWT() {
        String token = tokenProvider.generateTokenFromUserId(120L);
        assertNotNull(tokenProvider.getTokenExpiryFromJWT(token));
    }

    @Test
    void testGetExpiryDuration() {
        assertEquals(jwtExpiryInMs, tokenProvider.getExpiryDuration());
    }

    @Test
    void testGetAuthoritiesFromJWT() {
        String token = tokenProvider.generateToken(stubCustomUser());
        assertNotNull(tokenProvider.getAuthoritiesFromJWT(token));
    }

    private CustomUserDetails stubCustomUser() {
        User user = new User();
        user.setId((long) 100);
        user.setRoles(Collections.singleton(new Role(RoleName.ROLE_ADMIN)));
        return new CustomUserDetails(user);
    }

}
