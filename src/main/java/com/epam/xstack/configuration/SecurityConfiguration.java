package com.epam.xstack.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.epam.xstack.models.enums.Permission.*;
import static com.epam.xstack.models.enums.Role.TRAINEE;
import static com.epam.xstack.models.enums.Role.TRAINER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/actuator/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/**").hasAnyRole(TRAINEE.name(), TRAINER.name())

                                .requestMatchers(GET, "/api/v1/trainers/{id}").hasAuthority(TRAINER_READ.name())
                                .requestMatchers(GET, "/api/v1/trainers/select/{id}").hasAuthority(TRAINER_READ.name())
                                .requestMatchers(POST, "/api/v1/trainers/**").hasAuthority(TRAINER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/trainers/update/{id}").hasAuthority(TRAINER_UPDATE.name())
                                .requestMatchers(PUT, "/api/v1/trainers/{id}").hasAuthority(TRAINER_UPDATE.name())

                                .requestMatchers(POST, "/api/v1/trainings/save").hasAuthority(TRAINER_CREATE.name())
                                .requestMatchers(POST, "/api/v1/training_types/save").hasAuthority(TRAINER_CREATE.name())
                                .requestMatchers(GET, "/api/v1/training_types/all").hasAuthority(TRAINER_READ.name())


                                .requestMatchers(GET, "/api/v1/trainees/{id}").hasAuthority(TRAINEE_READ.name())
                                .requestMatchers(GET, "/api/v1/trainees/select/{id}").hasAuthority(TRAINEE_READ.name())
                                .requestMatchers(GET, "/api/v1/trainees/active-not-assigned/{id}").hasAuthority(TRAINEE_READ.name())
                                .requestMatchers(POST, "/api/v1/trainees/**").hasAuthority(TRAINEE_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/trainees/update/{id}").hasAuthority(TRAINEE_UPDATE.name())
                                .requestMatchers(PUT, "/api/v1/trainees/update-list/{id}").hasAuthority(TRAINEE_UPDATE.name())
                                .requestMatchers(PUT, "/api/v1/trainees/{id}").hasAuthority(TRAINEE_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/trainees/delete/{id}").hasAuthority(TRAINEE_DELETE.name())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}
