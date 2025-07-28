package com.minigod.auth.exception;

import com.minigod.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/14 12:04
 * @description：
 */
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

	private static final String USER_DOES_NOT_EXIST = "UserDetailsService returned null, which is an interface contract violation";

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	@Override
	public ResponseEntity translate(Exception e) throws Exception {
		Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
		Exception ase = (OAuth2Exception) this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
		//异常链中有OAuth2Exception异常
		if (ase != null) {
			return this.handleOAuth2Exception((OAuth2Exception) ase);
		}
		//身份验证相关异常
		ase = (AuthenticationException) this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
		if (ase != null) {
			return this.handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
		}
		//异常链中包含拒绝访问异常
		ase = (AccessDeniedException) this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
		if (ase instanceof AccessDeniedException) {
			return this.handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
		}
		//异常链中包含Http方法请求异常
		ase = (HttpRequestMethodNotSupportedException) this.throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
		if (ase instanceof HttpRequestMethodNotSupportedException) {
			return this.handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
		}
		ase = (PasswordMissingException) this.throwableAnalyzer.getFirstThrowableOfType(PasswordMissingException.class, causeChain);
		if (ase instanceof PasswordMissingException) {
			return  handlerPasswordException(I18nUtil.getMessage(CommonConstant.PLEASE_SET_THE_LOGIN_PASSWORD));
		}
		ase = (PasswordErrorException) this.throwableAnalyzer.getFirstThrowableOfType(PasswordErrorException.class, causeChain);
		if (ase instanceof PasswordErrorException) {
			return  handlerPasswordException(I18nUtil.getMessage(CommonConstant.ACCOUNT_OR_PASSWORD_ERROR));
		}
		ase = (PasswordErrorThanMaxException) this.throwableAnalyzer.getFirstThrowableOfType(PasswordErrorThanMaxException.class, causeChain);
		if (ase instanceof PasswordErrorThanMaxException) {
			return  handlerPasswordException(ase.getMessage());
		}
		ase = (PasswordErrorThanMaxException) this.throwableAnalyzer.getFirstThrowableOfType(PasswordErrorThanMaxException.class, causeChain);
		if (ase instanceof PasswordErrorThanMaxException) {
			return  handlerPasswordException(ase.getMessage());
		}
		return this.handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
	}


	private ResponseEntity<RuntimeException> handlerPasswordException(String message){
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache());
		headers.setPragma(CacheControl.noCache().getHeaderValue());
		ResponseEntity<RuntimeException> response = new ResponseEntity(R.fail(message), headers, HttpStatus.OK);
		return response;
	}

	private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
		String message = e.getMessage();
		if (USER_DOES_NOT_EXIST.equals(e.getMessage())){
			message = "用户不存在";
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache());
		headers.setPragma(CacheControl.noCache().getHeaderValue());
		ResponseEntity<OAuth2Exception> response = new ResponseEntity(R.fail(message), headers, HttpStatus.OK);
		return response;
	}

	private static class MethodNotAllowed extends OAuth2Exception {
		public MethodNotAllowed(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "method_not_allowed";
		}

		@Override
		public int getHttpErrorCode() {
			return 405;
		}
	}

	private static class UnauthorizedException extends OAuth2Exception {
		public UnauthorizedException(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "unauthorized";
		}

		@Override
		public int getHttpErrorCode() {
			return 401;
		}
	}

	private static class ServerErrorException extends OAuth2Exception {
		public ServerErrorException(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "server_error";
		}

		@Override
		public int getHttpErrorCode() {
			return 500;
		}
	}

	private static class ForbiddenException extends OAuth2Exception {
		public ForbiddenException(String msg, Throwable t) {
			super(msg, t);
		}

		@Override
		public String getOAuth2ErrorCode() {
			return "access_denied";
		}

		@Override
		public int getHttpErrorCode() {
			return 403;
		}
	}
}
