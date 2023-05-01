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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public class RandomUtil {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String numberChar = "0123456789";

    public static String generateUUID32() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }

    public static String generateUUID() { // generateUUID36()
        return UUID.randomUUID().toString();
    }

    // 대문자와 소문자, 숫자를 포함하는 문자열 생성
    public static String generateStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    // 숫자로만 구성된 문자열 생성
    public static String generateDigitalStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }

    // 대문자와 소문자만 포함하는 문자열 생성
    public static String generateLetterStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    // 소문자로만 구성된 문자열 생성
    public static String generateLowerStr(int length) {
        return generateLetterStr(length).toLowerCase();
    }

    // 대문자로만 구성된 문자열 생성
    public static String generateUpperStr(int length) {
        return generateLetterStr(length).toUpperCase();
    }

    public static String generateZeroStr(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    // 숫자를 기반으로 문자열을 생성하고 길이가 충분하지 않으면 앞에 0을 추가
    public static String generateStrWithZero(int num, int strLength) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (strLength - strNum.length() >= 0) {
            sb.append(generateZeroStr(strLength - strNum.length()));
        } else {
            throw new RuntimeException("error!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
          .findFirst()
          .getAsInt();
    }

    public static String getRandomDateOfBirthDate(int start, int end) {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return randomBirthDate.format(dateTimeFormatter);
    }

    @Deprecated
    public static String getRandomDateOfBirth(int start, int end) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(start, end);
        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
