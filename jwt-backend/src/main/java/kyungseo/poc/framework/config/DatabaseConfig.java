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

package kyungseo.poc.framework.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;

import kyungseo.poc.AppConstants;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
public abstract class DatabaseConfig extends HikariConfig {

    public static final String DATASOURCE_1_NAME_PREFIX        = "ds1";
    public static final String DATASOURCE_2_NAME_PREFIX        = "ds2";

    public static final String JPA_REPOSITORY_PACKAGE_PREFIX   = AppConstants.SCAN_BASE_PACKAGE + ".**.repository";
    public static final String JPA_ENTITY_PACKAGE_PREFIX       = AppConstants.SCAN_BASE_PACKAGE + ".**.entity";

    public static final String MYBATIS_MAPPER_PACKAGE_PREFIX   = AppConstants.SCAN_BASE_PACKAGE + ".**.mapper";
    public static final String MYBATIS_MAPPER_LOCATION_PATTERN = "classpath*:mybatis/mapper/**/*Mapper.xml";

    // JPA ================================================

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    // 주의! Production 환경인 경우 무조건 'none' 값으로 설정할 것!
    @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
    private String hibernateDdlAuto;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private String hibernateFormatSql;

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    protected void setConfigureEntityManagerFactory(LocalContainerEntityManagerFactoryBean factory) {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaPropertyMap(ImmutableMap.of(
                "hibernate.hbm2ddl.auto", hibernateDdlAuto,
                "hibernate.dialect", hibernateDialect,
                "hibernate.show_sql", showSql,
                "hibernate.format_sql", hibernateFormatSql
        ));
        factory.afterPropertiesSet();
    }

    // MyBatis ============================================

    protected void setConfigureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resolver.getResources(MYBATIS_MAPPER_LOCATION_PATTERN));
    }

}