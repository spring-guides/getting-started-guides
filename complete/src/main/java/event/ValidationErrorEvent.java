package event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.event.annotation.AbstractPayloadTypedEvent;

public class ValidationErrorEvent<T> extends AbstractPayloadTypedEvent<T> {

	public ValidationErrorEvent(AjaxRequestTarget target, T payload) {
		super(target, payload);
	}

}
