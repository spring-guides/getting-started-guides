package hello;

import hello.Customer;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;


public class CustomerTests {

	@Test
	public void testNormalization() {
		Customer customer = new Customer();
		assertNull(customer.normalizeString(null));
		assertEquals("", customer.normalizeString(""));
		assertEquals("Philip", customer.normalizeString(" pHiLip "));
		assertEquals("Mary", customer.normalizeString(" MARY "));
	}
}
