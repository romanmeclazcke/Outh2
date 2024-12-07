package org.example.oauth2.security;

import org.example.oauth2.Authentication.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configurable
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler; //created a custo

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(request -> {
                    // rutas públicas y protegidas
                    request.requestMatchers(HttpMethod.GET, "/", "/hello").permitAll();
                    request.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults()) // Autenticación con formulario (opcional)
                .oauth2Login(oauth2 ->
                        oauth2.successHandler(customAuthenticationSuccessHandler) // Redirección después de autenticación OAuth2 al manejador de exito
                )
                .build();
    }
}
