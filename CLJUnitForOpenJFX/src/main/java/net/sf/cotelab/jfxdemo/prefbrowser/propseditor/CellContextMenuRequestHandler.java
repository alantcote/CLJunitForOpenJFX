package net.sf.cotelab.jfxdemo.prefbrowser.propseditor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class CellContextMenuRequestHandler implements EventHandler<ContextMenuEvent> {
	protected PrefTableCell tableCell;

	public CellContextMenuRequestHandler(PrefTableCell aTableCell) {
		tableCell = aTableCell;
	}

	@Override
	public void handle(ContextMenuEvent event) {
		ContextMenu theMenu = new ContextMenu();
		ObservableList<MenuItem> itemList = theMenu.getItems();
		MenuItem deleteRowItem = new MenuItem("Delete row");
		double x = event.getScreenX();
		double y = event.getScreenY();
		MenuItem addRowItem = new MenuItem("Add row");

		addRowItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableCell.getTableView().itemsProperty().getValue().add(new Preference("", ""));

				tableCell.getTableView().refresh();
			}

		});
		
		itemList.add(addRowItem);

		deleteRowItem.setOnAction(new DeleteRowHandler(tableCell));
		itemList.add(deleteRowItem);
		

		theMenu.show(tableCell, x, y);
	}

}
