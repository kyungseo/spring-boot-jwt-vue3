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

package kyungseo.poc.demo.common.handler;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.auth.core.exception.InvalidOldPasswordException;
import kyungseo.poc.framework.auth.core.exception.UserAlreadyExistException;
import kyungseo.poc.framework.auth.core.exception.UserNotFoundException;
import kyungseo.poc.framework.exception.ResourceNotFoundException;
import kyungseo.poc.framework.exception.ViolationException;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Order(2)
@ControllerAdvice(annotations = Controller.class)
public class GlobalControllerExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    // https://jira.spring.io/browse/SPR-14651
    // 4.3.5 supports RedirectAttributes redirectAttributes

    // 400

    @ExceptionHandler({
        InvalidOldPasswordException.class })
    protected String handle400Error(final RuntimeException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    protected String handle400Error(final ConstraintViolationException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    protected String handle400Error(final DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    protected String handle400Error(final HttpMessageNotReadableException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    @ExceptionHandler({ ConversionFailedException.class })
    protected String handle400Error(final ConversionFailedException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    @ExceptionHandler({ ValidationException.class })
    protected String handle400Error(final ValidationException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    @ExceptionHandler({ ViolationException.class })
    protected String handle400Error(final ViolationException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("BAD_REQUEST: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_400_URL;
    }

    // 401

    @ExceptionHandler({
        AuthenticationCredentialsNotFoundException.class })
    protected String handle401Error(final AuthenticationCredentialsNotFoundException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("NOT_FOUND: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_401_URL;
    }


    // 404

    @ExceptionHandler({
        EntityNotFoundException.class,
        ResourceNotFoundException.class,
        FileNotFoundException.class,
        UserNotFoundException.class })
    protected String handle404Error(final RuntimeException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("NOT_FOUND: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_404_URL;
    }

    // 405

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    protected String handle405Error(HttpRequestMethodNotSupportedException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("SERVER ERROR: {}", e.getMessage(), e);
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return "redirect:" + AppConstants.ERROR_405_URL;
    }

    @ExceptionHandler({ InvalidParameterException.class })
    protected String handle405Error(InvalidParameterException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("SERVER ERROR: {}", e.getMessage(), e);
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return "redirect:" + AppConstants.ERROR_405_URL;
    }

    // 409

    @ExceptionHandler({
        UserAlreadyExistException.class,
        InvalidDataAccessApiUsageException.class,
        DataAccessException.class })
    protected String handle409Error(final RuntimeException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("CONFLICT: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_409_URL;
    }

    // 500

    @ExceptionHandler({
        MailAuthenticationException.class,
        NullPointerException.class,
        IllegalArgumentException.class,
        IllegalStateException.class,
        RestClientException.class,
        HttpClientErrorException.class,
        HttpServerErrorException.class })
    protected String handle500Error(final RuntimeException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("SERVER ERROR: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_500_URL;
    }

    @ExceptionHandler({ Exception.class })
    protected String handleError(final RuntimeException e, RedirectAttributes redirectAttributes) {
        LOGGER.error("SERVER ERROR: {}", e.getMessage(), e);
        return "redirect:" + AppConstants.ERROR_500_URL;
    }

}
