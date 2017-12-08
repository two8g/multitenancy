package com.anakiou.mt;

import com.anakiou.mt.domain.Tenant;
import com.anakiou.mt.repository.TenantRepository;
import com.anakiou.mt.util.TenantDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private TenantDataSourceProvider tenantDataSourceProvider;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (Tenant tenant : tenantRepository.findAll()) {
            tenantDataSourceProvider.addDataSource(tenant);
        }
    }
}
