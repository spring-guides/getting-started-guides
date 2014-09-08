package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping ( "customers" )
public class CustomerController {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@RequestMapping ( value = "customer/{id}", method = RequestMethod.GET )
	public Customer getCustomer(
			@PathVariable ( "id" )
			int id) {
		return customerRepository.getCustomer(id);
	}

	@RequestMapping ( value = "customer", method = RequestMethod.POST )
	@ResponseStatus ( HttpStatus.CREATED )
	public Customer createCustomer(
			@RequestBody
			@Valid
			Customer customer) {
		int newCustomerId = customerRepository.createCustomer(customer);
		return customerRepository.getCustomer(newCustomerId);
	}


	@RequestMapping ( method = RequestMethod.GET )
	public List<Customer> getCustomers() {
		return customerRepository.getCustomers();
	}


	@ExceptionHandler
	@ResponseStatus ( value = HttpStatus.NOT_FOUND)
	public void handleCustomerNotFoundException(EmptyResultDataAccessException ex) {
		ex.printStackTrace(System.err);
	}

	@ExceptionHandler
	@ResponseStatus ( value = HttpStatus.BAD_REQUEST )
	public void handleBadArgumentException(MethodArgumentNotValidException ex) {
		ex.printStackTrace(System.err);
	}

	@ExceptionHandler
	@ResponseStatus ( value = HttpStatus.CONFLICT )
	public void handleDuplicateCustomer(DuplicateKeyException ex) {
		ex.printStackTrace(System.err);
	}

	@ExceptionHandler
	@ResponseStatus ( value = HttpStatus.INTERNAL_SERVER_ERROR )
	public void handleException(Exception ex) {
		ex.printStackTrace(System.err);
	}


}
