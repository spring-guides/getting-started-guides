package hello;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class RestApiController {

	@RequestMapping(value="/unsecured", method=RequestMethod.GET)
	public String sayHelloUnsecured(@RequestParam(value="name", required=false) String name) {
		return "Hello " + (name == null ? "world" : name) + " from UNSECURED REST service";
	}
	
	@RequestMapping(value="/secured", method=RequestMethod.GET)
	public String sayHelloSecured() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return "Hello [" + user.getUsername() + "] from SECURED REST service";
	}
	
}
