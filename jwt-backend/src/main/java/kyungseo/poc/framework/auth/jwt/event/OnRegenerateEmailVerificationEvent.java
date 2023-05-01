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

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.entity.VerificationToken;

public class OnRegenerateEmailVerificationEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private transient UriComponentsBuilder redirectUrl;

    private User user;

    private transient VerificationToken token;

    public OnRegenerateEmailVerificationEvent(User user, UriComponentsBuilder redirectUrl, VerificationToken token) {
        super(user);
        this.user = user;
        this.redirectUrl = redirectUrl;
        this.token = token;
    }

    public UriComponentsBuilder getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }

}
