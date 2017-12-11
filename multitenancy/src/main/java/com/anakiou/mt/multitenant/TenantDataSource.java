package com.anakiou.mt.multitenant;

import com.anakiou.mt.domain.Tenant;
import com.anakiou.mt.repository.TenantDataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TenantDataSource implements Serializable {

    private Set<String> tenants = new HashSet<>();
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private TenantDataSourceRepository tenantDataSourceRepository;

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            tenants.add(name);
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

    public Map<String, DataSource> getAll() {
        Map<String, DataSource> result = new HashMap<>();
        for (Tenant config : tenantDataSourceRepository.findAll()) {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        }
        return result;
    }

    public DataSource createDataSource(String name) {
        Tenant config = tenantDataSourceRepository.findByName(name);
        if (config != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName("org.h2.Driver")
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl());
            DataSource ds = factory.build();
            initialize(ds);
            return ds;
        }
        return null;
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

}
