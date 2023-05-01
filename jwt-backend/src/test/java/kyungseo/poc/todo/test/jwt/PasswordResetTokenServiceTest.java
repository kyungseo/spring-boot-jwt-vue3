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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.ImmutableList;

import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.core.persistence.entity.PasswordResetToken;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.repository.PasswordResetTokenRepository;
import kyungseo.poc.framework.auth.jwt.payload.request.PasswordResetRequest;
import kyungseo.poc.framework.auth.jwt.service.PasswordResetTokenService;
import kyungseo.poc.framework.exception.ResourceNotFoundException;

public class PasswordResetTokenServiceTest {

    @Mock
    private PasswordResetTokenRepository repository;

    private PasswordResetTokenService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.service = new PasswordResetTokenService(repository);
        ReflectionTestUtils.setField(service, "expiration", 100L);
    }

    @Test
    void testGetValidTokenWhenTokenDoesNotExist() {
        User user = new User();
        PasswordResetRequest request = new PasswordResetRequest();
        request.setToken("T1");

        //doReturn(Optional.empty()).when(repository).findByToken("T1");
        doReturn(null).when(repository).findByToken("T1");
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getValidToken(request));

        assertTrue(ex.getMessage().contains("T1"));
        verify(repository, times(1)).findByToken("T1");
    }

    @Test
    void testGetValidTokenWhenTokenExists() {
        User user = new User();
        user.setEmail("E1");
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("E1");
        request.setToken("T1");

        PasswordResetToken token = service.createTokenWithUser(user);
        //doReturn(Optional.of(token)).when(repository).findByToken("T1");
        doReturn(token).when(repository).findByToken("T1");
        PasswordResetToken validToken = service.getValidToken(request);

        assertEquals(token, validToken);
        assertEquals(user, validToken.getUser());
        verify(repository, times(1)).findByToken("T1");
    }

    @Test
    void testCreateToken() {
        User user = new User();
        doAnswer(invocation -> invocation.getArguments()[0]).when(repository).save(any());
        Optional<PasswordResetToken> tokenOpt = service.createToken(user);
        assertTrue(tokenOpt.isPresent());

        PasswordResetToken token = tokenOpt.get();
        verify(repository, times(1)).save(token);

        assertTrue(token.getExpiryDate().toInstant().isAfter(Instant.now()));
        assertFalse(token.getToken().isEmpty());
        assertFalse(token.getClaimed());
        assertTrue(token.getActive());
        assertEquals(user, token.getUser());
    }

    @Test
    void testClaimToken() {
        User user = new User();
        PasswordResetToken unusedTokenA = service.createTokenWithUser(user);
        PasswordResetToken unusedTokenB = service.createTokenWithUser(user);
        PasswordResetToken unusedTokenC = service.createTokenWithUser(user);

        when(repository.findActiveTokensForUser(user)).thenReturn(ImmutableList.of(
                unusedTokenA, unusedTokenB, unusedTokenC
        ));

        // we are using tokenB, hence it should be marked as "claimed"
        PasswordResetToken claimedToken = service.claimToken(unusedTokenB);
        verify(repository, times(1)).findActiveTokensForUser(user);
        assertEquals(unusedTokenB, claimedToken);
        assertEquals(user, claimedToken.getUser());

        // only token B is claimed
        assertFalse(unusedTokenA.getClaimed());
        assertTrue(unusedTokenB.getClaimed());
        assertFalse(unusedTokenC.getClaimed());

        // all tokens should be marked inactive regardless of claimed/unclaimed
        assertFalse(unusedTokenA.getActive());
        assertFalse(claimedToken.getActive());
        assertFalse(unusedTokenC.getActive());
    }

    @Test
    void testVerifyExpirationWhenTokenIsExpired() {
        PasswordResetToken token = mock(PasswordResetToken.class, Mockito.RETURNS_DEEP_STUBS);
        when(token.getExpiryDate()).thenReturn(Date.from(Instant.now().minusSeconds(10)));

        InvalidTokenRequestException ex = assertThrows(InvalidTokenRequestException.class,
                () -> service.verifyExpiration(token));
        assertTrue(ex.getMessage().contains("만료된 token. 새로운 발급을 요청하세요."));
    }

    @Test
    void testVerifyExpirationWhenTokenIsMarkedInactive() {
        PasswordResetToken token = mock(PasswordResetToken.class, Mockito.RETURNS_DEEP_STUBS);
        when(token.getExpiryDate()).thenReturn(Date.from(Instant.now().plusSeconds(10)));
        when(token.getActive()).thenReturn(false);

        InvalidTokenRequestException ex = assertThrows(InvalidTokenRequestException.class,
                () -> service.verifyExpiration(token));
        assertTrue(ex.getMessage().contains("Token이 비활성으로 표시됨"));
    }

    @Test
    void testMatchEmailWhenEmailMatches() {
        PasswordResetToken token = mock(PasswordResetToken.class, Mockito.RETURNS_DEEP_STUBS);
        when(token.getUser().getEmail()).thenReturn("email-1");

        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("email-1");
        assertAll(() -> service.matchEmail(token, request.getEmail()));
    }


    @Test
    void testMatchEmailWhenEmailDoesNotMatch() {
        PasswordResetToken token = mock(PasswordResetToken.class, Mockito.RETURNS_DEEP_STUBS);
        when(token.getUser().getEmail()).thenReturn("email-1");

        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("email-2");
        InvalidTokenRequestException ex = assertThrows(InvalidTokenRequestException.class,
                () -> service.matchEmail(token, request.getEmail()));
        assertTrue(ex.getMessage().contains("지정된 사용자에 대한 토큰이 유효하지 않습니다. - email-2"));
    }

}
