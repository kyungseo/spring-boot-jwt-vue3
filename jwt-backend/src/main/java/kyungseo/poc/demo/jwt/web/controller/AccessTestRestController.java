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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kyungseo.poc.AppConstants;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AppConstants.API_JWT_URI_PREFIX + "/access/test")
public class AccessTestRestController {

    @GetMapping("/public")
    public String publicAccess() {
        return "익명 사용자(Anonymous User)를 포함한 모든 사용자가 접근할 수 있는 컨텐츠입니다.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public String userAccess() {
        return "USER(일반 사용자) 또는 STAFF 또는 ADMIN 등의 권한을 보유한 사용자가 접근할 수 있는 컨텐츠입니다.";
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('STAFF')")
    public String moderatorAccess() {
        return "STAFF 권한을 보유한 사용자가 접근할 수 있는 컨텐츠입니다.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "ADMIN(관리자) 권한을 보유한 사용자가 접근할 수 있는 컨텐츠입니다.";
    }

}
