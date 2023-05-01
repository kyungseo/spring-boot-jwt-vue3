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


import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.core.exception.PasswordResetException;
import kyungseo.poc.framework.auth.core.exception.PasswordResetLinkException;
import kyungseo.poc.framework.auth.core.exception.TokenRefreshException;
import kyungseo.poc.framework.auth.core.exception.UserLoginException;
import kyungseo.poc.framework.auth.core.exception.UserRegistrationException;
import kyungseo.poc.framework.auth.core.persistence.entity.RefreshToken;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.entity.VerificationToken;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtTokenProvider;
import kyungseo.poc.framework.auth.jwt.event.OnGenerateResetLinkEvent;
import kyungseo.poc.framework.auth.jwt.event.OnRegenerateEmailVerificationEvent;
import kyungseo.poc.framework.auth.jwt.event.OnUserAccountChangeEvent;
import kyungseo.poc.framework.auth.jwt.event.OnUserRegistrationCompleteEvent;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;
import kyungseo.poc.framework.auth.jwt.payload.request.LoginRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.PasswordResetLinkRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.PasswordResetRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.RegistrationRequest;
import kyungseo.poc.framework.auth.jwt.payload.request.TokenRefreshRequest;
import kyungseo.poc.framework.auth.jwt.payload.response.JwtResponse;
import kyungseo.poc.framework.auth.jwt.service.AuthService;
import kyungseo.poc.framework.payload.generic.GenericResponse;
import kyungseo.poc.framework.payload.generic.GenericResponseBody;

@RestController
@RequestMapping(AppConstants.API_JWT_URI_PREFIX + "/auth")
public class JwtAuthRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AuthService authService;

    private final JwtTokenProvider tokenProvider;

    private final ApplicationEventPublisher applicationEventPublisher;

    public JwtAuthRestController(AuthService authService, JwtTokenProvider tokenProvider, ApplicationEventPublisher applicationEventPublisher) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // TODO
    // getUsers()
    // getUserProfileById()
    // activateUserById()
    // deactivateUserById()
    // deleteUserById()
    // ...

    /**
     * 주어진 이메일이 사용 중인지 여부를 확인
     */
    @GetMapping("/checkEmailInUse")
    public ResponseEntity<GenericResponseBody> checkEmailInUse(
            @Param(value = "이미 사용중인지 체크할 대상 Email") @RequestParam("email") String email) {
        Boolean emailExists = authService.emailAlreadyExists(email);
        return GenericResponse.success(emailExists.toString());
    }

    /**
     * 사용자 로그인을 위한 Entry point. jwt 기반의 auth token과 refresh token을 반환
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(
            @Param(value = "로그인 요청 파라미터") @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("사용자 로그인 실패: [" + loginRequest + "]"));

        User user = (User) authentication.getPrincipal();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        LOGGER.info("로그인한 사용자 [Username]: " + customUserDetails.getUsername());

        // Spring Security의 Authentication 객체 생성
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtResponse(
                            jwtToken,
                            refreshToken,
                            tokenProvider.getUserAuthorities(customUserDetails),
                            tokenProvider.getExpiryDuration(),
                            customUserDetails.getId(),
                            customUserDetails.getUsername(),
                            customUserDetails.getMembername(),
                            customUserDetails.getEmail()
                        )
                    );
                })
                .orElseThrow(() -> new UserLoginException("Refresh token 생성 실패: [" + loginRequest + "]"));
    }

    /**
     * 사용자 등록 절차를 위한 Entry point
     * 등록에 성공하면 Event를 게시하여 이메일 verification token을 생성
     */
    @PostMapping("/register")
    public ResponseEntity<GenericResponseBody> registerUser(
            @Param(value = "사용자 등록 요청 파라미터") @Valid @RequestBody RegistrationRequest registrationRequest) {
        return authService.registerUser(registrationRequest)
                .map(user -> {
                    // UserRegistrationCompleteListener에서 확인 메일 발송
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(
                            AppConstants.API_JWT_URI_PREFIX + "/auth/registrationConfirmation");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent =
                            new OnUserRegistrationCompleteEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    LOGGER.info("Registered User returned [API[: " + user);

                    return GenericResponse.success("사용자 등록이 성공하였습니다. 확인을 위해 이메일을 체크하시기 바랍니다.");
                })
                .orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Database에 사용자 객체가 없습니다."));
    }

    /**
     * 비밀번호 재설정 링크 요청을 수신하고 요청이 유효한 경우,
     * reset link가 포함된 이메일을 발송하는 이벤트를 게시
     */
    @PostMapping("/password/resetlink")
    public ResponseEntity<GenericResponseBody> resetLink(
            @Param(value = "비밀번호 재설정 링크 요청 파라미터") @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest) {
        return authService.generatePasswordResetToken(passwordResetLinkRequest)
                .map(passwordResetToken -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(
                            AppConstants.API_JWT_URI_PREFIX + "/auth/password/reset");
                    OnGenerateResetLinkEvent generateResetLinkMailEvent =
                            new OnGenerateResetLinkEvent(passwordResetToken,
                            urlBuilder);
                    applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
                    return GenericResponse.success("비밀번호 reset link 발송 성공");
                })
                .orElseThrow(() -> new PasswordResetLinkException(passwordResetLinkRequest.getEmail(), "유효 token 생성 실패"));
    }

    /**
     * 확인 후 비밀번호를 재설정하고 이벤트를 게시하여 승인 이메일을 발송
     */
    @PostMapping("/password/reset")
    public ResponseEntity<GenericResponseBody> resetPassword(
            @Param(value = "비밀번호 재설정 요청 파라미터") @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        return authService.resetPassword(passwordResetRequest)
                .map(changedUser -> {
                    OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset" +
                            " Password",
                            "Changed Successfully");
                    applicationEventPublisher.publishEvent(onPasswordChangeEvent);
                    return GenericResponse.success("비밀번호 변경 성공");
                })
                .orElseThrow(() -> new PasswordResetException(passwordResetRequest.getToken(), "비밀번호 재설정 오류"));
    }

    /**
     * 사용자를 등록하는 과정에서 이메일 verification token을 확인
     * 이때 token이 유효하지 않거나 token이 만료된 경우 오류를 보고
     */
    @GetMapping("/registrationConfirmation")
    public ResponseEntity<GenericResponseBody> confirmRegistration(
            @Param(value = "사용자 이메일로 전송된 token") @RequestParam("token") String token) {
        return authService.confirmEmailRegistration(token)
                .map(user -> GenericResponse.success("사용자 확인 성공"))
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token, "사용자 확인 실패 - 새 이메일 확인 요청을 생성하십시오."));
    }

    /**
     * 만료가 업데이트된 token으로 등록 메일을 재전송
     */
    @GetMapping("/resendRegistrationToken")
    public ResponseEntity<GenericResponseBody> resendRegistrationToken(
            @Param(value = "사용자 등록 후 이메일로 전송된 초기 Token") @RequestParam("token") String existingToken) {
        VerificationToken newEmailToken = authService.recreateRegistrationToken(existingToken)
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
                        "사용자가 이미 등록되어 있습니다. 토큰을 다시 생성할 필요가 없습니다."));

        return Optional.ofNullable(newEmailToken.getUser())
                .map(registeredUser -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(
                            AppConstants.API_JWT_URI_PREFIX + "/auth/registrationConfirmation");
                    OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent =
                            new OnRegenerateEmailVerificationEvent(registeredUser, urlBuilder, newEmailToken);
                    applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);
                    return GenericResponse.success("이메일 verification 재전송 성공");
                })
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
                        "이 요청과 관련된 사용자가 없습니다. 재확인 거부됨"));
    }

    /**
     * 특정 기기(Device)에 대한 refresh token을 사용하여 이미 만료된 Auth Token(Access Token)을 갱신한 후 업데이트된 token을 반환
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshJwtToken(
            @Param(value = "The TokenRefreshRequest payload") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        // TODO 사용자가 실제로 애플리케이션을 사용하는 동안, JWT Token을 새로 고치기 위해 주기적으로 "/refresh" 요청을 보냄
        return authService.refreshJwtToken(tokenRefreshRequest.getRefreshToken())
                .map(updatedToken -> {
                    LOGGER.info("새로운 Jwt Auth token 생성 완료: " + updatedToken);
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    User user = authService.getUserById(tokenProvider.getUserIdFromJWT(updatedToken)).get();

                    return ResponseEntity.ok(new JwtResponse(
                            updatedToken,
                            refreshToken,
                            tokenProvider.getRolesFromJWT(updatedToken),
                            tokenProvider.getExpiryDuration(),
                            user.getId(),
                            user.getUsername(),
                            user.getMembername(),
                            user.getEmail()
                        )
                    );
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "Token을 갱신하는 동안 예기치 않은 오류가 발생했습니다. 로그아웃했다가 다시 로그인하십시오."));
    }

}
