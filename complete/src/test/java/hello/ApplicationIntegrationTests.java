package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = Application.class)
@WebAppConfiguration
@IntegrationTest ("server.port:0")
public class ApplicationIntegrationTests {

	@Value ("${local.server.port}")
	private int port;

	@Test
	public void testBadPath() throws Exception {
		ResponseEntity<String> response = new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/junk", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void testGetCustomer() throws Exception {
		Customer customer = new TestRestTemplate().getForObject("http://localhost:" + this.port + "/customers/customer/1", Customer.class);
		assertNotNull(customer);
		assertEquals("Fred", customer.getFirstName());
		assertEquals("Flintstone", customer.getLastName());

	}

	@Test
	public void testCustomerDoesNotExist() throws Exception {
		ResponseEntity<String> response = new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/customers/customer/40001", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testListCustomers() throws Exception {
		ResponseEntity<Customer[]> results = new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/customers", Customer[].class);
		assertNotNull(results);
		assertEquals(5, results.getBody().length);
	}


	@Test
	public void testCreateCustomer() throws Exception {
		RestTemplate restTemplate = new TestRestTemplate();

		Customer customer = new Customer();
		customer.setFirstName("BARNEY");
		customer.setLastName("RUBBLE");

		assertNull(customer.getId());
		Customer result = restTemplate.postForObject("http://localhost:" + this.port + "/customers/customer", customer, Customer.class);
		assertNotNull(result.getId());
		assertTrue(result.getId() > 0);
		assertEquals("Barney", result.getFirstName());
		assertEquals("Rubble", result.getLastName());


		ResponseEntity<Customer[]> results = restTemplate.getForEntity("http://localhost:" + this.port + "/customers", Customer[].class);
		assertNotNull(results);
		assertEquals(6, results.getBody().length);


	}

	@Test
	public void testCreateInvalidCustomer() throws Exception {
		RestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + this.port + "/customers/customer", new Customer(), String.class);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

	}


	@Test
	public void testDuplicateCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setFirstName("Lisa");
		customer.setLastName("Simpson");
		RestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + this.port + "/customers/customer", customer, String.class);
		assertEquals(HttpStatus.CONFLICT, result.getStatusCode());

	}


}
