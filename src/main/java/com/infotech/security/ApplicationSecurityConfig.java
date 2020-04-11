package com.infotech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.infotech.security.ApplicationUserPermission.COURSE_WRITE;
import static com.infotech.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/user").hasRole(ADMIN.name())
                    /*.antMatchers(HttpMethod.POST, "/emp/**").hasAuthority(COURSE_WRITE.getPermission())
                    .antMatchers(HttpMethod.PUT, "/emp/**").hasAuthority(COURSE_WRITE.getPermission())
                    .antMatchers(HttpMethod.DELETE, "/emp/**").hasAuthority(COURSE_WRITE.getPermission())
                    .antMatchers(HttpMethod.GET, "/emp/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())*/
                    .antMatchers("/*css", "/*js", "/index*").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails alex = User.builder().username("alex")
                .password(passwordEncoder.encode("1234"))
                /*.roles(ADMIN.name())*/
                .authorities(ADMIN.grantedAuthorities())
                .build();

        UserDetails anto = User.builder().username("anto")
                .password(passwordEncoder.encode("1234"))
                /*.roles(STUDENT.name())*/
                .authorities(STUDENT.grantedAuthorities())
                .build();

        UserDetails tony = User.builder().username("tony")
                .password(passwordEncoder.encode("1234"))
                /*.roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE*/
                .authorities(ADMINTRAINEE.grantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                alex,
                anto,
                tony
        );
    }
}
