package hello;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.event.annotation.OnEvent;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import event.CancelEvent;
import event.SaveEvent;
import event.ValidationErrorEvent;
import util.EventAjaxButton;

/**
 * Panel for editing/creating customer details.
 */
public class CustomerEditPanel extends Panel {

    private Form<?> form;

    public CustomerEditPanel(String id, IModel<Customer> model) {
        super(id, model);

        // tag::form[]
        /*
         * Form.
         */
        form = new BootstrapForm<>("form", model);
        queue(form);

        /*
         * First name field.
         */
        queue(new FormGroup("firstNameGroup"));
        queue(new TextField<>("firstNameField",
                new PropertyModel<>(model, "firstName"))
                .setLabel(new ResourceModel("label.first.name"))
                .setRequired(true)
                .add(new InputBehavior()));

        /*
         * Last name field.
         */
        queue(new FormGroup("lastNameGroup"));
        queue(new TextField<>("lastNameField",
                new PropertyModel<>(model, "lastName"))
                .setLabel(new ResourceModel("label.last.name"))
                .setRequired(true)
                .add(new InputBehavior()));

        /*
         * Save button.
         */
        queue(new EventAjaxButton("saveButton", Type.Primary,
                (target, payload) -> new SaveEvent<>(target, payload))
                .setIconType(GlyphIconType.check)
                .setLabel(new ResourceModel("label.save")));

        /*
         * Cancel button.
         */
        queue(new EventAjaxButton("cancelButton", Type.Default,
                (target, payload) -> new CancelEvent(target))
                .setIconType(GlyphIconType.remove)
                .setLabel(new ResourceModel("label.cancel"))
                .setDefaultFormProcessing(false));
        // end::form[]
    }

    // tag::handle-validation-error[]
    @OnEvent(types = Customer.class)
    public void handleValidationErrorEvent(ValidationErrorEvent<Customer> event) {
        event.getTarget().add(form);
    }
    // end::handle-validation-error[]

}
