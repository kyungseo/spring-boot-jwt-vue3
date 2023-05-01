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

package kyungseo.poc.framework.auth.jwt.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kyungseo.poc.framework.auth.core.persistence.entity.User;
import kyungseo.poc.framework.auth.core.persistence.repository.UserRepository;
import kyungseo.poc.framework.auth.jwt.payload.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> dbUser = Optional.ofNullable(userRepository.findByEmail(email));
        LOGGER.info("Fetched user : " + dbUser + " by " + email);
        return dbUser.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("데이터베이스에서 일치하는 사용자 이메일을 찾을 수 없습니다. - " + email));
    }

    public UserDetails loadUserById(Long id) {
        Optional<User> dbUser = userRepository.findById(id);
        LOGGER.info("Fetched user : " + dbUser + " by " + id);
        return dbUser.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("데이터베이스에서 일치하는 사용자 Id를 찾을 수 없습니다. " + id));
    }

}
