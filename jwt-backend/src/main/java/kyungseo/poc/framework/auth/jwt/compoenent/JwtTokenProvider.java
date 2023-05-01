/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 *
 * Original Code: https://github.com/isopropylcyanide/Jwt-Spring-Security-JPA
 * modified by Kyungseo Park
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

package kyungseo.poc.framework.auth.jwt.compoenent;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;

@Component
public class JwtTokenProvider {

    private final String AUTHORITIES_CLAIM = "authorities";

    private final String COOKIE_PATH_API = "/api";

    private final String REFRESH_TOKEN_API = AppConstants.API_JWT_URI_PREFIX + "/auth/refresh";

    private final String jwtSecret;

    private final long jwtAccessExpirationInMs;

    private final long jwtRefreshExpirationInMs;

    private final String jwtCookie;

    private final String jwtRefreshCookie;

    private final String jwtIssuer;

    public JwtTokenProvider(
            @Value("${token.key.secret}") String jwtSecret,
            @Value("${token.access.expire.time}") long jwtAccessExpirationInMs,
            @Value("${token.refresh.expire.time}") long jwtRefreshExpirationInMs,
            @Value("${token.access.cookie.name}") String jwtCookie,
            @Value("${token.refresh.cookie.name}") String jwtRefreshCookie,
            @Value("${token.jwt.issuer}") String jwtIssuer) {
        this.jwtSecret = jwtSecret;
        //this.jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        this.jwtAccessExpirationInMs = jwtAccessExpirationInMs;
        this.jwtRefreshExpirationInMs = jwtRefreshExpirationInMs;
        this.jwtCookie = jwtCookie;
        this.jwtRefreshCookie = jwtRefreshCookie;
        this.jwtIssuer = jwtIssuer;
    }

    /**
     * Principal object에 Token을 생성한다.
     * 새로운 access token을 생성할 수 있도록 JWT에 refresh token을 포함
     */
    public String generateToken(CustomUserDetails customUserDetails) {
        Instant expiryDate = Instant.now().plusMillis(jwtAccessExpirationInMs);
        String authorities = getUserAuthorities(customUserDetails);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(Long.toString(customUserDetails.getId()))
                .setIssuedAt(Date.from(Instant.now()))
                .setIssuer(jwtIssuer)
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim(AUTHORITIES_CLAIM, authorities)
                .compact();
    }

    /**
     * Principal object에 Token을 생성한다.
     * 새로운 access token을 생성할 수 있도록 JWT에 refresh token을 포함
     */
    public String generateTokenFromUserId(Long userId) {
        Instant expiryDate = Instant.now().plusMillis(jwtAccessExpirationInMs);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(Long.toString(userId))
                .setIssuedAt(Date.from(Instant.now()))
                .setIssuer(jwtIssuer)
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Token 내에 캡슐화된 사용자 ID를 반환
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Token 내에 캡슐화된 만료 일자를 반환
     */
    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    /**
     * Access Token을 적절하게 실행할 수 있도록 만료 기간을 반환
     */
    public long getExpiryDuration() {
        return jwtAccessExpirationInMs;
    }

    /**
     * Refresh Token을 적절하게 실행할 수 있도록 만료 기간을 반환
     */
    public long getRefreshExpiryDuration() {
        return this.jwtRefreshExpirationInMs;
    }

    public String getRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(AUTHORITIES_CLAIM).toString();
    }

    /**
     * Token 내에 캡슐화된 Authorities Claim을 반환
     */
    public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        return Arrays.stream(getRolesFromJWT(token).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getUserAuthorities(CustomUserDetails customUserDetails) {
        return customUserDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // Cookies

    public ResponseCookie generateJwtCookie(CustomUserDetails userPrincipal) {
        String jwt = generateTokenFromUserId(userPrincipal.getId());
        return generateCookie(jwtCookie, jwt, COOKIE_PATH_API);
    }

    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateTokenFromUserId(user.getId());
        return generateCookie(jwtCookie, jwt, COOKIE_PATH_API);
    }

    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken, REFRESH_TOKEN_API);
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path(COOKIE_PATH_API).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path(REFRESH_TOKEN_API).build();
        return cookie;
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie
                .from(name, value)
                .path(path)
                .maxAge(24 * 60 * 60) // TODO properties로 분리
                .httpOnly(true)
                .build();
        return cookie;
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

}
