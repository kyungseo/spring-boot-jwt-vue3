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

package kyungseo.poc.framework.auth.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kyungseo.poc.framework.validation.annotation.PasswordMatches;
import kyungseo.poc.framework.validation.annotation.ValidEmail;
import kyungseo.poc.framework.validation.annotation.ValidPassword;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@PasswordMatches
public class UserDto {

    @NotNull
    @Size(min = 1, message = "{valid.user.name.size}")
    private String membername;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{valid.user.email.size}")
    private String email;

    private Boolean enabled;

    private Integer age;

    private String phoneNumber;

    private String country;

    private String birthdate;

    private boolean isUsing2FA;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(final String membername) {
        this.membername = membername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(boolean isUsing2FA) {
        this.isUsing2FA = isUsing2FA;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDTO [membername=")
                .append(membername)
                .append(", email=")
                .append(email)
                .append(", age=")
                .append(age)
                .append(", phoneNumber=")
                .append(phoneNumber)
                .append(", country=")
                .append(country)
                .append(", birthdate=")
                .append(birthdate)
                .append(", enabled=")
                .append(enabled)
                .append(", isUsing2FA=")
                .append(isUsing2FA)
                .append(", role=")
                .append(role).append("]");
        return builder.toString();
    }

}
