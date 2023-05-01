/* ============================================================================
 * [ Development Templates based on Spring Boot ]
 * ----------------------------------------------------------------------------
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 *
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
 * ============================================================================
 * Author     Date            Description
 * --------   ----------      -------------------------------------------------
 * Kyungseo   2023-03-02      initial version
 * ========================================================================= */

package kyungseo.poc.framework.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Configuration
public class MessageConfig {

    // Spring Boot의 기본 messageSource 설정 사용 중!
    // Custom으로 사용하려면 하단 주석 해제 후 수정할 것

    /*
    @Bean
    public MessageSource messageSource() {
        //final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages-common","i18n/messages-common_ko","i18n/messages-todo","i18n/messages-todo_ko");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(60);
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource());
        return messageSourceAccessor;
    }

    @Bean
    public ResourceBundle getBeanResourceBundle() {
        final Locale locale = Locale.getDefault();
        return new MessageSourceResourceBundle(messageSource(), locale);
    }
    */

}
