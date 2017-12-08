package com.anakiou.mt.web;

import com.anakiou.mt.domain.Tenant;
import com.anakiou.mt.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private TenantRepository tenantRepository;

    @RequestMapping
    public String index(Model model) {
        model.addAttribute("tenant", new Tenant());
        model.addAttribute("tenants", tenantRepository.findAll());
        return "tenants";
    }
}
