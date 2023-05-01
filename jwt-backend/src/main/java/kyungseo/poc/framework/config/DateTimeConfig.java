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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import kyungseo.poc.AppConstants;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Configuration
public abstract class DateTimeConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                LOGGER.debug(text);
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT, locale));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                LOGGER.debug(object.toString());
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                LOGGER.debug(text);
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(AppConstants.DATETIME_FORMAT, locale));
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                LOGGER.debug(object.toString());
                return DateTimeFormatter.ISO_DATE_TIME.format(object);
            }
        };
    }

    @Bean
    public Formatter<Date> DateFormatter() {
        return new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                LOGGER.debug(text);
                SimpleDateFormat dt = new SimpleDateFormat(AppConstants.DATETIME_FORMAT, locale);
                return dt.parse(text);
            }

            @Override
            public String print(Date object, Locale locale) {
                LOGGER.debug(object.toString());
                return new SimpleDateFormat(AppConstants.DATETIME_FORMAT).format(object);
            }
        };
    }

}

/*
또는 다음과 같이...
//@Configuration
public class DateTimeConfig extends WebMvcConfigurationSupport {

  @Bean
  @Override
  public FormattingConversionService mvcConversionService() {
      DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

      DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
      dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT));
      dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(AppConstants.DATETIME_FORMAT));
      dateTimeRegistrar.registerFormatters(conversionService);

      DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
      dateRegistrar.setFormatter(new DateFormatter(AppConstants.DATE_FORMAT));
      dateRegistrar.registerFormatters(conversionService);

      return conversionService;
  }

}
*/
