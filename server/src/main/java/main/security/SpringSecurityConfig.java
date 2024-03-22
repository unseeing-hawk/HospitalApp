package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/wards/all").permitAll()
                .antMatchers(HttpMethod.GET, "/wards/ward/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/wards/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/diagnosis/all").permitAll()
                .antMatchers(HttpMethod.GET, "/diagnosis/diagnose/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/diagnosis/ward/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/diagnosis/{name}/people").permitAll()
                .antMatchers(HttpMethod.GET, "/people/all").permitAll()
                .antMatchers(HttpMethod.GET, "/people/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/people/{wardName}/{diagnoseName}").permitAll()
                .antMatchers(HttpMethod.GET, "/people/fullName").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
