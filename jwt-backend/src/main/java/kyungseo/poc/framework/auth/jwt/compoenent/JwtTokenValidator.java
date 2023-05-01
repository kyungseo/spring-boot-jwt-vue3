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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.jwt.event.OnUserLogoutSuccessEvent;

@Component
public class JwtTokenValidator {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String jwtSecret;

    private final LoggedOutJwtTokenCache loggedOutTokenCache;

    public JwtTokenValidator(@Value("${token.key.secret}") String jwtSecret, LoggedOutJwtTokenCache loggedOutTokenCache) {
        this.jwtSecret = jwtSecret;
        this.loggedOutTokenCache = loggedOutTokenCache;
    }

    /**
     * Token이 다음 속성을 만족하는지 확인
     *   - Signature is not malformed
     *   - Token hasn't expired
     *   - Token is supported
     *   - Token has not recently been logged out.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        }
        catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature -> Message: {}", ex);
            throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
        }
        catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token -> Message: {}", ex);
            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");
        }
        catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token -> Message: {}", ex);
            throw new InvalidTokenRequestException("JWT", authToken, "Token이 만료됨. 갱신 필요");
        }
        catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token -> Message: {}", ex);
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");
        }
        catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty. -> Message: {}", ex);
            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        }

        validateTokenIsNotForALoggedOutDevice(authToken);
        return true;
    }

    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = loggedOutTokenCache.getLogoutEventForToken(authToken);
        if (previouslyLoggedOutEvent != null) {
            String userEmail = previouslyLoggedOutEvent.getUserEmail();
            Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
            String errorMessage = String.format("사용자 [%s]의 Token은 [%s]에 이미 로그아웃 하였습니다. 다시 로그인하십시오.", userEmail, logoutEventDate);
            throw new InvalidTokenRequestException("JWT", authToken, errorMessage);
        }
    }

}
