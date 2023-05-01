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

package kyungseo.poc.demo.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtAuthenticationEntryPoint;
import kyungseo.poc.framework.auth.jwt.compoenent.JwtAuthenticationFilter;
import kyungseo.poc.framework.auth.jwt.service.CustomUserDetailsService;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
//@Profile("!dev")
@Configuration
@ComponentScan(basePackages = { AppConstants.SCAN_BASE_PACKAGE + ".framework.auth.jwt.compoenent" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityJwtConfig {

    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider())
            .build();
    }

    //@Bean
    //public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    //    return authConfig.getAuthenticationManager();
    //}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .antMatchers("/resources/**")
            .antMatchers("/h2/**") // h2-console 접근 경로
            .antMatchers("/vendor/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
          .and()
            .csrf().disable()
            .exceptionHandling()
            // [ JWT ] 인증에 실패한 경우: 401(UNAUTHORIZED)
            .authenticationEntryPoint(jwtEntryPoint)
            .and()
            // [ JWT ] Token 기반 인증이기 때문에 Session 사용하지 않음
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 또는 SessionCreationPolicy.NEVER
            .and()
            .authorizeRequests()
            // 요청에 대한 사용권한 체크 - START
            // ================================================================
            // 'Static Contents'의 경우, 익명 허용
            .antMatchers("/", "/index.html").permitAll()
            .antMatchers("/favicon.ico").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/img/**", "/image/**", "/images/**").permitAll()
            .antMatchers("/font/**", "/fonts/**").permitAll()
            .antMatchers("/file/**", "/files/**").permitAll()
            .antMatchers("/vendor/**").permitAll()
            .antMatchers("/configuration/**", "/api-docs/**", "/v3/api-docs/**", "/swagger*/**").permitAll() // springdoc, swagger
            .antMatchers("/webjars/**").permitAll()

            .antMatchers("/static/**").permitAll()
            // ----------------------------------------------------------------
            // API 경로들에 대한 접근권한 설정
            .antMatchers("/api/v*/jwt/auth/**").permitAll()
            .antMatchers("/api/v*/jwt/access/test/public").permitAll()

            .antMatchers("/api/v*/jwt/admin/**").hasRole("ADMIN")
            .antMatchers("/api/v*/jwt/staff/**").hasRole("STAFF")
            .antMatchers("/api/v*/jwt/user/**").hasRole("USER")
            // ----------------------------------------------------------------
            // 이하, 상기 패턴에 해당되지 않는 모든 요청들은 최소 읽기 권한 필요
            //.anyRequest().hasAuthority("READ_PRIVILEGE");
            .anyRequest().authenticated();
            // ================================================================
            // 요청에 대한 사용권한 체크 - END

        // [ JWT ] jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 삽입
        //http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // RoleHierarchy를 적용하려면 하단 주석을 제거할 것!
    //
    // 단, 제거하는 경우 sec:authorize="hasRole('USER')"이 애매해진다.
    // 왜냐하면 다음 룰에 따라
    //   "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER"
    // 어드민 사용자는 hasRole('ADMIN'), hasRole('STAFF'), hasRole('USER') 모두 true가 된다.
    //
    // Role에 따른 분기가 어려워지는 것을 피하기 위해 우선 RoleHierachy 적용을 해제함(주석 처리함)
    // 단, 이렇게 되면 반대급부로 ADMIN이 USER 역할만 접근 가능한 리소스에 접근이 불가능해지므로
    // 관리자 사용자에게는 ADMIN 외에 USER 역할을 추가적으로 할당해줘야 한다.
    /*
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
    */

}