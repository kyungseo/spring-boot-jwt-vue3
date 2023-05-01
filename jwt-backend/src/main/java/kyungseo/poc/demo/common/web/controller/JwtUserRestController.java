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

package kyungseo.poc.demo.common.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.auth.core.exception.UpdatePasswordException;
import kyungseo.poc.framework.auth.core.service.CurrentUser;
import kyungseo.poc.framework.auth.jwt.event.OnUserAccountChangeEvent;
import kyungseo.poc.framework.auth.jwt.event.OnUserLogoutSuccessEvent;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;
import kyungseo.poc.framework.auth.jwt.payload.request.LogOutRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.UpdatePasswordRequest;
import kyungseo.poc.framework.auth.jwt.service.AuthService;
import kyungseo.poc.framework.auth.jwt.service.JwtUserService;
import kyungseo.poc.framework.payload.generic.GenericResponse;
import kyungseo.poc.framework.payload.generic.GenericResponseBody;

// 로그인한 사용자의 endpoint(진입점)를 정의 - 기본적으로 보안이 설정되어 있다.
@RestController
@RequestMapping(AppConstants.API_JWT_URI_PREFIX + "/user")
public class JwtUserRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AuthService authService;

    private final JwtUserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public JwtUserRestController(AuthService authService, JwtUserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.authService = authService;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 로그인한 사용자의 profile을 조회
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponseBody> getUserProfile(@CurrentUser CustomUserDetails currentUser) {
        LOGGER.info(currentUser.getEmail() + " has role: " + currentUser.getRoles());
        return GenericResponse.success("성공");
    }

    /**
     * 시스템의 모든 관리자를 반환. ADMIN 액세스 필요
     */
    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponseBody> getAllAdmins() {
        LOGGER.info("Inside secured resource with admin");
        return GenericResponse.success("성공");
    }

    /**
     * 현재 로그인한 사용자의 비밀번호를 업데이트
     */
    @PostMapping("/password/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponseBody> updateUserPassword(@CurrentUser CustomUserDetails customUserDetails,
            @Param(value = "The UpdatePasswordRequest payload") @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        return authService.updatePassword(customUserDetails, updatePasswordRequest)
                .map(updatedUser -> {
                    OnUserAccountChangeEvent onUserPasswordChangeEvent = new OnUserAccountChangeEvent(updatedUser,
                            "Update Password", "Change successful");
                    applicationEventPublisher.publishEvent(onUserPasswordChangeEvent);
                    return GenericResponse.success("성공");
                })
                .orElseThrow(() -> new UpdatePasswordException("--Empty--", "해당 사용자가 없습니다."));
    }

    /**
     * 앱/기기(Device)에서 사용자를 로그아웃.
     * 이때 지정된 사용자 기기(Device) 정보를 기록하고 연결되어 있는 refresh token을 삭제
     */
    @PostMapping("/logout")
    public ResponseEntity<GenericResponseBody> logoutUser(@CurrentUser CustomUserDetails customUserDetails,
            @Param(value = "The LogOutRequest payload") @Valid @RequestBody LogOutRequest logOutRequest) {
        userService.logoutUser(customUserDetails, logOutRequest);
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();

        // UserLogoutSuccessEventListener에서 Logout 된 token을 Blacklist(Cache)에 저장
        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(customUserDetails.getEmail(),
                credentials.toString(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);

        return GenericResponse.success("사용자가 로그아웃 성공!");
    }

}
