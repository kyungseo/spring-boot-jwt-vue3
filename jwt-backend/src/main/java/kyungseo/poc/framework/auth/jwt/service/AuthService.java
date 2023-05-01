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

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.exception.PasswordResetLinkException;
import kyungseo.poc.framework.auth.core.exception.ResourceAlreadyInUseException;
import kyungseo.poc.framework.auth.core.exception.TokenRefreshException;
import kyungseo.poc.framework.auth.core.exception.UpdatePasswordException;
import kyungseo.poc.framework.auth.core.persistence.entity.PasswordResetToken;
import kyungseo.poc.framework.auth.core.persistence.entity.RefreshToken;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.entity.UserDevice;
import kyungseo.poc.framework.auth.core.persistence.entity.VerificationToken;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtTokenProvider;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;
import kyungseo.poc.framework.auth.jwt.payload.request.LoginRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.PasswordResetLinkRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.PasswordResetRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.RegistrationRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.UpdatePasswordRequest;
import kyungseo.poc.framework.exception.ResourceNotFoundException;

@Service
public class AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final JwtUserService userService;

    private final JwtTokenProvider tokenProvider;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final VerificationTokenService verificationTokenService;

    private final UserDeviceService userDeviceService;

    private final PasswordResetTokenService passwordResetService;

    @Autowired
    public AuthService(JwtUserService userService, JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService,
                PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                VerificationTokenService verificationTokenService, UserDeviceService userDeviceService,
                PasswordResetTokenService passwordResetService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.verificationTokenService = verificationTokenService;
        this.userDeviceService = userDeviceService;
        this.passwordResetService = passwordResetService;
    }

    /**
     * 일련의 빠른 검사를 수행하여 Database에 새 사용자를 등록
     *
     * @return 성공적으로 생성된 경우, 사용자 객체
     */
    public Optional<User> registerUser(RegistrationRequest registrationRequest) {
        String newEmail = registrationRequest.getEmail();

        if (emailAlreadyExists(newEmail)) {
            LOGGER.error("이메일이 이미 사용 중입니다! - " + newEmail);
            throw new ResourceAlreadyInUseException("Email", "Address", newEmail);
        }

        LOGGER.info("[" + newEmail + "] 새로운 사용자 등록 시도");
        User newUser = userService.createUser(registrationRequest); // user 객체 생성
        User registeredNewUser = userService.save(newUser); // repository에 저장
        return Optional.ofNullable(registeredNewUser);
    }

    public Optional<User> getUserById(Long id) {
        return userService.findById(id);
    }

    /**
     * 주어진 이메일이 Database에 이미 존재하는지 확인
     *
     * @return 이메일이 존재하면 true, 그렇지 않으면 false
     */
    public Boolean emailAlreadyExists(String email) {
        return userService.existsByEmail(email);
    }

    /**
     * LoginRequest를 사용하여 사용자를 인증하고 로그인 처리
     */
    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
        /*
        // 먼저 email을 기반으로 유효한 사용자가 존재하는 지 검증
        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Fail! -> 원인: 사용자를 찾을 수 없음"));
        */

        // Login ID/PW 를 기반으로 Spring Security의 Authentication 객체 생성
        //   -> loadUserByUsername 메서드 호출
        return Optional.ofNullable(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())));
    }

    /**
     * 토큰 만료를 기반으로 사용자를 검증하고 해당 사용자를 활성(active)으로 표시
     * 사용자가 이미 확인된 경우 불필요한 데이터베이스 호출을 지양
     */
    public Optional<User> confirmEmailRegistration(String emailToken) {
        VerificationToken verificationToken = verificationTokenService.findByToken(emailToken);
        if (ObjectUtils.isEmpty(verificationToken)) {
            throw new ResourceNotFoundException("Token", "Email verification", emailToken);
        }

        User registeredUser = verificationToken.getUser();
        if (registeredUser.getEmailVerified()) {
            LOGGER.info("이미 등록된 사용자 [" + emailToken + "]");
            return Optional.of(registeredUser);
        }

        verificationTokenService.verifyExpiration(verificationToken);
        verificationToken.setConfirmedStatus();
        verificationTokenService.save(verificationToken);

        registeredUser.markVerificationConfirmed();
        userService.save(registeredUser);
        return Optional.of(registeredUser);
    }

    /**
     * 주어진 Token이 만료된 상태인 경우, 새 이메일 verification token을 다시 생성.
     * 이전 Token이 유효하다면 만료 기간을 늘리고,
     * 유효하지 않다면 Token 값을 업데이트하고 새로운 만료를 추가
     *
     */
    public Optional<VerificationToken> recreateRegistrationToken(String existingToken) {
        VerificationToken verificationToken = verificationTokenService.findByToken(existingToken);
        if (ObjectUtils.isEmpty(verificationToken)) {
        }

        if (verificationToken.getUser().getEmailVerified()) {
            return Optional.empty();
        }
        return Optional.ofNullable(verificationTokenService.updateExistingTokenWithNameAndExpiry(verificationToken));
    }

    /**
     * 현재 로그인한 사용자의 비밀번호를 검증
     */
    private Boolean currentPasswordMatches(User currentUser, String password) {
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    /**
     * 현재 로그인한 사용자의 비밀번호를 업데이트
     */
    public Optional<User> updatePassword(CustomUserDetails customUserDetails,
                                         UpdatePasswordRequest updatePasswordRequest) {
        String email = customUserDetails.getEmail();
        User currentUser = userService.findByEmail(email)
                .orElseThrow(() -> new UpdatePasswordException(email, "No matching user found"));

        if (!currentPasswordMatches(currentUser, updatePasswordRequest.getOldPassword())) {
            LOGGER.info("Current password is invalid for [" + currentUser.getPassword() + "]");
            throw new UpdatePasswordException(currentUser.getEmail(), "Invalid current password");
        }
        String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        currentUser.setPassword(newPassword);
        userService.save(currentUser);
        return Optional.of(currentUser);
    }

    /**
     * 검증된 Client를 위해 JWT token을 생성
     */
    public String generateToken(CustomUserDetails customUserDetails) {
        return tokenProvider.generateToken(customUserDetails);
    }

    /**
     * userId를 사용해 검증된 클라이언트에 대한 JWT Token을 생성
     */
    private String generateTokenFromUserId(Long userId) {
        return tokenProvider.generateTokenFromUserId(userId);
    }

    /**
     * 사용자 기기(Device)와 매핑하기 위한 refresh token을 생성하고 저장한다.
     * 만약 사용자 기기가 이미 존재하면 refresh toke을 다시 생성한다.
     * 만료된 token이 매핑된 사용되지 않은 기기의 경우, 별도의 배치 Job 등을 통해 제거해야 한다.
     */
    public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(
            Authentication authentication, LoginRequest loginRequest) {
        User currentUser = (User) authentication.getPrincipal();
        String deviceId = loginRequest.getDeviceInfo().getDeviceId();
        // 이미 사용자 Device와 연결된 Refresh Token이 있다면, 현재 새 Session을 생성하는 중이므로 삭제 처리
        userDeviceService.findDeviceByUserId(currentUser.getId(), deviceId)
                .map(UserDevice::getRefreshToken)
                .map(RefreshToken::getId)
                .ifPresent(refreshTokenService::deleteById);

        // UserDevice와 Refresh Token 객체를 새로 생성
        UserDevice userDevice = userDeviceService.createUserDevice(loginRequest.getDeviceInfo());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();

        userDevice.setUser(currentUser);
        userDevice.setRefreshToken(refreshToken);
        refreshToken.setUserDevice(userDevice);

        // 새로 생성한 Refresh Token을 DB에 저장
        refreshToken = refreshTokenService.save(refreshToken);

        return Optional.ofNullable(refreshToken);
    }

    /**
     * Refresh token과 기기(Device) 정보를 사용하여 만료된 jwt token을 갱신.
     *
     * Refresh token은 특정 기기에 매핑되며 Refresh token이 만료되지 않은 상태라면 새로운 jwt를 생성하기 위해 사용
     * 만약 Refresh token이 해당 기기에 대해 비활성 상태이거나 만료된 경우라면 적절한 오류를 발생
     */
    public Optional<String> refreshJwtToken(String requestRefreshToken) {
        // 사용자가 명시적으로 logout 한 경우, DB에 Refresh Token이 존재하지 않는다.
        return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    refreshTokenService.verifyExpiration(refreshToken);        // refreshToken의 만료여부 체크
                    userDeviceService.verifyRefreshAvailability(refreshToken); // refreshToken과 매핑된 Device 속성 중 갱신가능여부 체크
                    refreshTokenService.increaseCount(refreshToken);           // refreshToken의 갱신횟수 업데이트
                    return refreshToken;
                })
                .map(RefreshToken::getUserDevice) // refreshToken으로 사용자 Device 정보 추출
                .map(UserDevice::getUser)         // 사용자 Device에서 User 정보 추출
                .map(CustomUserDetails::new)      // UserDetails 정보를 사용해
                .map(this::generateToken))        // 현재 시간부로 만료시간이 갱신된 Access Token을 새로 생성
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Database에 refresh token이 없습니다. 다시 로그인하십시오."));
    }

    /**
     * 재설정 요청에 따라 비밀번호 reset token을 생성
     */
    public Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest) {
        String email = passwordResetLinkRequest.getEmail();
        return userService.findByEmail(email)
                .map(passwordResetService::createToken)
                .orElseThrow(() -> new PasswordResetLinkException(email, "주어진 요청에 일치하는 사용자를 찾을 수 없습니다."));
    }

    /**
     * 재설정 요청에 따라 비밀번호를 재설정(reset)하고 업데이트된 사용자를 반환.
     *
     * Reset token은 사용자의 이메일과 일치해야 하며 다시 사용할 수 없음.
     * 사용자가 비밀번호를 여러번 요청할 수 있으므로 여러 Token이 생성될 수 있기 때문에
     * 사용자 비밀번호를 변경하기 전에 기존 비밀번호 reset token을 모두 무효화 처리
     */
    public Optional<User> resetPassword(PasswordResetRequest request) {
        PasswordResetToken token = passwordResetService.getValidToken(request);
        final String encodedPassword = passwordEncoder.encode(request.getConfirmPassword());

        return Optional.of(token)
            .map(passwordResetService::claimToken)
            .map(PasswordResetToken::getUser)
            .map(user -> {
                user.setPassword(encodedPassword);
                userService.save(user);
                return user;
            });
    }

}
