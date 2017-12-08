package com.anakiou.mt.repository;

import com.anakiou.mt.domain.Tenant;
import org.springframework.data.repository.CrudRepository;

public interface TenantRepository extends CrudRepository<Tenant, Long> {

}
