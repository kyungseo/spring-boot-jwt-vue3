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

package kyungseo.poc.demo.jwt.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import kyungseo.poc.demo.jwt.persistence.model.SecuredSample;
import kyungseo.poc.framework.payload.paging.PagingRequestDto;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Mapper
public interface SecuredSampleMapper {

    List<SecuredSample> selectAll();

    /*
    @Select("select * from user_account")
    @Results({
            @Result(property = "username", column = "username", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password")
    })
    List<SecuredSample> selectAll();
    */

    List<SecuredSample> selectList(PagingRequestDto pagingRequestDto);

    Integer totalCount(PagingRequestDto pagingRequestDto);

    SecuredSample selectOne(Long id);

    /*
    @Select("select * from user_account where id = #{id}")
    @Results({
            @Result(property = "username", column = "username", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password")
    })
    SecuredSample selectOne(Long id);
    */

    SecuredSample selectByEmail(@Param("email") String email);

    void insert(SecuredSample user);

    /*
    @Insert("insert into user_account(username, password) values(#{username}, #{password})")
    void insert(SecuredSample user);
    */

    void batchInsert(List<SecuredSample> userList);

    void update(SecuredSample user);

    /*
    @Update("update user_account set username=#{username}, password=#{password} where id = #{id}")
    void update(SecuredSample user);
    */

    void batchUpdate(List<SecuredSample> userList);

    @Update("update user_account set enabled=#{enabled} where id = #{id}")
    void updateEnabled(Long id, Boolean enabled);

    @Delete("delete from users_roles where user_id = #{id}")
    void deleteUserRoles(Long id);

    void batchDeleteUserRoles(List<SecuredSample> userList);

    void delete(Long id);

    /*
    @Delete("delete from user_account where id = #{id}")
    void delete(Long id);
    */

    void batchDelete(List<SecuredSample> userList);

}
