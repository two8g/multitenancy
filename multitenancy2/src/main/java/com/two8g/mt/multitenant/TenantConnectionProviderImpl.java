package com.two8g.mt.multitenant;

import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Startable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

import static com.two8g.mt.MultiTenantConstants.DEFAULT_TENANT_ID;

@Component
public class TenantConnectionProviderImpl extends AbstractMultiTenantConnectionProvider implements Startable {

    @Autowired
    private DataSource dataSource;
    private final DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();

    @Override
    public void start() {
        connectionProvider.configure(Collections.singletonMap(Environment.DATASOURCE, dataSource));
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProvider;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        try {
            connection.createStatement().execute("USE " + tenantIdentifier);
        } catch (SQLException e) {
            connection.createStatement().execute("USE " + DEFAULT_TENANT_ID);
        }
        return connection;
    }

}