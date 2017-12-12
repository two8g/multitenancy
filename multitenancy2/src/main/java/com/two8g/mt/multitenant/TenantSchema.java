package com.two8g.mt.multitenant;

import com.two8g.mt.domain.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TenantSchema {

    @Autowired
    private DataSource dataSource;

    private void initialize(Connection connection) {
        ClassPathResource schemaResource = new ClassPathResource("tenant-schema.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schemaResource);
        populator.populate(connection);
    }

    public void initSchema(Tenant tenant) throws SQLException {
        Connection connection = dataSource.getConnection();
        if (tenant != null) {
            connection.createStatement().execute("CREATE SCHEMA " + tenant.getName() + ";" + "USE " + tenant.getName());
            initialize(connection);
        }
    }
}
