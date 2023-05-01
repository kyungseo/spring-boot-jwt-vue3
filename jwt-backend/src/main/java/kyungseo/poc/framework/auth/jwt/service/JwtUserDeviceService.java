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

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.TokenRefreshException;
import kyungseo.poc.framework.auth.core.persistence.entity.RefreshToken;
import kyungseo.poc.framework.auth.core.persistence.entity.UserDevice;
import kyungseo.poc.framework.auth.core.persistence.repository.UserDeviceRepository;
import kyungseo.poc.framework.auth.jwt.payload.request.DeviceInfo;

@Service
public class JwtUserDeviceService {

	@Autowired
    private UserDeviceRepository userDeviceRepository;

    public Optional<UserDevice> findByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setRefreshActive(true);

        return userDevice;
    }

    public void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "Token에 일치하는 Device를 찾을 수 없습니다. 다시 로그인하십시오."));

        if (!userDevice.getRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), "현재 Device에 대한 Refresh가 차단되었습니다. 다른 Device를 통해 로그인하세요.");
        }
    }

}
