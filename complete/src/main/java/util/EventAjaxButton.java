package util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import event.ValidationErrorEvent;

public class EventAjaxButton extends BootstrapAjaxButton {

	private SerializableBiFunction<AjaxRequestTarget, Object, Object> submitEventGenerator;

	public EventAjaxButton(String componentId, Type type, SerializableBiFunction<AjaxRequestTarget, Object, Object> eventGenerator) {
		super(componentId, type);
		this.submitEventGenerator = eventGenerator;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		send(this, Broadcast.BUBBLE, submitEventGenerator.apply(target, form.getModelObject()));
	}

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        send(this, Broadcast.BUBBLE, new ValidationErrorEvent<>(target, form.getModelObject()));
    }

}
