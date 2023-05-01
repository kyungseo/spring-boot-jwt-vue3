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

import kyungseo.poc.framework.validation.annotation.EnumValid;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (hasEnumValue(valueForValidation, enumValue, this.annotation.ignoreCase())) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    private boolean hasEnumValue(String valueForValidation, Object enumValue, boolean ignoreCase) {
        return valueForValidation == null
                || valueForValidation.equals(enumValue.toString())
                || (ignoreCase && valueForValidation.equalsIgnoreCase(enumValue.toString()));
    }

}
