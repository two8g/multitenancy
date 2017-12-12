package com.two8g.mt.web;

import com.two8g.mt.domain.Tenant;
import com.two8g.mt.multitenant.TenantSchema;
import com.two8g.mt.repository.TenantDataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;

@Controller
@RequestMapping("/tenants")
public class TenantController {
    @Autowired
    private TenantDataSourceRepository tenantDataSourceRepository;
    @Autowired
    private TenantSchema tenantSchema;

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
        try {
            tenantSchema.initSchema(tenant);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
