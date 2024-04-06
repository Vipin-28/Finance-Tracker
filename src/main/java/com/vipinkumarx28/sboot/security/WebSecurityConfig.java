package com.vipinkumarx28.sboot.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;



@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig{
    private static final String[] WHITE_LIST_URLS = {
            "/register",
            "/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, WHITE_LIST_URLS).anonymous()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable);

//        http.securityMatcher("/api/**").authorizeHttpRequests(authz ->
//                        authz.requestMatchers(HttpMethod.POST, "/register").anonymous()
//                                .requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
//                                .requestMatchers("/**").authenticated())
//                .httpBasic(hbc -> hbc.authenticationEntryPoint(authenticationEntryPoint))
//                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
