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

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class RegexUtil {

    // 폰 번호
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    // 이메일
    //public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // URL
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    // IP 주소
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    public static boolean isMobile(String mobile) {
        return StringUtils.isNotBlank(mobile) && Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean isEmail(String email) {
        return StringUtils.isNotBlank(email) && Pattern.matches(REGEX_EMAIL, email);
    }

    public static boolean isUrl(String url) {
        return StringUtils.isNotBlank(url) && Pattern.matches(REGEX_URL, url);
    }

    public static boolean isIPAddr(String ipAddr) {
        return StringUtils.isNotBlank(ipAddr) && Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

}
