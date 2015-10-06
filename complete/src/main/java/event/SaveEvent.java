package event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.event.annotation.AbstractPayloadTypedEvent;

public class SaveEvent<T> extends AbstractPayloadTypedEvent<T> {

	public SaveEvent(AjaxRequestTarget target, T payload) {
		super(target, payload);
	}

}
