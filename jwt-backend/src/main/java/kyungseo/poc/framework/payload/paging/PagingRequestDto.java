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

package kyungseo.poc.framework.payload.paging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kyungseo.poc.framework.config.EnvironmentAwareConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PagingRequestDto {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Integer pageNum;

    private Integer pageSize;

    private String searchType;

    private String searchKeyword;

    public PagingRequestDto() {
        this.pageNum = 1;
        this.pageSize = Integer.parseInt(
                EnvironmentAwareConfig.getProperty("app.paging.size.default"));
    }

    public Integer getLimit() {
        return pageSize;
    }

    public Integer getOffset() {
        return pageSize * pageNum - pageSize;
    }

    public Pageable getPageable() {
        LOGGER.debug("> pageNum: " + pageNum);
        LOGGER.debug("> pageSize: " + pageSize);
        return PageRequest.of(pageNum -1, pageSize);
    }

    public Pageable getPageable(Sort sort) {
        LOGGER.debug("> pageNum: " + pageNum);
        LOGGER.debug("> pageSize: " + pageSize);
        return PageRequest.of(pageNum -1, pageSize, sort);
    }

}
