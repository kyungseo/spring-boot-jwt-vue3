/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 *
 * Original Code: https://github.com/isopropylcyanide/Jwt-Spring-Security-JPA
 * modified by Kyungseo Park
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

package kyungseo.poc.framework.auth.jwt.event;

import java.time.Instant;
import java.util.Date;

import org.springframework.context.ApplicationEvent;

import kyungseo.poc.framework.auth.jwt.payload.request.LogOutRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnUserLogoutSuccessEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final String userEmail;

    private final String token;

    private final transient LogOutRequest logOutRequest;

    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String userEmail, String token, LogOutRequest logOutRequest) {
        super(userEmail);
        this.userEmail = userEmail;
        this.token = token; // Access Token
        this.logOutRequest = logOutRequest;
        this.eventTime = Date.from(Instant.now());
    }

}
