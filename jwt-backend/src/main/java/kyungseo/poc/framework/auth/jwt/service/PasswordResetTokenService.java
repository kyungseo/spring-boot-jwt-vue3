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
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.core.persistence.entity.PasswordResetToken;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.repository.PasswordResetTokenRepository;
import kyungseo.poc.framework.auth.jwt.payload.request.PasswordResetRequest;
import kyungseo.poc.framework.exception.ResourceNotFoundException;
import kyungseo.poc.framework.util.RandomUtil;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository repository;

    @Value("${token.password.reset.duration}")
    private Long expiration;

    public PasswordResetTokenService(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * naturalId가 주어진 데이터베이스에서 Token을 찾거나 예외를 발생.
     * reset token은 사용자의 이메일과 일치해야 하며 다시 사용할 수 없음
     */
    public PasswordResetToken getValidToken(PasswordResetRequest request) {
        String tokenID = request.getToken();
        PasswordResetToken token = repository.findByToken(tokenID);
        if (ObjectUtils.isEmpty(token)) {
            throw new ResourceNotFoundException("Password Reset Token", "Token Id", tokenID);
        }

        matchEmail(token, request.getEmail());
        verifyExpiration(token);
        return token;
    }

    /**
     * 사용자를 연결하여 token repository에 저장할 수 있도록 새로운 비밀번호 Token을 생성하고 반환.
     */
    public Optional<PasswordResetToken> createToken(User user) {
        PasswordResetToken token = createTokenWithUser(user);
        return Optional.of(repository.save(token));
    }

    /**
     * 비밀번호 reset token을 claimed로 표시(사용자가 암호를 업데이트하는 데 사용)
     *
     * 사용자가 비밀번호를 여러번 요청할 수 있으므로 여러 Token이 생성될 수 있기 때문에
     * 사용자 비밀번호를 변경하기 전에 기존 비밀번호 reset token을 모두 무효화 처리
     */
    public PasswordResetToken claimToken(PasswordResetToken token) {
        User user = token.getUser();
        token.setClaimed(true);

        CollectionUtils.emptyIfNull(repository.findActiveTokensForUser(user))
                .forEach(t -> t.setActive(false));

        return token;
    }

    /**
     * 현재 서버 시간을 기준으로 제공된 token이 만료되었는지 여부 및 오류 발생 여부를 확인
     */
    public void verifyExpiration(PasswordResetToken token) {
        if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
            throw new InvalidTokenRequestException("Password Reset Token", token.getToken(),
                    "만료된 token. 새로운 발급을 요청하세요.");
        }
        if (!token.getActive()) {
            throw new InvalidTokenRequestException("Password Reset Token", token.getToken(),
                    "Token이 비활성으로 표시됨");
        }
    }

    /**
     * 제공된 Token이 실제로 사용자에 의해 생성되었는지 확인을 위해 일치 여부를 체크
     */
    public void matchEmail(PasswordResetToken token, String requestEmail) {
        if (token.getUser().getEmail().compareToIgnoreCase(requestEmail) != 0) {
            throw new InvalidTokenRequestException("Password Reset Token", token.getToken(),
                    "지정된 사용자에 대한 토큰이 유효하지 않습니다. - " + requestEmail);
        }
    }

    public PasswordResetToken createTokenWithUser(User user) {
        String tokenID = RandomUtil.generateUUID();
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(tokenID);
        token.setExpiryDate(Date.from(Instant.now().plusMillis(expiration)));
        token.setClaimed(false);
        token.setActive(true);
        token.setUser(user);

        return token;
    }

}
