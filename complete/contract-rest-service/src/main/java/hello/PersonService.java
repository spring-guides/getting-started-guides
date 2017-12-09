package hello;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

	private Map<Long, Person> personMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		personMap.put(1l, new Person(1l, "Richard", "Gere"));
		personMap.put(2l, new Person(2l, "Emma", "Choplin"));
		personMap.put(3l, new Person(3l, "Anna", "Carolina"));
	}
	
	public Person findPersonById(Long id) {
		return personMap.get(id);
	}
	
}
