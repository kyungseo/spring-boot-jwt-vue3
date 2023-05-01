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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.TokenRefreshException;
import kyungseo.poc.framework.auth.core.persistence.entity.RefreshToken;
import kyungseo.poc.framework.auth.core.persistence.repository.RefreshTokenRepository;
import kyungseo.poc.framework.util.RandomUtil;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${token.refresh.expire.time}")
    private Long refreshTokenDurationMs;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * natural id에 기반한 refresh token을 검색 - i.e token 자체
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * 업데이트된 refreshToken 인스턴스를 Database에 저장
     */
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * 새로운 refresh token을 생성하고 반환
     */
    public RefreshToken createRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(RandomUtil.generateUUID());
        refreshToken.setRefreshCount(0L);
        return refreshToken;
    }

    /**
     * 현재 서버 시간을 기준으로 제공된 Token이 만료되었는지 여부 및 오류 발생 여부를 확인
     */
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "만료된 token. 새로운 발급을 요청하세요.");
        }
    }

    /**
     * 사용자 장치와 연결된 refresh token을 삭제
     */
    public void deleteById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    /**
     * Database에서 token 사용 횟수를 증가 - 감사(Audit) 용도
     */
    public void increaseCount(RefreshToken refreshToken) {
        refreshToken.incrementRefreshCount();
        save(refreshToken);
    }

}
