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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.UserLogoutException;
import kyungseo.poc.framework.auth.core.persistence.entity.Role;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.entity.UserDevice;
import kyungseo.poc.framework.auth.core.persistence.repository.UserRepository;
import kyungseo.poc.framework.auth.core.service.CurrentUser;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;
import kyungseo.poc.framework.auth.jwt.payload.request.LogOutRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.RegistrationRequest;

@Service
public class JwtUserService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final UserDeviceService userDeviceService;

    private final RefreshTokenService refreshTokenService;

    public JwtUserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
            RoleService roleService, UserDeviceService userDeviceService, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * 이메일로 데이터베이스에서 사용자 찾기
     */
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    /**
     * Id로 데이터베이스에서 사용자 찾기
     */
    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    /**
     * 사용자를 데이터베이스에 저장
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 주어진 이메일에 해당하는 사용자가 존재하는지 확인: naturalId
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Registration 요청에 따른 신규 사용자 객체 생성
     */
    public User createUser(RegistrationRequest registerRequest) {
        User newUser = new User();
        Boolean isNewUserAsAdmin = registerRequest.getRegisterAsAdmin();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setUsername(registerRequest.getUsername());
        newUser.setMembername(registerRequest.getUsername()); // TODO FIXME Payload에 membername 추가!
        newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setEnabled(false);
        newUser.setEmailVerified(false);
        newUser.setUsing2FA(false);

        return newUser;
    }

    /**
     * 새 사용자에게 할당할 수 있는 Role을 확인하기 위해 빠른 검사를 수행
     *
     * @return 새 사용자의 역할 목록
     */
    private Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin) {
        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
        if (!isToBeMadeAdmin) {
            newUserRoles.removeIf(Role::isAdminRole);
        }
        LOGGER.info("Setting user roles: " + newUserRoles);

        return newUserRoles;
    }

    /**
     * 지정된 사용자를 로그아웃하고 이와 관련된 refresh token을 삭제.
     * Database에 이 사용자에 대한 장치 ID가 없으면 로그아웃 예외를 발생
     */
    public void logoutUser(@CurrentUser CustomUserDetails currentUser, LogOutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findDeviceByUserId(currentUser.getId(), deviceId)
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(
                        logOutRequest.getDeviceInfo().getDeviceId(),
                        "잘못된 Device ID가 제공되었습니다. 지정된 사용자와 일치하는 Device가 없습니다."));

        // UserDevice와 매핑되어 있는 RefreshToken을 삭제
        LOGGER.info("Removing refresh token associated with device [" + userDevice + "]");
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
    }

}
