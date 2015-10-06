package hello;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.ThrottlingSettings;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.event.annotation.OnEvent;
import org.wicketstuff.minis.behavior.VisibleModelBehavior;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.table.TableBehavior;
import event.CancelEvent;
import event.DeleteEvent;
import event.EditEvent;
import event.SaveEvent;
import util.ButtonColumn;
import util.EventAjaxButton;
import util.EventAjaxLink;
import util.SupplierModel;

public class ManageCustomersPage extends BasePage {

    private static final int PAGE_SIZE = 5;

    @SpringBean
    private CustomerRepository repo;

    private IModel<Customer> newCustomerModel = Model.of();

    private Component table;
    private Component editPanel;

    public ManageCustomersPage() {
        super();

        IModel<String> searchModel = Model.of();

        /*
         * Search form.
         */
        // tag::search-form[]
        Form<?> searchForm = new BootstrapForm<>("searchForm", searchModel)
                .type(FormType.Inline);
        queue(searchForm);
        // end::search-form[]

        /*
         * List of customers.
         */
        // tag::table[]
        table = new AjaxFallbackDefaultDataTable<>("table",
                    getColumns(),
                    new CustomerDataProvider(searchModel,
                            PAGE_SIZE,
                            CustomerSort.ID,
                            SortOrder.ASCENDING),
                    PAGE_SIZE)
                .add(new TableBehavior().bordered().condensed().striped())
                .setOutputMarkupId(true);
        queue(table);
        // end::table[]

        /*
         * Last name search field.
         */
        // tag::search-form-components[]
        queue(new TextField<>("lastNameSearch", searchModel)
                .add(new AjaxFormComponentUpdatingBehavior("keyup") {

                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        target.add(table);
                    }

                    @Override
                    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                        super.updateAjaxAttributes(attributes);
                        attributes.setThrottlingSettings(new ThrottlingSettings(null, Duration.milliseconds(100), true));
                    }

                })
            .add(new InputBehavior())
            .add(new AttributeAppender("placeholder", new ResourceModel("placeholder.last.name.filter"))));

        /*
         * New customer link.
         */
        queue(new EventAjaxLink<>("newCustomerLink", Type.Default,
                target -> new EditEvent<>(target, new Customer()))
            .setIconType(GlyphIconType.plus)
            .setLabel(new ResourceModel("label.new.customer")));
        // end::search-form-components[]

        /*
         * Edit customer panel.
         */
        // tag::customer-edit-panel[]
        editPanel = new CustomerEditPanel("newCustomerPanel", newCustomerModel)
                .setOutputMarkupPlaceholderTag(true)
                .add(new VisibleModelBehavior(new SupplierModel<>(() -> newCustomerModel.getObject() != null)));
        queue(editPanel);
        // end::customer-edit-panel[]
    }

    // tag::event-handlers[]
    @OnEvent(types = Customer.class)
    public void handleSaveEvent(SaveEvent<Customer> event) {
        repo.save(event.getPayload());
        newCustomerModel.setObject(null);
        event.getTarget().add(table, editPanel);
    }

    @OnEvent
    public void handleCancelEvent(CancelEvent event) {
        newCustomerModel.setObject(null);
        event.getTarget().add(editPanel);
    }

    @OnEvent(types = Customer.class)
    public void handleDeleteEvent(DeleteEvent<Customer> event) {
        repo.delete(event.getPayload());
        event.getTarget().add(table);
    }

    @OnEvent(types = Customer.class)
    public void handleEditEvent(EditEvent<Customer> event) {
        newCustomerModel.setObject(event.getPayload());
        editPanel.modelChanged();
        event.getTarget().add(editPanel);
    }
    // end::event-handlers[]

    // tag::table-columns[]
    private List<IColumn<Customer, CustomerSort>> getColumns() {
        return Arrays.asList(
                new PropertyColumn<>(
                        new ResourceModel("label.id"),
                        CustomerSort.ID,
                        "id"),
                new PropertyColumn<>(
                        new ResourceModel("label.first.name"),
                        CustomerSort.FIRST_NAME,
                        "firstName"),
                new PropertyColumn<>(
                        new ResourceModel("label.last.name"),
                        CustomerSort.LAST_NAME,
                        "lastName"),
                new ButtonColumn<>(
                        new ResourceModel("label.actions"),
                        bid -> Arrays.asList(
                                new EventAjaxButton(bid, Type.Default, (target, payload) ->
                                        new EditEvent<>(target, payload))
                                    .setIconType(GlyphIconType.edit),
                                new EventAjaxButton(bid, Type.Default, (target, payload) ->
                                        new DeleteEvent<>(target, payload))
                                    .setIconType(GlyphIconType.trash))));
    }
    // end::table-columns[]

}
