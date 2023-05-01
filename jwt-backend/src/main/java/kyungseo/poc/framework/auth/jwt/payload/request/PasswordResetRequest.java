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

import javax.validation.constraints.NotBlank;

import kyungseo.poc.framework.validation.annotation.MatchPassword;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@MatchPassword
public class PasswordResetRequest {

    @NotBlank(message = "비밀번호를 재설정하는데 필요한 이메일은 비워둘 수 없습니다.")
    private String email;

    @NotBlank(message = "새로운 비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "확인 비밀번호는 필수 항목입니다.")
    private String confirmPassword;

    @NotBlank(message = "지정된 이메일에 대한 비밀번호 재설정 Token이 필요합니다.")
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
