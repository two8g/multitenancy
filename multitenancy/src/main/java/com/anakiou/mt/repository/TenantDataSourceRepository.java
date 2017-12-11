package com.anakiou.mt.repository;

import com.anakiou.mt.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantDataSourceRepository extends JpaRepository<Tenant, Long> {

    Tenant findByName(String name);
}
