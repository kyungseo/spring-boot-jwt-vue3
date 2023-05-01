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

package kyungseo.poc.demo.jwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import kyungseo.poc.demo.jwt.payload.SecuredSampleDto;
import kyungseo.poc.demo.jwt.persistence.model.SecuredSample;
import kyungseo.poc.framework.payload.paging.PagingRequestDto;
import kyungseo.poc.framework.payload.paging.PagingResultDto;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public interface SecuredSampleService {

    List<SecuredSample> selectAll();

    PagingResultDto<SecuredSampleDto, SecuredSample> findPaginatedWithPageable(PagingRequestDto pagingRequestDto);

    SecuredSample selectOne(Long id);

    SecuredSample selectByEmail(String email);

    //void insert(SecuredSampleDto user);

    //void batchInsert(List<SecuredSampleDto> userList);

    void update(SecuredSampleDto user);

    void batchUpdate(List<SecuredSampleDto> userList);

    void updateEnabled(Long id, Boolean enabled);

    void delete(Long id);

    void batchDelete(List<SecuredSampleDto> userList);

    default SecuredSample convertToEntity(SecuredSampleDto dto) {
        SecuredSample entity = SecuredSample.builder()
                .id(dto.getId())
                .membername(dto.getMembername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .age(dto.getAge())
                .phoneNumber(dto.getPhoneNumber())
                .country(dto.getCountry())
                .birthdate(dto.getBirthdate())
                .enabled(dto.getEnabled())
                .roles(dto.getRoles())
                .isUsing2FA(dto.getIsUsing2FA())
                .secret(dto.getSecret())
                .createdDate(dto.getCreatedDate())
                .modifiedDate(dto.getModifiedDate())
                .build();
        return entity;
    }

    default SecuredSampleDto convertToDto(SecuredSample entity){
        SecuredSampleDto dto  = SecuredSampleDto.builder()
                .id(entity.getId())
                .membername(entity.getMembername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .age(entity.getAge())
                .phoneNumber(entity.getPhoneNumber())
                .country(entity.getCountry())
                .birthdate(entity.getBirthdate())
                .enabled(entity.getEnabled())
                .roles(entity.getRoles())
                .isUsing2FA(entity.getIsUsing2FA())
                .secret(entity.getSecret())
                .createdDate(entity.getCreatedDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
        return dto;
    }

    default List<SecuredSample> toAdmUserList(List<SecuredSampleDto> userList) {
        if (CollectionUtils.isEmpty(userList)) return null;

        return userList.stream().map(userDto -> {
            SecuredSample user = SecuredSample.builder().build();
            BeanUtils.copyProperties(userDto, user);
            return user;
        }).collect(Collectors.toList());
    }

    default List<SecuredSampleDto> toAdmUserDtoList(List<SecuredSample> userList) {
        if (CollectionUtils.isEmpty(userList)) return null;

        return userList.stream().map(user -> {
            SecuredSampleDto userDto = SecuredSampleDto.builder().build();
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        }).collect(Collectors.toList());
    }

}
