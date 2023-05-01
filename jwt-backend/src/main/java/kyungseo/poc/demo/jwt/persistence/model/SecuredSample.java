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

package kyungseo.poc.demo.jwt.persistence.model;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.util.Collection;

import org.jboss.aerogear.security.otp.api.Base32;

import kyungseo.poc.framework.auth.core.persistence.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class SecuredSample {

    private Long id;
    private String membername;
    private String username; // Spring Security의 'username'
    private String email;
    private String password;
    private Integer age;
    private String phoneNumber;
    private String country;
    private String birthdate;
    private Boolean enabled;
    private Collection<Role> roles;
    private Boolean isEmailVerified;
    private Boolean isUsing2FA;
    private String secret;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public SecuredSample() {
        super();
        this.secret = Base32.random();
        this.enabled = false;
    }

}
