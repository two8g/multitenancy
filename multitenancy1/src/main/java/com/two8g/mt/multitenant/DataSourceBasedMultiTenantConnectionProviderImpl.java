package com.two8g.mt.multitenant;

import com.two8g.mt.MultiTenantConstants;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    private TenantDataSource tenantDataSource;
    @Autowired
    private DataSource dataSource;

    @Override
    protected DataSource selectAnyDataSource() {
        return dataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (tenantIdentifier.equals(MultiTenantConstants.DEFAULT_TENANT_ID)) {
            return dataSource;
        }
        return tenantDataSource.getDataSource(tenantIdentifier);
    }
}