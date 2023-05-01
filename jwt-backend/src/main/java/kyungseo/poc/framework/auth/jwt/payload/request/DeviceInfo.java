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
import javax.validation.constraints.NotNull;

import kyungseo.poc.framework.auth.core.model.DeviceType;
import kyungseo.poc.framework.validation.annotation.NullOrNotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class DeviceInfo {

    @NotBlank(message = "Device id 항목은 필수 값입니다.")
    private String deviceId;

    @NotNull(message = "Device type 항목은 필수 값입니다.")
    private DeviceType deviceType;

    @NullOrNotBlank(message = "Device 알림 토큰은 필수 값입니다.")
    private String notificationToken;

}
