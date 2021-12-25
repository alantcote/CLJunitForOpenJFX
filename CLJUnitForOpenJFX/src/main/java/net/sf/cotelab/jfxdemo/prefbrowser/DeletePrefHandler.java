package net.sf.cotelab.jfxdemo.prefbrowser;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TreeCell;

public class DeletePrefHandler implements EventHandler<ActionEvent> {
	protected PrefTreeCell theTreeCell;

	public DeletePrefHandler(TreeCell<Preferences> aTreeCell) {
		theTreeCell = (PrefTreeCell) aTreeCell;
	}

	@Override
	public void handle(ActionEvent event) {
		// pop up a dialog to question the deletion
		System.out.println("DeletePrefHandler.handle(): entry");

		ButtonType okButtonType = ButtonType.OK;
		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		Preferences pref = theTreeCell.getItem();
		String prefName = pref.absolutePath();

		dialogPane.setContentText("Delete preference \"" + prefName + "\"?");
		dialog.setTitle("Verify deletion");
		dialogPane.getButtonTypes().add(okButtonType);

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			ButtonType resultType = result.get();

			System.out.println("DeletePrefHandler.handle(): result = " + resultType);

			if (okButtonType == resultType) {
				PrefTreeItem parentTreeItem = (PrefTreeItem) theTreeCell.getTreeItem().getParent();

				try {
					pref.removeNode();

					parentTreeItem.setChildItemsCached(false);
//					theTreeCell.getTreeView().refresh();
				} catch (BackingStoreException e) {
					// report it and continue
					System.err.println("DeletePrefHandler.handle(): caught . . .");
					e.printStackTrace();
					System.err.println("DeletePrefHandler.handle(): proceeding . . .");
				}
			}
		}
	}

}
