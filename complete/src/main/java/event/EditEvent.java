package event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.event.annotation.AbstractPayloadTypedEvent;

public class EditEvent<T> extends AbstractPayloadTypedEvent<T> {

	public EditEvent(AjaxRequestTarget target, T payload) {
		super(target, payload);
	}

}
