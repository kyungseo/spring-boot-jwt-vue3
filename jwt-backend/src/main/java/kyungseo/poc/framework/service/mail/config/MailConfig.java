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

package kyungseo.poc.framework.service.mail.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Configuration
@EnableAsync
public class MailConfig {

    @Value("${spring.mail.default-encoding}")
    private String mailDefaultEncoding;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.port}")
    private Integer mailPort;

    @Value("${spring.mail.protocol}")
    private String mailProtocol;

    @Value("${spring.mail.debug}")
    private String mailDebug;

    @Value("${spring.mail.properties.mail.smtps.auth}")
    private String mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtps.starttls.enable}")
    private String mailSmtpStartTls;

    @Bean
    @Primary
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("/templates/email/");
        return bean;
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setDefaultEncoding(mailDefaultEncoding);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", mailSmtpStartTls);
        javaMailProperties.put("mail.smtp.auth", mailSmtpAuth);
        javaMailProperties.put("mail.transport.protocol", mailProtocol);
        javaMailProperties.put("mail.debug", mailDebug);

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

}
