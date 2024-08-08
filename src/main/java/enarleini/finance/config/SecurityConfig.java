// File: src/main/java/enarleini/finance/config/SecurityConfig.java
package enarleini.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/expenses/delete/**").permitAll()
                        .requestMatchers("/api/incomes/delete/**").permitAll()
                        .requestMatchers("/api/expenses/add").permitAll()
                        .requestMatchers("/api/incomes/add").permitAll()
                        .requestMatchers("/api/expenses/update/**").permitAll()
                        .requestMatchers("/api/incomes/update/**").permitAll()
                        .requestMatchers("/api/expenses/findall").permitAll()
                        .requestMatchers("/api/incomes/findall").permitAll()
                        .requestMatchers("/api/expenses/sum").permitAll()
                        .requestMatchers("/api").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/expenses/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/clients/**").permitAll()
                        .requestMatchers("/api/expenses/**").permitAll()
                        .requestMatchers("/api/incomes/**").permitAll()
                        .requestMatchers("api/clients/expenses/**").permitAll()
                        .requestMatchers("api/clients/incomes/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(List.of("x-auth-token"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}