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

package kyungseo.poc.framework.auth.core.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kyungseo.poc.framework.auth.core.persistence.repository.PasswordResetTokenRepository;
import kyungseo.poc.framework.auth.core.persistence.repository.RefreshTokenRepository;
import kyungseo.poc.framework.auth.core.persistence.repository.VerificationTokenRepository;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Service
@Transactional
public class TokensPurgeTask {

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());

        passwordTokenRepository.deleteAllExpiredSince(now);
        tokenRepository.deleteAllExpiredSince(now);
        refreshTokenRepository.deleteAllExpiredSince(now);
    }

}
