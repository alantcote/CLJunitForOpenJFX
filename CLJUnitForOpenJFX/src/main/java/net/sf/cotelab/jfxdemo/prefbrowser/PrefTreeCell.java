package net.sf.cotelab.jfxdemo.prefbrowser;

import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PrefTreeCell extends TreeCell<Preferences> {
	protected Preferences item = null;
	protected TextField nameTextField = new TextField();
	protected PreferencesBrowser preferencesBrowser = null;

	public PrefTreeCell(PreferencesBrowser preferencesBrowser) {
		super();
		
		this.preferencesBrowser = preferencesBrowser;
	}

	@Override
	public void cancelEdit() {
		System.out.println("PrefTreeCell.cancelEdit(): calling establishNormalGUI()");

		establishNormalGUI();

		System.out.println("PrefTreeCell.cancelEdit(): calling super.cancelEdit()");

		super.cancelEdit();
	}

	@Override
	public void startEdit() {
		System.out.println("PrefTreeCell.startEdit(): entry");

		super.startEdit();

		// switch to a text field
		TreeView<Preferences> tv = getTreeView();
		int index = super.getIndex();
		TreeItem<Preferences> theTreeItem = tv.getTreeItem(index);
		final Preferences pref = theTreeItem.getValue();

		System.out.println("PrefTreeCell.startEdit(): pref = " + pref);

		nameTextField.setText(pref.name());

		System.out.println("PrefTreeCell.startEdit(): calling nameTextField.setOnAction()");

		nameTextField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String proposedName = nameTextField.getText();

				if (0 < proposedName.length()) {
					if (0 < proposedName.indexOf('/')) {
						commitEdit(pref);
					}
				}
			}

		});

		System.out.println("PrefTreeCell.startEdit(): calling nameTextField.addEventHandler()");

		nameTextField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (KeyCode.ESCAPE == event.getCode()) {
					cancelEdit();
				}
			}

		});

		System.out.println("PrefTreeCell.startEdit(): calling setText(null)");

		setText(null);

		System.out.println("PrefTreeCell.startEdit(): calling setGraphic(nameTextField)");

		setGraphic(nameTextField);

		System.out.println("PrefTreeCell.startEdit(): exit");
	}

	@Override
	public void updateIndex(int i) {
		System.out.println("PrefTreeCell.updateIndex(): i = " + i);
		
		super.updateIndex(i);

		System.out.println("PrefTreeCell.updateIndex(): calling establishNormalGUI(pref): i = " + i);

		establishNormalGUI();
	}

	protected void establishNormalGUI() {
		TreeView<Preferences> tv = getTreeView();
		TreeItem<Preferences> tc = tv.getTreeItem(getIndex());

		System.out.println("PrefTreeCell.establishNormalGUI(): getIndex() = " + getIndex());
		
		super.setGraphic(null);

		if (tc == null) {
			setText("");
		} else {
			Preferences pref = tc.getValue();

			System.out.println("PrefTreeCell.establishNormalGUI(): calling textForPref(pref): pref = " + pref);

			setText(textForPref(pref));

			if (pref == null) {
				setEditable(false);
			} else {
				String name = pref.name();

				if (name.length() == 0) {
					setEditable(false);
				} else {
					setEditable(true);
				}
			}
		}
	}

	public String textForPref(Preferences pref) {
		String tfp;

		System.out.println("PrefTreeCell.textForPref(): pref = " + pref);

		if (pref == null) {
			tfp = "Preferences";
		} else {
			String name = pref.name();

			if (name.length() == 0) {
//				if (pref.isUserNode()) {
//					tfp = "User Preferences";
//				} else {
//					tfp = "System Preferences";
//				}
				tfp = pref.toString();
			} else {
				tfp = pref.name();
			}
		}

		return tfp;
	}

	protected void updateItem(Preferences item, boolean empty) {
		System.out.println("PrefTreeCell.updateItem(): item = " + item);
		System.out.println("PrefTreeCell.updateItem(): empty = " + empty);
		
		super.updateItem(item, empty);

		this.item = item;

		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			System.out.println("PrefTreeCell.updateItem(): calling textForPref(item): item = " + item);
			
			setText(textForPref(item));
			
			try {
				item.addNodeChangeListener(new NodeChangeListener() {

					@Override
					public void childAdded(NodeChangeEvent evt) {
						getTreeView().refresh();
					}

					@Override
					public void childRemoved(NodeChangeEvent evt) {
						getTreeView().refresh();
					}
					
				});
			} catch (IllegalStateException e) {
				// report it and continue
//				System.err.println("PrefTreeCell.updateItem(): caught . . .");
//				e.printStackTrace();
//				System.err.println("PrefTreeCell.updateItem(): proceeding . . .");
			}
		}
	}

	@Override
	public void updateSelected(boolean selected) {
		System.out.println("PrefTreeCell.updateSelected(): selected = " + selected);
		
		super.updateSelected(selected);
		
		if (isSelected()) {
        	preferencesBrowser.setSelectedTreeCell(this);
		}
	}

}
