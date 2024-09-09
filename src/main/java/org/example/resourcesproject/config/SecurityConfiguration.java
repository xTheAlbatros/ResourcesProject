package org.example.resourcesproject.config;

import org.casbin.casdoor.service.CasdoorAuthService;
import org.casbin.jcasbin.main.Enforcer;
import org.example.resourcesproject.casbin.CasbinFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Enforcer enforcer;
    private final CasdoorAuthService casdoorAuthService;

    public SecurityConfiguration(Enforcer enforcer, CasdoorAuthService casdoorAuthService) {
        this.enforcer = enforcer;
        this.casdoorAuthService = casdoorAuthService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CasbinFilter(enforcer, casdoorAuthService), BasicAuthenticationFilter.class)
                .csrf().disable()
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                            corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
                            corsConfig.setAllowCredentials(true);
                            return corsConfig;
                        })
                );
        return http.build();
    }
}
