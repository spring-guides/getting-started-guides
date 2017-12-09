package hello;

import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractRestClientApplicationTest {

	@Rule
	public StubRunnerRule stubRunnerRule = new StubRunnerRule()
		.downloadStub("com.example", "contract-rest-service", "0.0.1-SNAPSHOT", "stubs")
		.withPort(8100)
		.workOffline(true);
	
	@Test
	public void get_weather_from_service_contract() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Person> personResponseEntity = restTemplate.getForEntity("http://localhost:8100/person/1", Person.class);
		
		BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
		
		BDDAssertions.then(personResponseEntity.getBody().getId()).isEqualTo(1l);
		BDDAssertions.then(personResponseEntity.getBody().getName()).isEqualTo("foo");
		BDDAssertions.then(personResponseEntity.getBody().getSurname()).isEqualTo("bee");
		
	}
}
