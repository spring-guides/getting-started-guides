package hello.security;

import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

public class ApiRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

	public ApiRequestHeaderAuthenticationFilter() {
		super.setPrincipalRequestHeader("X-Auth-Token");
		super.setExceptionIfHeaderMissing(false);
	}
}
