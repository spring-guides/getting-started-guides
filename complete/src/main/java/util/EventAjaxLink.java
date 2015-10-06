package util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;

public class EventAjaxLink<T> extends BootstrapAjaxLink<T> {

	private SerializableFunction<AjaxRequestTarget, Object> eventGenerator;

	public EventAjaxLink(String componentId, Type type, SerializableFunction<AjaxRequestTarget, Object> eventGenerator) {
		super(componentId, type);
		this.eventGenerator = eventGenerator;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		send(this, Broadcast.BUBBLE, eventGenerator.apply(target));
	}

}
