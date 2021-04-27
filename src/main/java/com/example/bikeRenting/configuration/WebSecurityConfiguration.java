package com.example.bikeRenting.configuration;

import com.example.bikeRenting.service.user.AuthenticationService;
import com.example.bikeRenting.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final Boolean swaggerEnabled;
    private final UserService userService;

    @Autowired
    public WebSecurityConfiguration(AuthenticationService authenticationService,
                                    @Value("#{new Boolean('${swagger.enabled}')}") Boolean swaggerEnabled,
                                    UserService userService) {
        this.authenticationService = authenticationService;
        this.swaggerEnabled = swaggerEnabled;
        this.userService = userService;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(super.authenticationManager(), authenticationService, userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register", "/login", "/mock/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/*.{js,css,html}", "/assets/**", "favicon.ico");

        // Disable security on Swagger endpoints (if it's enabled)
        if (Boolean.TRUE.equals(swaggerEnabled)) {
            web.ignoring()
                    .antMatchers("/v2/api-docs")
                    .antMatchers("/swagger-ui.html")
                    .antMatchers("/swagger-resources/**")
                    .antMatchers("/configuration/ui")
                    .antMatchers("/api/renting/swagger/docs")
                    .antMatchers("/webjars/**");
        }
    }
}
