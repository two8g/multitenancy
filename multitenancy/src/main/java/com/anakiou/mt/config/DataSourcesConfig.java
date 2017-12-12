package com.anakiou.mt.config;

import com.anakiou.mt.MultiTenantConstants;
import com.anakiou.mt.domain.Employee;
import com.anakiou.mt.multitenant.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.anakiou.mt.multitenant.TenantIdentifierResolver;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourcesConfig {
    @Autowired
    DataSourceBasedMultiTenantConnectionProviderImpl dsProvider;

    @Autowired
    TenantIdentifierResolver tenantResolver;

    /**
     * Creates the default "master" datasource
     *
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Configures the Hibernate JPA service with multi-tenant support enabled.
     *
     * @param builder
     * @return
     */
    @PersistenceContext
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> props = new HashMap<>();
        props.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, dsProvider);
        props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);

        return builder.dataSource(dataSource())
                .persistenceUnit(MultiTenantConstants.TENANT_KEY)
                .properties(props)
                .packages(Employee.class.getPackage().getName()).build();
    }

}
