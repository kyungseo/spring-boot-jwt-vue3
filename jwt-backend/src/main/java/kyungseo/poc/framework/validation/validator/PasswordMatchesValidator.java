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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kyungseo.poc.framework.auth.payload.UserDto;
import kyungseo.poc.framework.validation.annotation.PasswordMatches;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private Boolean allowNull;

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto value = (UserDto) obj;
        String password = value.getPassword();
        String matchingPassword = value.getMatchingPassword();
        if (allowNull) {
            return null == password && null == matchingPassword;
        }
        return password.equals(matchingPassword);
    }

}
