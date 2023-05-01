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

package kyungseo.poc.framework.validation;

/**
 * Validation Markers interface for @Validated annotation
 *
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public interface ValidationMarkers {

	// Validation Marker for Create-request
	interface Create {}

	// Validation Marker for Retrieve-request
	interface Retrieve {}

	// Validation Marker for Update-request
	interface Update {}

	// Validation Marker for Delete-request
	interface Delete {}

}
