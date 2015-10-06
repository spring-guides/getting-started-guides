package util;

import org.apache.wicket.model.LoadableDetachableModel;

public class SupplierModel<T> extends LoadableDetachableModel<T> {

    /*
     * Supplier that supplies the value of the model.
     */
    private SerializableSupplier<T> supplier;

    /**
     * Constructor.
     * @param supplier that supplies the value of the model
     */
    public SupplierModel(SerializableSupplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected T load() {
        return supplier.get();
    }

}
