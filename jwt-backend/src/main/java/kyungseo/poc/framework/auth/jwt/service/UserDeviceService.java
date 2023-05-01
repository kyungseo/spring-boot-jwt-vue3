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

package kyungseo.poc.framework.auth.jwt.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.TokenRefreshException;
import kyungseo.poc.framework.auth.core.persistence.entity.RefreshToken;
import kyungseo.poc.framework.auth.core.persistence.entity.UserDevice;
import kyungseo.poc.framework.auth.core.persistence.repository.UserDeviceRepository;
import kyungseo.poc.framework.auth.jwt.payload.request.DeviceInfo;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Service
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    /**
     * 사용자 ID로 사용자 기기(Device) 정보 검색
     */
    public Optional<UserDevice> findDeviceByUserId(Long userId, String deviceId) {
        return userDeviceRepository.findByUserIdAndDeviceId(userId, deviceId);
    }

    /**
     * Refresh token으로 사용자 기기(Device) 정보 검색
     */
    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    /**
     * 새로운 사용자 기기(Device) 정보를 생성하고 사용자를 현재 기기로 설정
     */
    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setNotificationToken(deviceInfo.getNotificationToken());
        userDevice.setRefreshActive(true);

        return userDevice;
    }

    /**
     * Token에 해당하는 사용자 기기(Device)가 refresh 가능한지를 확인하고
     * 클라이언트에 적절한 오류를 발생
     */
    void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(
                        refreshToken.getToken(),
                        "Token과 일치하는 사용자 기기 정보를 찾을 수 없습니다. 다시 로그인하십시오"));

        if (!userDevice.getRefreshActive()) {
            throw new TokenRefreshException(
                    refreshToken.getToken(),
                    "사용자 기기(Device)에 대해 새로고침이 차단되었습니다. 다른 기기를 통해 로그인하세요.");
        }
    }

}
