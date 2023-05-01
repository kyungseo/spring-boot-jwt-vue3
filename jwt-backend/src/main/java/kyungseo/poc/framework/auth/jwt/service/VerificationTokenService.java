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

package kyungseo.poc.framework.auth.jwt.service;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.core.model.TokenStatus;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.entity.VerificationToken;
import kyungseo.poc.framework.auth.core.persistence.repository.VerificationTokenRepository;
import kyungseo.poc.framework.util.RandomUtil;

@Service
public class VerificationTokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${token.email.verification.duration}")
    private Long verificationTokenExpiryDuration;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    /**
     * 이메일 확인을 위한 token을 생성하고 사용자가 확인할 수 있도록 Database에 저장
     */
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Date.from(Instant.now().plusMillis(verificationTokenExpiryDuration)));
        LOGGER.info("Generated Email verification token [" + verificationToken + "]");
        verificationTokenRepository.save(verificationToken);
    }

    /**
     * Database의 기존 Token의 만료를 새로 업데이트
     */
    public VerificationToken updateExistingTokenWithNameAndExpiry(VerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        existingToken.setExpiryDate(Date.from(Instant.now().plusMillis(verificationTokenExpiryDuration)));
        LOGGER.info("Updated Email verification token [" + existingToken + "]");
        return save(existingToken);
    }

    /**
     * @NaturalId token으로 이메일 verification token을 검색
     */
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    /**
     * Repository에 이메일 verification token을 저장
     */
    public VerificationToken save(VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    /**
     * 이메일 확인을 위한 토큰으로 사용하기 위해 새로운 임의의 UUID를 생성
     */
    public String generateNewToken() {
        return RandomUtil.generateUUID();
    }

    /**
     * 현재 서버 시간을 기준으로 제공된 Token이 만료되었는지 여부 및 오류 발생 여부를 확인
     */
    public void verifyExpiration(VerificationToken token) {
        if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
            throw new InvalidTokenRequestException(
                    "Email Verification Token",
                    token.getToken(),
                    "만료된 token. 새로운 발급을 요청하세요.");
        }
    }

}
