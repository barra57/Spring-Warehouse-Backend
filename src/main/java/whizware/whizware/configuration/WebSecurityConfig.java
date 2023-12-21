package whizware.whizware.configuration;

import whizware.whizware.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import whizware.whizware.filter.JwtAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/swagger", "/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("/user/**").hasAnyAuthority(Role.ADMIN.getName())
                                .requestMatchers("/warehouse/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName())
                                .requestMatchers("/store/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName(), Role.STORE.getName())
                                .requestMatchers("/goods/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName())
                                .requestMatchers("/location/**").hasAnyAuthority(Role.ADMIN.getName())
                                .requestMatchers("/stock/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName())
                                .requestMatchers("/receipt/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName())
                                .requestMatchers("/delivery/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName(), Role.STORE.getName())
                                .requestMatchers("/transfer/**").hasAnyAuthority(Role.ADMIN.getName(), Role.WAREHOUSE.getName())
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

}
