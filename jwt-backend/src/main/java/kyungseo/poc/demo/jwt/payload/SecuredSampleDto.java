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

package kyungseo.poc.demo.jwt.payload;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kyungseo.poc.framework.auth.core.persistence.entity.Role;
import kyungseo.poc.framework.validation.ValidationMarkers.Create;
import kyungseo.poc.framework.validation.ValidationMarkers.Update;
import kyungseo.poc.framework.validation.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecuredSampleDto {

    @NotNull
    private Long id;

    @NotEmpty(groups = {Create.class, Update.class}, message = "${valid.user.name.notempty}")
    @Size(min = 2, max = 20, message = "{valid.user.name.size}")
    private String membername;

    private String username; // Spring Security의 'username'

    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Size(min = 5, max = 128, message = "{valid.user.email.size}")
    @ValidEmail
    private String email;

    //@ValidPassword
    private String password;

    @NotNull(message = "나이는 필수 입력 값입니다.")
    @Min(value = 18)
    private Integer age;

    private String phoneNumber;

    private String country;

    private String birthdate;

    private Boolean enabled;

    private Collection<Role> roles;

    private Boolean isEmailVerified;

    private Boolean isUsing2FA;

    private String secret;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATETIME_FORMAT, timezone = AppConstants.TIMEZONE)
    //@DateTimeFormat(pattern = AppConstants.DATETIME_FORMAT)
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String date;

    /*
    public Date getSubmissionDateConverted(String timezone) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(this.date);
    }

    public void setSubmissionDate(Date date, String timezone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.date = dateFormat.format(date);
    }
    */

}
