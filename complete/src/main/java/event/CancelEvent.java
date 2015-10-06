package event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.event.annotation.AbstractAjaxAwareEvent;

public class CancelEvent extends AbstractAjaxAwareEvent {

	public CancelEvent(AjaxRequestTarget target) {
		super(target);
	}

}
