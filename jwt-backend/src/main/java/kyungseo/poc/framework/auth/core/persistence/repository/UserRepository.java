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

package kyungseo.poc.framework.auth.core.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kyungseo.poc.framework.auth.core.persistence.entity.User;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);

    //User findByUsername(String username);

    //Boolean existsByUsername(String username);

    Page<User> findByMembernameContainingOrEmailContaining(String membername, String email, Pageable pageable);

    Page<User> findByEmailContaining(String email, Pageable pageable);

    Page<User> findByMembernameContaining(String membername, Pageable pageable);

    @Override
    void delete(User user);

}
