package com.anakiou.mt.web;

import com.anakiou.mt.domain.Tenant;
import com.anakiou.mt.multitenant.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.anakiou.mt.multitenant.TenantDataSource;
import com.anakiou.mt.repository.TenantDataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Controller
@RequestMapping("/tenants")
public class TenantController {
    @Autowired
    private TenantDataSourceRepository tenantDataSourceRepository;
    @Autowired
    private TenantDataSource tenantDataSource;
    @Resource
    private DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider;

    @RequestMapping
    public String tenants(Model model) {
        model.addAttribute("tenant", new Tenant());
        model.addAttribute("tenants", tenantDataSourceRepository.findAll());
        return "tenants";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public String addTenant(@ModelAttribute Tenant tenant) {
        tenantDataSourceRepository.save(tenant);
        tenant.setUrl("jdbc:h2:mem:" + tenant.getName());
        DataSource dataSource = tenantDataSource.createDataSource(tenant.getName());
        dataSourceBasedMultiTenantConnectionProvider.add(tenant.getName(), dataSource);
        return "redirect:/tenants";
    }
}
