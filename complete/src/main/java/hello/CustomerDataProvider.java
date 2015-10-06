package hello;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * {@link SortableDataProvider} for {@link Customer}s.
 */
public class CustomerDataProvider extends SortableDataProvider<Customer, CustomerSort> {

    @SpringBean
    private CustomerRepository repo;

    private IModel<String> lastNameFilter;

    private int pageSize;

    public CustomerDataProvider(IModel<String> lastNameFilter, int pageSize, CustomerSort sort, SortOrder order) {
        this.lastNameFilter = lastNameFilter;
        this.pageSize = pageSize;
        setSort(sort, order);
        Injector.get().inject(this);
    }

    @Override
    // tag::iterator[]
    public Iterator<? extends Customer> iterator(long first, long count) {
        Pageable pageable = new PageRequest((int) (first / pageSize),
                pageSize,
                getSort().isAscending() ? Direction.ASC : Direction.DESC,
                getSort().getProperty().getProperties());
        Page<Customer> list = lastNameFilter.getObject() == null ?
                repo.findAll(pageable) :
                repo.findByLastNameStartsWithIgnoreCase(lastNameFilter.getObject(), pageable);
        return list.iterator();
    }
    // end::iterator[]

    // tag::count[]
    @Override
    public long size() {
        return lastNameFilter.getObject() == null ?
                repo.count() :
                repo.countByLastNameStartsWithIgnoreCase(lastNameFilter.getObject());
    }
    // end::count[]

    // tag::model[]
    @Override
    public IModel<Customer> model(Customer customer) {
        return new CustomerModel(customer);
    }
    // end::model[]

}