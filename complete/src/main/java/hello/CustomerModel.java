package hello;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class CustomerModel extends LoadableDetachableModel<Customer> {

	@SpringBean
	private CustomerRepository repo;

	private long id;

	public CustomerModel(Customer customer) {
		super(customer);
		this.id = customer.getId();
		Injector.get().inject(this);
	}

	// tag::load[]
	@Override
	protected Customer load() {
		return repo.findOne(id);
	}
	// end::load[]

}
