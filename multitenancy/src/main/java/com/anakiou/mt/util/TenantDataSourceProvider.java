package com.anakiou.mt.util;

import com.anakiou.mt.domain.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类负责根据租户ID来提供对应的数据源
 *
 * @author lanyuanxiaoyao
 * @version 1.0
 */
@Component
public class TenantDataSourceProvider {

    // 使用一个map来存储我们租户和对应的数据源，租户和数据源的信息就是从我们的tenant_info表中读出来
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    @Autowired
    private DataSource dataSource;

    // 根据传进来的tenantId决定返回的数据源
    public DataSource getTenantDataSource(String tenantId) {
        if (dataSourceMap.containsKey(tenantId)) {
            System.out.println("GetDataSource:" + tenantId);
            return dataSourceMap.get(tenantId);
        } else {
            System.out.println("GetDataSource:" + "Default");
            return dataSource;
        }
    }

    // 初始化的时候用于添加数据源的方法
    public void addDataSource(Tenant tenant) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
                .url(tenant.getUrl())
                .username(tenant.getUsername())
                .password(tenant.getPassword());
        DataSource dataSource = dataSourceBuilder.build();
        dataSourceMap.put(tenant.getName(), dataSource);
    }

}
