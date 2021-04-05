package com.example.bikeRenting.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
    private final SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;
    private final String secret;
    private final Boolean swaggerEnabled;

    @Autowired
    public WebSecurityConfiguration(@Qualifier("userMainService") UserDetailsService userDetailsService,
                                    SimpleUrlAuthenticationFailureHandler authenticationFailureHandler,
                                    SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler,
                                    @Value("${jwt.secret}") String secret,
                                    @Value("#{new Boolean('${swagger.enabled}')}") Boolean swaggerEnabled) {
        this.userDetailsService = userDetailsService;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.secret = secret;
        this.swaggerEnabled = swaggerEnabled;
    }

    @Bean
    public JsonObjectAuthenticationFilter jsonObjectAuthenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler); // 1
        filter.setAuthenticationFailureHandler(authenticationFailureHandler); // 2
        filter.setAuthenticationManager(super.authenticationManager()); // 3
        return filter;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        var filter = new JWTAuthenticationFilter(super.authenticationManager(), userDetailsService, secret);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/register", "/mock/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jsonObjectAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}