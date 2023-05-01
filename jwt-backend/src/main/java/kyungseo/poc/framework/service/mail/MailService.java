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

package kyungseo.poc.framework.service.mail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import kyungseo.poc.framework.service.mail.model.Mail;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    private final Configuration templateConfiguration;

    @Value("${token.velocity.templates.location}")
    private String basePackagePath;

    @Value("${support.email}")
    private String mailFrom;

    @Value("${token.password.reset.duration}")
    private Long expiration;

    public MailService(JavaMailSender mailSender, Configuration templateConfiguration) {
        this.mailSender = mailSender;
        this.templateConfiguration = templateConfiguration;
    }

    public void sendEmailVerification(String emailVerificationUrl, String to)
            throws IOException, TemplateException, MessagingException {
        Mail mail = new Mail();
        mail.setSubject("[KYUNGSEO.PoC] Email 검증");
        mail.setTo(to);
        mail.setFrom(mailFrom);
        mail.getModel().put("userName", to);
        mail.getModel().put("userEmailTokenVerificationLink", emailVerificationUrl);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("email-verification.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
        mail.setContent(mailContent);
        send(mail);
    }

    /**
     * 사용자의 등록 메일에 비밀번호 재설정 링크 보내기
     */
    public void sendResetLink(String resetPasswordLink, String to)
            throws IOException, TemplateException, MessagingException {
        Long expirationInMinutes = TimeUnit.MILLISECONDS.toMinutes(expiration);
        String expirationInMinutesString = expirationInMinutes.toString();
        Mail mail = new Mail();
        mail.setSubject("[KYUNGSEO.PoC] 비밀번호 재설정 링크");
        mail.setTo(to);
        mail.setFrom(mailFrom);
        mail.getModel().put("userName", to);
        mail.getModel().put("userResetPasswordLink", resetPasswordLink);
        mail.getModel().put("expirationTime", expirationInMinutesString);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("reset-link.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
        mail.setContent(mailContent);
        send(mail);
    }

    /**
     * 사용자의 등록 메일에 계정 상태 변경 알림 보내기
     */
    public void sendAccountChangeEmail(String action, String actionStatus, String to)
            throws IOException, TemplateException, MessagingException {
        Mail mail = new Mail();
        mail.setSubject("[KYUNGSEO.PoC] 계정 변경 사항 알림");
        mail.setTo(to);
        mail.setFrom(mailFrom);
        mail.getModel().put("userName", to);
        mail.getModel().put("action", action);
        mail.getModel().put("actionStatus", actionStatus);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("account-activity-change.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
        mail.setContent(mailContent);
        send(mail);
    }

    /**
     * MIME Multipart message를 사용하여 간단한 메일을 전송
     */
    public void send(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(mail.getTo());
        helper.setText(mail.getContent(), true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        mailSender.send(message);
    }

}
