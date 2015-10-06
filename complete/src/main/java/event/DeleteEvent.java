package event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.event.annotation.AbstractPayloadTypedEvent;

public class DeleteEvent<T> extends AbstractPayloadTypedEvent<T> {

	public DeleteEvent(AjaxRequestTarget target, T payload) {
		super(target, payload);
	}

}
