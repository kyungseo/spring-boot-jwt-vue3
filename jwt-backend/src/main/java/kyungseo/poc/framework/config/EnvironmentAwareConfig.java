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

package kyungseo.poc.framework.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Spring의 autowiring이 너무 늦게 발생하는 경우 Environment를 사용하기 위한 트릭!
 *   -> org.springframework.context.EnvironmentAware를 구현
 *   -> Spring 3.1 부터 작동
 *
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
public class EnvironmentAwareConfig implements EnvironmentAware {

    private static Environment env;

    @Override
    public void setEnvironment(final Environment env) {
        EnvironmentAwareConfig.env = env;
    }

    public static String getProperty(String key) {
        return env.getProperty(key);
    }

}
