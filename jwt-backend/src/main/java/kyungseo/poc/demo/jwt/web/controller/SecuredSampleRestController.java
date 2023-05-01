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

package kyungseo.poc.demo.jwt.web.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kyungseo.poc.AppConstants;
import kyungseo.poc.demo.jwt.payload.SecuredSampleDto;
import kyungseo.poc.demo.jwt.persistence.model.SecuredSample;
import kyungseo.poc.demo.jwt.service.SecuredSampleServiceImpl;
import kyungseo.poc.demo.jwt.service.SecuredSampleValidationService;
import kyungseo.poc.framework.exception.ResourceNotFoundException;
import kyungseo.poc.framework.payload.generic.GenericResponse;
import kyungseo.poc.framework.payload.generic.GenericResponseBody;
import kyungseo.poc.framework.payload.paging.PagingRequestDto;
import kyungseo.poc.framework.payload.paging.PagingResultDto;
import kyungseo.poc.framework.util.Helper;
import lombok.RequiredArgsConstructor;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@RestController
@PreAuthorize("hasRole({'ADMIN'})")
@RequestMapping( AppConstants.API_JWT_URI_PREFIX + "/secured/sample/users" )
@RequiredArgsConstructor // for 자동 DI
public class SecuredSampleRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final SecuredSampleServiceImpl userService;

    private final SecuredSampleValidationService userValidationService;

    // read - all
    @GetMapping({"", "/"})
    public ResponseEntity<GenericResponseBody> findPaginatedWithPageable(Optional<PagingRequestDto> searchDto, Model model) {
        PagingRequestDto dto = null;
        if (searchDto.isPresent()) {
            dto = searchDto.get();
        }
        else {
            PagingRequestDto defaultDto = new PagingRequestDto();
            defaultDto.setSearchType("n"); // 성명
            defaultDto.setSearchKeyword("유저");
            dto = defaultDto;
        }

        PagingResultDto<SecuredSampleDto, SecuredSample> result = userService.findPaginatedWithPageable(dto);

        return GenericResponse.success("success", result);
    }

    // read - one
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseBody> read(@PathVariable("id") Long id) {
        SecuredSample user = userService.selectOne(id);
        SecuredSampleDto userDto = new SecuredSampleDto();
        BeanUtils.copyProperties(user, userDto);
        return GenericResponse.success("success", userDto);
    }

    @GetMapping("/countries")
    public ResponseEntity<GenericResponseBody> getCountries() {
        return GenericResponse.success("success", Helper.getCountries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseBody> update(
            @Valid final SecuredSampleDto userDto) {
        LOGGER.debug("수정 요청된 사용자 정보: {}", userDto);
        String invalidError = userValidationService.validateUser(userDto);

        if (!invalidError.isEmpty()) {
            return GenericResponse.fail("실패");
        }

        userService.update(userDto);

        return GenericResponse.success("사용자 수정 완료!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseBody> delete(@PathVariable("id") Long id) {
        SecuredSample user = userService.selectOne(id);
        if (user == null) {
            throw new ResourceNotFoundException("SecuredSample", "id", id);
        }
        userService.delete(id);

        return GenericResponse.success("사용자 삭제 완료!");
    }

    @PutMapping("/{id}/deactivate") // 계정 비활성화
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseBody> deactivateUserById(@PathVariable(value = "id") Long id) {
        activateUserById(id, false);
        return GenericResponse.success("사용자 비활성화 성공!");
    }

    @PutMapping("/{id}/activate") // 계정 활성화
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseBody> activateUserById(@PathVariable(value = "id") Long id) {
        activateUserById(id, true);
        return GenericResponse.success("사용자 활성화 성공!");
    }

    @PutMapping("/{id}/disable2fa") // 2FA 비활성화
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseBody> disable2faUserById(@PathVariable(value = "id") Long id) {
        enable2faUserById(id, false);
        return GenericResponse.success("Google 2FA 비활성화 성공!");
    }

    @PutMapping("/{id}/enable2fa") // 2FA 활성화
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenericResponseBody> enable2faUserById(@PathVariable(value = "id") Long id) {
        enable2faUserById(id, true);
        return GenericResponse.success("Google 2FA 활성화 성공!");
    }

    private void activateUserById(Long id, Boolean activated) {
        SecuredSample user = userService.selectOne(id);
        if (user == null) {
            throw new ResourceNotFoundException("SecuredSample", "id", id);
        }
        userService.updateEnabled(id, activated);
    }

    private void enable2faUserById(Long id, Boolean enabled) {
        SecuredSample user = userService.selectOne(id);
        if (user == null) {
            throw new ResourceNotFoundException("SecuredSample", "id", id);
        }
        SecuredSampleDto userDto = new SecuredSampleDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setIsUsing2FA(enabled);
        userService.update(userDto);
    }

}
