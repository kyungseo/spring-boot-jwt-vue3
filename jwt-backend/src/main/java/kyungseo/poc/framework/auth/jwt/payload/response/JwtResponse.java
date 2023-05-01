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

package kyungseo.poc.framework.auth.jwt.payload.response;

import kyungseo.poc.framework.config.EnvironmentAwareConfig;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class JwtResponse {

    private String tokenType; // "Bearer" (공백 없음 주의!)

    private String accessToken;

    private String refreshToken;

    private String roles; // Comma로 분리된 Role 문자열

    private Long expiryDuration;

    // user infos

    private Long id;
    private String userName;
    private String userRealName;
    private String userEmail;

    public JwtResponse(String accessToken, String refreshToken, String roles, Long expiryDuration,
            Long id, String userName, String userRealName, String userEmail) {
        super();
        this.tokenType = EnvironmentAwareConfig.getProperty("token.jwt.header.prefix");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
        this.expiryDuration = expiryDuration;
        this.id = id;
        this.userName = userName;
        this.userRealName = userRealName;
        this.userEmail = userEmail;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType + " ";
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(Long expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
