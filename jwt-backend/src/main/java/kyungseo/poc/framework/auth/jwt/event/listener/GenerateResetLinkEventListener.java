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
import kyungseo.poc.framework.auth.core.persistence.entity.PasswordResetToken;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.jwt.event.OnGenerateResetLinkEvent;
import kyungseo.poc.framework.service.mail.MailService;

@Component
public class GenerateResetLinkEventListener implements ApplicationListener<OnGenerateResetLinkEvent> {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MailService mailService;

    public GenerateResetLinkEventListener(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 비밀번호 재설정을 위한 링크를 클릭하고 유효한 이메일 ID를 입력하면,
     * 이 이벤트를 통해 비밀번호 재설정 링크가 해당 메일로 전송
     */
    @Override
    @Async
    public void onApplicationEvent(OnGenerateResetLinkEvent onGenerateResetLinkMailEvent) {
        sendResetLink(onGenerateResetLinkMailEvent);
    }

    /**
     * 비밀번호 재설정 토큰과 함께 재설정 링크를 메일 주소로 전송
     */
    private void sendResetLink(OnGenerateResetLinkEvent event) {
        PasswordResetToken passwordResetToken = event.getPasswordResetToken();
        User user = passwordResetToken.getUser();
        String recipientAddress = user.getEmail();
        String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", passwordResetToken.getToken())
                .toUriString();
        try {
            mailService.sendResetLink(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }

}
