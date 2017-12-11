package com.anakiou.mt.multitenant;

import com.anakiou.mt.domain.Tenant;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.anakiou.mt.MultiTenantConstants.TEST_TENANT_ID;

@Component
public class TenantDataSource implements Serializable {

    private Set<String> tenants = new HashSet<>();
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        return dataSources.get(TEST_TENANT_ID);
    }

    private void initialize(DataSource dataSource) {
        ClassPathResource schemaResource = new ClassPathResource("tenant-schema.sql");
        ClassPathResource dataResource = new ClassPathResource("tenant-data.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource, dataResource);
        populator.execute(dataSource);
    }

    public boolean exist(String name) {
        return tenants.contains(name);
    }

    public void addDataSource(Tenant tenant) {
        if (tenant != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName("org.h2.Driver")
                    .username(tenant.getUsername())
                    .password(tenant.getPassword())
                    .url(tenant.getUrl());
            DataSource ds = factory.build();
            initialize(ds);
            dataSources.put(tenant.getName(), ds);
            tenants.add(tenant.getName());
        }
    }
}
