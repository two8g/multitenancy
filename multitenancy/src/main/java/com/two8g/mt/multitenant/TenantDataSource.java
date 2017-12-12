package com.two8g.mt.multitenant;

import com.two8g.mt.MultiTenantConstants;
import com.two8g.mt.domain.Tenant;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class TenantDataSource implements Serializable {

    private Set<String> tenants = new HashSet<>();
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        return dataSources.get(MultiTenantConstants.TEST_TENANT_ID);
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
            DataSourceBuilder factory = DataSourceBuilder.create()
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
