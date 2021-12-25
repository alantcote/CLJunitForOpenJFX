package net.sf.cotelab.jfxdemo.prefbrowser;

import java.util.prefs.Preferences;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.input.ContextMenuEvent;

public class ContextMenuRequestHandler implements EventHandler<ContextMenuEvent> {
	protected TreeCell<Preferences> theTreeCell;

	public ContextMenuRequestHandler(TreeCell<Preferences> treeCell) {
		theTreeCell = treeCell;
	}

	@Override
	public void handle(ContextMenuEvent event) {
		ContextMenu theMenu = new ContextMenu();
		ObservableList<MenuItem> itemList = theMenu.getItems();
		MenuItem addChildItem = new MenuItem("Add child");
		MenuItem deletePrefItem = new MenuItem("Delete");
		Preferences pref = theTreeCell.getItem();

		if (null != pref) {
			double x = event.getScreenX();
			double y = event.getScreenY();

			addChildItem.setOnAction(new AddChildHandler(theTreeCell));
			deletePrefItem.setOnAction(new DeletePrefHandler(theTreeCell));

			itemList.add(addChildItem);
			itemList.add(deletePrefItem);

			theMenu.show(theTreeCell, x, y);
		}
	}

}
