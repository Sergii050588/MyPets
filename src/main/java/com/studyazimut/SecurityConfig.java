package com.studyazimut;

import com.studyazimut.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
    auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(getPasswordEncoder());
  }
  
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.csrf().disable();
    http
            .authorizeRequests()
            .antMatchers("/css", "/js", "/api/user").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
  }
  
  private PasswordEncoder getPasswordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence charSequence) {
        return charSequence.toString();
      }
      
      @Override
      public boolean matches(CharSequence charSequence, String s) {
        return true;
      }
    };
  }
}
