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

package kyungseo.poc.framework.validation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kyungseo.poc.framework.auth.payload.UserDto;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object obj, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "membername", "sec.message.name", "사용자명은 필수 값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "password", "sec.message.password", "비밀번호는 필수 값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "username", "sec.message.username", "아이디/이메일은 필수 값입니다.");
    }

}
