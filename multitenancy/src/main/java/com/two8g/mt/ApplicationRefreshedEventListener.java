package com.two8g.mt;

import com.two8g.mt.domain.Tenant;
import com.two8g.mt.multitenant.TenantDataSource;
import com.two8g.mt.repository.TenantDataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TenantDataSourceRepository tenantDataSourceRepository;
    @Autowired
    private TenantDataSource tenantDataSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (Tenant tenant : tenantDataSourceRepository.findAll()) {
            tenantDataSource.addDataSource(tenant);
        }
    }
}
