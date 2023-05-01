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
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kyungseo.poc.demo.jwt.payload.SecuredSampleDto;
import kyungseo.poc.demo.jwt.persistence.mapper.SecuredSampleMapper;
import kyungseo.poc.demo.jwt.persistence.model.SecuredSample;
import kyungseo.poc.framework.payload.paging.PagingRequestDto;
import kyungseo.poc.framework.payload.paging.PagingResultDto;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Service
//@Transactional
public class SecuredSampleServiceImpl implements SecuredSampleService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public static ModelMapper modelMapper;

    @Autowired
	private SecuredSampleMapper userMapper;

    @Override
    public List<SecuredSample> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public PagingResultDto<SecuredSampleDto, SecuredSample> findPaginatedWithPageable(PagingRequestDto pagingRequestDto) {
        LOGGER.debug("pagingRequestDto.getPageNum: " + pagingRequestDto.getPageNum());

        Pageable pageable = pagingRequestDto.getPageable();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        List<SecuredSample> list = userMapper.selectList(pagingRequestDto);
        int totalCount = userMapper.totalCount(pagingRequestDto);

        LOGGER.debug("pageSize: " + pageSize);
        LOGGER.debug("currentPage: " + currentPage);
        LOGGER.debug("totalCount: " + totalCount);
        LOGGER.debug("list.size(): " + list.size());

        Page<SecuredSample> userPage = new PageImpl<SecuredSample>(list, PageRequest.of(currentPage, pageSize), totalCount);
        Function<SecuredSample, SecuredSampleDto> fn = (entity -> convertToDto(entity));

        return new PagingResultDto<>(userPage, fn );
    }

    @Override
    public SecuredSample selectOne(final Long id) {
        return this.userMapper.selectOne(id);
    }

    @Override
    public SecuredSample selectByEmail(final String email) {
        return this.userMapper.selectByEmail(email);
    }

    /*
    @Override
    public void insert(SecuredSampleDto user) {
        this.userMapper.insert(dtoToEntity(user));
    }
    */

    /*
    @Override
    public void batchInsert(List<SecuredSampleDto> userList) {
        this.userMapper.batchInsert(toAdmUserList(userList));
    }
    */

    @Override
    public void update(SecuredSampleDto user) {
        this.userMapper.update(convertToEntity(user));
    }

    @Override
    public void batchUpdate(List<SecuredSampleDto> userList) {
        this.userMapper.batchUpdate(toAdmUserList(userList));
    }

    @Override
    public void updateEnabled(Long id, Boolean enabled) {
        this.userMapper.updateEnabled(id, enabled);
    }

    @Override
    public void delete(final Long id) {
        this.userMapper.deleteUserRoles(id);
        this.userMapper.delete(id);
    }

    @Override
    public void batchDelete(List<SecuredSampleDto> userList) {
        this.userMapper.batchDeleteUserRoles(toAdmUserList(userList));
        this.userMapper.batchDelete(toAdmUserList(userList));
    }

}
