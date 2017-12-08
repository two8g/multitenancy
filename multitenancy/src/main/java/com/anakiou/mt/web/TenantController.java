package com.anakiou.mt.web;

import com.anakiou.mt.domain.Tenant;
import com.anakiou.mt.repository.TenantRepository;
import com.anakiou.mt.util.TenantDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tenants")
public class TenantController {
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private TenantDataSourceProvider tenantDataSourceProvider;

    @RequestMapping
    public String tenants(Model model) {
        model.addAttribute("tenants", tenantRepository.findAll());
        return "tenants";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTenant(@ModelAttribute Tenant tenant, Model model) {
        tenantRepository.save(tenant);
        tenantDataSourceProvider.addDataSource(tenant);
        return "redirect:/tenants";
    }
}
