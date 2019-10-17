package org.zuoyu.security.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 解决登录失败的返回.
 *
 * @author zuoyu
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    if (exception.fillInStackTrace().getClass() == LockedException.class) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的账户已被锁定");
      return;
    }
    if (exception.fillInStackTrace().getClass() == DisabledException.class) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的账户已被禁用");
      return;
    }
    if (exception.fillInStackTrace().getClass() == AccountExpiredException.class) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的账户已过期");
      return;
    }
    if (exception.fillInStackTrace().getClass() == CredentialsExpiredException.class) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的凭证已过期");
      return;
    }
    if (exception.fillInStackTrace().getClass() == InternalAuthenticationServiceException.class) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "登录失败，密码或帐号错误");
      return;
    }
    if (exception.fillInStackTrace().getClass() == BadCredentialsException.class) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "登录失败，密码或帐号错误");
      return;
    }
    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "登录功能异常");
  }
}
