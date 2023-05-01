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

package kyungseo.poc.framework.auth.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import kyungseo.poc.framework.auth.core.model.DeviceType;

@Entity(name = "USER_DEVICE")
public class UserDevice {

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_user_device_seq")
    //@SequenceGenerator(name = "security_user_device_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "DEVICE_TYPE")
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "NOTIFICATION_TOKEN")
    private String notificationToken;

    @Column(name = "DEVICE_ID", nullable = false)
    private String deviceId;

    @OneToOne(optional = false, mappedBy = "userDevice")
    private RefreshToken refreshToken;

    @Column(name = "IS_REFRESH_ACTIVE")
    private Boolean isRefreshActive;

    public UserDevice() {
    }

    public UserDevice(Long id, User user, DeviceType deviceType, String notificationToken, String deviceId,
                      RefreshToken refreshToken, Boolean isRefreshActive) {
        this.id = id;
        this.user = user;
        this.deviceType = deviceType;
        this.notificationToken = notificationToken;
        this.deviceId = deviceId;
        this.refreshToken = refreshToken;
        this.isRefreshActive = isRefreshActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getRefreshActive() {
        return isRefreshActive;
    }

    public void setRefreshActive(Boolean refreshActive) {
        isRefreshActive = refreshActive;
    }

}
