package com.anakiou.mt.util;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final long serialVersionUID = 8168907057647334460L;
    private static final String DEFAULT_TENANT_ID = "Default";
    @Autowired
    private TenantDataSourceProvider tenantDataSourceProvider;

    @Override
    protected DataSource selectAnyDataSource() {
        return tenantDataSourceProvider.getTenantDataSource(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return tenantDataSourceProvider.getTenantDataSource(tenantIdentifier);
    }
}
