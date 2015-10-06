package util;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

public class ButtonPanel extends Panel {

	public <T> ButtonPanel(String id, IModel<T> model, SerializableFunction<String, List<Component>> buttonFactory) {
		super(id, model);
		Form<?> form = new Form<>("form", model);
		add(form);

		form.add(new ListView<Component>("buttons", new ListModel<>(buttonFactory.apply("button"))) {

			@Override
			protected void populateItem(ListItem<Component> item) {
				item.add(item.getModel().getObject());
			}

		});
	}

}
