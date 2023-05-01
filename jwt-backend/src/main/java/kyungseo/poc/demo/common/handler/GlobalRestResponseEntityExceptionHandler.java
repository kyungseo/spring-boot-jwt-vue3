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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kyungseo.poc.framework.auth.core.exception.InvalidOldPasswordException;
import kyungseo.poc.framework.auth.core.exception.InvalidTokenRequestException;
import kyungseo.poc.framework.auth.core.exception.UserAlreadyExistException;
import kyungseo.poc.framework.auth.core.exception.UserNotFoundException;
import kyungseo.poc.framework.exception.ResourceNotFoundException;
import kyungseo.poc.framework.exception.ViolationException;
import kyungseo.poc.framework.payload.generic.GenericResponse;
import kyungseo.poc.framework.payload.generic.GenericResponseBody;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class GlobalRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messages;

    // 400
    @Override
    protected ResponseEntity<Object> handleBindException(
            final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.BAD_REQUEST, "Invalid" + result.getObjectName(), result.getAllErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Validation 결과가 오류일때...
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        final BindingResult result = ex.getBindingResult();
        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.BAD_REQUEST, "Invalid" + result.getObjectName(), result.getAllErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ InvalidOldPasswordException.class })
    public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);

        final List<ObjectError> allErrors = new ArrayList<>();
        final ObjectError invalidOldPasswordError = new ObjectError("invalidOldPassword",
                messages.getMessage("sec.message.invalidOldPassword", null, request.getLocale()));
        allErrors.add(invalidOldPasswordError);

        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.BAD_REQUEST, "InvalidOldPassword", allErrors);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 404
    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFound(final RuntimeException ex, final WebRequest request) {
        logger.error("404 Status Code", ex);

        final List<ObjectError> allErrors = new ArrayList<>();
        final ObjectError userNotFoundError = new ObjectError("userNotFound",
                messages.getMessage("sec.message.userNotFound", null, request.getLocale()));
        allErrors.add(userNotFoundError);

        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.NOT_FOUND, "UserNotFound", allErrors);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409
    @ExceptionHandler({ UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        logger.error("409 Status Code", ex);

        final List<ObjectError> allErrors = new ArrayList<>();
        final ObjectError userAlreadyExistError = new ObjectError("email",
                messages.getMessage("sec.message.regError", null, request.getLocale()));
        allErrors.add(userAlreadyExistError);

        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.CONFLICT, "UserAlreadyExist", allErrors);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 500
    @ExceptionHandler({ MailAuthenticationException.class })
    public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);

        final List<ObjectError> allErrors = new ArrayList<>();
        final ObjectError mailError = new ObjectError("emailConfig",
                messages.getMessage("sec.message.email.config.error", null, request.getLocale()));
        allErrors.add(mailError);

        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.INTERNAL_SERVER_ERROR, "MailError", allErrors);
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);

        final List<ObjectError> allErrors = new ArrayList<>();
        final ObjectError internalError = new ObjectError("internalError",
                messages.getMessage("sec.message.error", null, request.getLocale()));
        allErrors.add(internalError);

        final GenericResponseBody bodyOfResponse = GenericResponse.getErrorBody(
                HttpStatus.INTERNAL_SERVER_ERROR, "InternalError", allErrors);
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

	// 400 (BAD_REQUEST)

    @ExceptionHandler({ ValidationException.class })
    public ResponseEntity<Object> handleBadRequest(final ValidationException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ ViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final ViolationException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        logger.error("400 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                headers, HttpStatus.BAD_REQUEST, request);
    }

    // handleConnversion은 springdoc을 위한 것으로 @ControllerAdvice and @ResponseStatus를 사용하여 Document를 생성
    // https://www.baeldung.com/spring-rest-openapi-documentation
    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConnversion(RuntimeException ex) {
        logger.error("400 Status Code", ex);
        return new ResponseEntity<>(
                GenericResponse.getErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    // 401

    @ExceptionHandler({
        AuthenticationCredentialsNotFoundException.class,
        InvalidTokenRequestException.class })
    protected ResponseEntity<Object> handleNotFound(final Exception ex, final WebRequest request) {
        logger.error("401 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.UNAUTHORIZED, ex.getMessage()),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    // 404

    @ExceptionHandler({
	    EntityNotFoundException.class,
	    ResourceNotFoundException.class,
	    FileNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        logger.error("404 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.NOT_FOUND, ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

	// 409 (CONFLICT)

	@ExceptionHandler({
	    InvalidDataAccessApiUsageException.class,
	    DataAccessException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        logger.error("409 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.CONFLICT, ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	// 500 (I/O Error)

    @ExceptionHandler({
        NullPointerException.class,
        IllegalArgumentException.class,
        IllegalStateException.class })
	public ResponseEntity<Object> handleGenInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // 500 (HTTP Client/Server Error)

    @ExceptionHandler({
        HttpClientErrorException.class,
        HttpServerErrorException.class })
    public ResponseEntity<Object> handleHttpError(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

	// 500 (Rest Client Error)

    @ExceptionHandler({ RestClientException.class })
    public ResponseEntity<Object> handleRestClientError(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        return handleExceptionInternal(ex,
                GenericResponse.getErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
