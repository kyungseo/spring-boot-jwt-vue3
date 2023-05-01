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

package kyungseo.poc.framework.service.mail.util;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import kyungseo.poc.framework.service.mail.model.Mail;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Component
public class MailUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${support.email}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public boolean send(Mail mail) {
        String to = mail.getTo();
        String subject = mail.getSubject();
        String content = mail.getContent();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            LOGGER.info("이메일 전송 완료");
            return true;
        }
        catch (MailException e) {
            LOGGER.error("이메일 전송 실패, to: {}, title: {}", to, subject, e);
            return false;
        }
    }

    public boolean sendAttachment(Mail mail, File file) {
        String to = mail.getTo();
        String subject = mail.getSubject();
        String content = mail.getContent();

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            FileSystemResource resource = new FileSystemResource(file);
            String fileName = file.getName();
            helper.addAttachment(fileName, resource);
            mailSender.send(message);
            LOGGER.info("첨부파일 이메일 전송 완료");
            return true;
        }
        catch (Exception e) {
            LOGGER.error("첨부파일 이메일 전송 실패, to: {}, title: {}", to, subject, e);
            return false;
        }
    }

}
