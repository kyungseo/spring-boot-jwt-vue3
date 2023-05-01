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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Data
public class PagingResultDto<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int pageNum;

    // 목록 사이즈
    private int pageSize;

    // 시작 페이지 번호
    private int start;

    // 끝 페이지 번호
    private int end;

    // 이전
    private boolean prev;

    // 다음
    private boolean next;

    //페이지 번호 목록
    private List<Integer> pageList;

    public PagingResultDto(Page<EN> result, Function<EN,DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        pageNum = result.getPageable().getPageNumber() + 1; // 0 부터 시작하므로 1을 추가
        pageSize = result.getPageable().getPageSize();

        // temp end page
        int tempEnd = (int) (Math.ceil(pageNum / 10.0)) * 10;

        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
