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

package kyungseo.poc;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public interface AppConstants {

    public static final String SCAN_BASE_PACKAGE = "kyungseo.poc";
    public static final String TIMEZONE = "Asia/Seoul";
    public static final String DATE_FORMAT = "yyyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Locale 변경을 위한 Parameter name
    public static final String LOCALE_CHANGE_PARAM_NAME = "lang";

    // Double Submit 방지를 위한 request id 키값
    public static final String REQUEST_ID = "_requestId_";

    // ------------------------------------------------------------------------
    // APIs
    // ------------------------------------------------------------------------

    // JWT API 접두어
    public static final String API_JWT_URI_PREFIX = "/api/v1/jwt";

    // ------------------------------------------------------------------------
    // View Pages
    // ------------------------------------------------------------------------

    // Home
    public static final String HOME_VIEW_NAME  = "welcome"; // "index"

    // Error Veiw Name & Page URLs
    public static final String ERROR_VIEW_NAME = "common/error";

    public static final String ERROR_400_URL   = "/common/error/400";
    public static final String ERROR_401_URL   = "/common/error/401";
    public static final String ERROR_403_URL   = "/common/error/403";
    public static final String ERROR_404_URL   = "/common/error/404";
    public static final String ERROR_405_URL   = "/common/error/405";
    public static final String ERROR_409_URL   = "/common/error/409";
    public static final String ERROR_500_URL   = "/common/error/500";

}