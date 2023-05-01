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
import org.springframework.validation.Validator;

import kyungseo.poc.framework.payload.file.FileBucket;
import kyungseo.poc.framework.payload.file.MultiFileBucket;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class MultiFileValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return MultiFileBucket.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		MultiFileBucket multiBucket = (MultiFileBucket) obj;

		int index = 0;

		for (FileBucket file : multiBucket.getFiles()) {
			if (file.getFile() != null && file.getFile().getSize() == 0) {
				errors.rejectValue("files[" + index + "].file", "missing.file");
			}
			index++;
		}
	}

}