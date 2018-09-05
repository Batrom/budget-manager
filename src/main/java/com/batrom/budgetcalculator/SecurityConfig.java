package com.batrom.budgetcalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.httpBasic().and().authorizeRequests()
            .antMatchers("/js/**", "/css/**", "/bootstrap/**", "/view/**").permitAll()
            .antMatchers("/", "/user", "index.html").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().csrfTokenRepository(csrfTokenRepository())
            .and()
            .logout()
            .and()
            .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    @Autowired
    public void configAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("select u.login, r.name from users u " +
                    "inner join user_role ur on ur.user_id = u.id " +
                    "inner join role r on r.id = ur.role_id " +
                    "where u.login = ?")
            .usersByUsernameQuery("select u.login, u.password, u.active from users u where u.login = ?");
//            .passwordEncoder(new BCryptPasswordEncoder());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
