package hello.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebMvcSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ApiPreAuthUserDetailsService preAuthUserDetailsService;
	
	@Autowired
	ApiAuthenticationSuccessHandler apiAuthenticationSuccessHandler;
	
	@Autowired
	ApiAuthenticationFailureHandler apiAuthenticationFailureHandler;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/api/unsecured").permitAll()
                .antMatchers(HttpMethod.POST, "/authenticate").anonymous()
                .antMatchers("/api/**").hasRole("USER")
            .and()
            .addFilterAfter(apiAuthFilter(), ApiRequestHeaderAuthenticationFilter.class)
            .addFilter(preAuthFilter());
    }

    @Bean
    public Filter apiAuthFilter() throws Exception {
		RequestMatcher antReqMatch = new AntPathRequestMatcher("/authenticate");
		
		List<RequestMatcher> reqMatches = new ArrayList<RequestMatcher>();
		reqMatches.add(antReqMatch);
		RequestMatcher reqMatch = new AndRequestMatcher(reqMatches);

		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
		filter.setPostOnly(false);
		filter.setUsernameParameter("username");
		filter.setPasswordParameter("password");
		filter.setRequiresAuthenticationRequestMatcher(reqMatch);
		filter.setAuthenticationSuccessHandler(apiAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(apiAuthenticationFailureHandler);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
    	
    }

	@Bean
	public Filter preAuthFilter() {
		ApiRequestHeaderAuthenticationFilter filter = new ApiRequestHeaderAuthenticationFilter();
		filter.setAuthenticationManager(preAuthAuthenticationManager());
		return filter;
	}
	
	@Bean
	protected AuthenticationManager preAuthAuthenticationManager() {

		PreAuthenticatedAuthenticationProvider preAuthProvider= new PreAuthenticatedAuthenticationProvider();
		preAuthProvider.setPreAuthenticatedUserDetailsService(preAuthUserDetailsService);
		
		List<AuthenticationProvider> providers = new  ArrayList<AuthenticationProvider>();
		providers.add(preAuthProvider);
		
		ProviderManager authMan = new ProviderManager(providers);
		return authMan;
	}
    
    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .inMemoryAuthentication()
                .withUser("test").password("1234").roles("USER")
                .and()
                .withUser("foo").password("bee").roles("USER");
        }

    }

}
