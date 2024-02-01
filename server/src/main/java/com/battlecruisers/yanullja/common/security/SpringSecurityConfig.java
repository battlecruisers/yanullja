package com.battlecruisers.yanullja.common.security;

import com.battlecruisers.yanullja.auth.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
        throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowedOrigins(
            java.util.List.of("http://localhost:5173", "https://yanullja.com"));
        config.setAllowedHeaders(java.util.List.of("*"));
        config.setAllowedMethods(
            java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http, AuthenticationManager authenticationManager)
        throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
            .formLogin(FormLoginConfigurer::disable)
            .csrf(CsrfConfigurer::disable);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(
            (auth) ->
                auth.requestMatchers("/users")
                    .authenticated()
                    .requestMatchers("/payment/instant")
                    .authenticated()
                    .requestMatchers("/payment/purchase")
                    .authenticated()
                    .requestMatchers(HttpMethod.GET, "/payment")
                    .authenticated()
                    .requestMatchers("/carts")
                    .authenticated()
                    .anyRequest()
                    .permitAll());

        // log in
        var customAuthenticationFilter = new CustomAuthenticationFilter(
            authenticationManager);
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        http.addFilter(customAuthenticationFilter);

        // log out
        http.logout(
            logout ->
                logout.logoutUrl("/auth/logout")
                    .logoutSuccessHandler(
                        new HttpStatusReturningLogoutSuccessHandler(
                            HttpStatus.OK))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID"));

        http.sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
