package com.two8g.mt.web;

import com.two8g.mt.MultiTenantConstants;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.two8g.mt.MultiTenantConstants.TENANT_KEY;


public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		if (pathVars.containsKey(TENANT_KEY)) {
			req.setAttribute(MultiTenantConstants.CURRENT_TENANT_IDENTIFIER, pathVars.get(TENANT_KEY));
		}
		return true;
	}
}
