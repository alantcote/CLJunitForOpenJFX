package net.sf.cotelab.jfxdemo.prefbrowser.propseditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

public class DeleteRowHandler implements EventHandler<ActionEvent> {
	protected PrefTableCell tableCell;

	public DeleteRowHandler(PrefTableCell aTableCell) {
		tableCell = aTableCell;
	}

	@Override
	public void handle(ActionEvent event) {
		TableView<Preference> tableView = tableCell.getTableView();
//		TableColumn<Preference,String> tableColumn = tableCell.getTableColumn();
		int row = tableCell.getIndex();

		tableView.itemsProperty().getValue().remove(row);
	}

}
