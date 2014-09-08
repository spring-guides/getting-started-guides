
package hello;

import hello.Customer;
import hello.CustomerController;
import hello.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.nio.charset.Charset;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith (MockitoJUnitRunner.class)
public class CustomerControllerTests {

	private MockMvc mockMvc;
	@Mock
	private CustomerRepository customerRepoMock;

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
																		 MediaType.APPLICATION_JSON.getSubtype(),
																		 Charset.forName("utf8")
	);

	private static final int CUSTOMER_ID = 101;


	@Before
	public void setUp() {
		CustomerController customerController = new CustomerController(customerRepoMock);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void testInvalidPath() throws Exception {
		mockMvc.perform(get("/junk"))
				.andExpect(status().isNotFound());
		verifyZeroInteractions(customerRepoMock);

	}

	@Test
	public void testGetCustomer() throws Exception {
		Customer customer = createCustomer(CUSTOMER_ID, "James", "Kirk");
		when(customerRepoMock.getCustomer(CUSTOMER_ID)).thenReturn(customer);

		mockMvc.perform(get("/customers/customer/" + CUSTOMER_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(CUSTOMER_ID)))
				.andExpect(jsonPath("$.firstName", is("James")))
				.andExpect(jsonPath("$.lastName", is("Kirk")));

		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(customerRepoMock, times(1)).getCustomer(argumentCaptor.capture());
		assertTrue(CUSTOMER_ID == argumentCaptor.getValue());
		verifyNoMoreInteractions(customerRepoMock);


	}

	@Test
	public void testGetCustomerBadPathParameter() throws Exception {
		mockMvc.perform(get("/customers/customer/junk"))
				.andExpect(status().isInternalServerError());
		verifyZeroInteractions(customerRepoMock);
	}

	@Test
	public void testCreateCustomer() throws Exception {
		String firstName = "Kathryn";
		String lastName = "Janeway";

		when(customerRepoMock.createCustomer(any(Customer.class))).thenReturn(CUSTOMER_ID);
		when(customerRepoMock.getCustomer(CUSTOMER_ID)).thenReturn(createCustomer(CUSTOMER_ID,firstName, lastName));

		mockMvc.perform(post("/customers/customer/")
								.contentType(APPLICATION_JSON_UTF8)
								.content("{\"lastName\": \"Janeway\", \"firstName\":\"Kathryn\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(CUSTOMER_ID)))
				.andExpect(jsonPath("$.firstName", is(firstName)))
				.andExpect(jsonPath("$.lastName", is(lastName)));



		ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(customerRepoMock, times(1)).createCustomer(argumentCaptor.capture());
		assertEquals(firstName, argumentCaptor.getValue().getFirstName());
		assertEquals(lastName, argumentCaptor.getValue().getLastName());

		ArgumentCaptor<Integer> argumentCaptorGetCustomer = ArgumentCaptor.forClass(Integer.class);
		verify(customerRepoMock, times(1)).getCustomer(argumentCaptorGetCustomer.capture());
		assertTrue(CUSTOMER_ID == argumentCaptorGetCustomer.getValue());
		verifyNoMoreInteractions(customerRepoMock);

	}

	@Test
	public void testGetCustomers() throws Exception {
		mockMvc.perform(get("/customers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8));
		verify(customerRepoMock, times(1)).getCustomers();
	}


	@Test
	public void testRandomException() throws Exception {
		when(customerRepoMock.getCustomer(CUSTOMER_ID)).thenThrow(new RuntimeException("BOOM!"));
		mockMvc.perform(get("/customers/customer/" + CUSTOMER_ID))
				.andExpect(status().isInternalServerError());

	}


	private Customer createCustomer(int id, String firstName, String lastName) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		return customer;
	}

}
