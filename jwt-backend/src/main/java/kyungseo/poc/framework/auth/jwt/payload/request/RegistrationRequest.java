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

package kyungseo.poc.framework.auth.jwt.payload.request;

import javax.validation.constraints.NotNull;

import kyungseo.poc.framework.validation.annotation.NullOrNotBlank;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class RegistrationRequest {

    @NullOrNotBlank(message = "Registration username은 null일 수 있지만 비워둘 수는 없습니다.")
    private String username;

    @NullOrNotBlank(message = "Registration email은 null일 수 있지만 비워둘 수는 없습니다.")
    private String email;

    @NotNull(message = "Registration Password는 필수 항목입니다.")
    private String password;

    @NotNull(message = "사용자가 관리자로 등록되어야 하는지 여부를 지정합니다.")
    private Boolean registerAsAdmin = false;

    public RegistrationRequest(String username, String email,
                               String password, Boolean registerAsAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerAsAdmin = registerAsAdmin;
    }

    public RegistrationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRegisterAsAdmin() {
        return registerAsAdmin;
    }

    public void setRegisterAsAdmin(Boolean registerAsAdmin) {
        this.registerAsAdmin = registerAsAdmin;
    }

}
