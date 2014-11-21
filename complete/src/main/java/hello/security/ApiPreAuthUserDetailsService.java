package hello.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class ApiPreAuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Autowired
	ApiPreAuthenticatedTokenCacheService apiPreAuthenticatedTokenCacheService;
	
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String xAuthToken = (String) token.getPrincipal();
		User user = apiPreAuthenticatedTokenCacheService.getFromCache(xAuthToken);

		if (user == null)
			throw new UsernameNotFoundException("Pre authenticated token not found : " + xAuthToken);

		return user;
	}

}
