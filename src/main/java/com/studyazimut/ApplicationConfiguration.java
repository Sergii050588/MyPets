package com.studyazimut;

import com.studyazimut.infastructure.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationConfiguration extends WebSecurityConfigurerAdapter {
  
  
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/pets/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/pets/**").hasRole("USER")
            .antMatchers(HttpMethod.PUT, "/pets/**").hasRole("USER")
            .antMatchers(HttpMethod.PATCH, "/pets/**").hasRole("USER")
            .antMatchers(HttpMethod.DELETE, "/pets/**").hasRole("USER")
            .and()
            .csrf().disable()
            .headers().frameOptions().sameOrigin();
  }
  
  @Bean
  public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
    return new SecurityEvaluationContextExtension();
  }
  
}