package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ContractRestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractRestClientApplication.class, args);
    }
}

@RestController
class MessageRestController {

	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
    @RequestMapping("/message/{personId}")
    String getMessage(@PathVariable("personId") Long personId) {
    	
    	Person person = restTemplateBuilder.build().getForObject("http://localhost:8000/person/{personId}", Person.class, personId);
        return "Hello " + person.getName();
    }
    
}