package com.studyazimut.infastructure;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
  
  private SecurityUtils() {}
  
  public static void run(String login, String password, String[] roles, Runnable runnable) {
    try {
      SecurityContextHolder.getContext().setAuthentication(auth(login, password, roles));
      runnable.run();
    } finally {
      SecurityContextHolder.clearContext();
    }
  }
  
  private static Authentication auth(String login, String password, String[] roles) {
    return new UsernamePasswordAuthenticationToken(login, password, AuthorityUtils.createAuthorityList(roles));
  }
  
}