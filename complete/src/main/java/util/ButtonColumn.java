package util;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public class ButtonColumn<T, S> extends AbstractColumn<T, S>{

	private SerializableFunction<String, List<Component>> buttonFactory;

	public ButtonColumn(IModel<String> displayModel, SerializableFunction<String, List<Component>> buttonFactory) {
		super(displayModel);
		this.buttonFactory = buttonFactory;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new ButtonColumnPanel(componentId, rowModel));
	}

	@Override
	public String getCssClass() {
		return "text-center";
	}

	private class ButtonColumnPanel extends Panel {

		public ButtonColumnPanel(String id, IModel<T> model) {
			super(id, model);
			add(new ButtonPanel("panel", model, buttonFactory));
		}

	}

}
