package com.minigod.auth.converter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/24 17:26
 * @description：
 */
public class JwtAuthenticationConverter extends DefaultUserAuthenticationConverter {
	private Collection<? extends GrantedAuthority> defaultAuthorities;

	public void setDefaultAuthorities(String[] defaultAuthorities) {
		this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
	}

	/**
	 * 设置存入认证信息中的Map
	 *
	 * @param authentication
	 * @return
	 */
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put(USERNAME, authentication.getName());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}

	/**
	 * 选择存入认证信息中的数据
	 *
	 * @param map
	 * @return
	 */
	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		Authentication authentication = null;
		if (map.containsKey(USERNAME)) {
			// 将用户对象作为用户信息。
			Object principal = map;
			Collection<? extends GrantedAuthority> authorities = this.getAuthorities(map);
			authentication = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
		}
		return authentication;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		if (!map.containsKey(AUTHORITIES)) {
			return this.defaultAuthorities;
		} else {
			Object authorities = map.get(AUTHORITIES);
			if (authorities instanceof String) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList((String)authorities);
			} else if (authorities instanceof Collection) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection)authorities));
			} else {
				throw new IllegalArgumentException("Authorities must be either a String or a Collection");
			}
		}
	}

}
