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

package kyungseo.poc.framework.auth.jwt.event.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import freemarker.template.TemplateException;
import kyungseo.poc.framework.auth.core.exception.MailSendException;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.jwt.event.OnUserRegistrationCompleteEvent;
import kyungseo.poc.framework.auth.jwt.service.VerificationTokenService;
import kyungseo.poc.framework.service.mail.MailService;

@Component
public class UserRegistrationCompleteListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final VerificationTokenService emailVerificationTokenService;

    private final MailService mailService;

    public UserRegistrationCompleteListener(VerificationTokenService emailVerificationTokenService, MailService mailService) {
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.mailService = mailService;
    }

    /**
     * 사용자 등록 이벤트가 완료되는 즉시 sendEmailVerification을 호출
     */
    @Override
    @Async
    public void onApplicationEvent(OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent) {
        sendEmailVerification(onUserRegistrationCompleteEvent);
    }

    /**
     * 사용자에게 등록 완료 확인을 위한 이메일을 전송하고 데이터베이스에 token을 저장
     */
    private void sendEmailVerification(OnUserRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = emailVerificationTokenService.generateNewToken();
        emailVerificationTokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", token).toUriString();

        try {
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }
}
