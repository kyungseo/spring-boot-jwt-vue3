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

package kyungseo.poc.framework.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Service
public class Helper {

    public static LinkedList<LinkedHashMap<String, String>> refineErrors(Errors errors) {
        LinkedList errorList = new LinkedList<LinkedHashMap<String, String>>();

        errors.getFieldErrors().forEach(e-> {
            LinkedHashMap<String, String> error = new LinkedHashMap<>();
            error.put("field", e.getField());
            error.put("message", e.getDefaultMessage());
            errorList.push(error);
        });

        return errorList;
    }

    public static Map<String, String> getCountries() {
        String[] locales = Locale.getISOCountries();
        Map<String, String> countries = new HashMap<>(500);
        //Locale outLocale = new Locale("ko", "KR");
        Locale outLocale = new Locale("EN", "us");
        for (String countryCode : locales) {
          //Locale obj = new Locale("ko-KR", countryCode);
          Locale obj = new Locale("en-us", countryCode);
          countries.put(obj.getCountry(), obj.getDisplayCountry(outLocale));
        }
        return countries;
    }

}
