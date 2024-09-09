package org.example.resourcesproject.config;

import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.persist.Adapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CasbinConfig {

    @Bean
    public Enforcer enforcer() {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/resources";
        String username = "postgres";
        String password = "admin";

        Adapter adapter;
        try {
            adapter = new JDBCAdapter(driver, url, username, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JDBC adapter for Casbin", e);
        }

        return new Enforcer("src/main/resources/casbin/model.conf", adapter);
    }


}


