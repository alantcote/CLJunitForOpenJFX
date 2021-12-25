package net.sf.cotelab.jfxdemo.prefbrowser.propseditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import net.sf.cotelab.jfxdemo.prefbrowser.PrefTreeCell;

public class PrefPropsPane extends BorderPane {
	public static final String DEF_COL_TITLE = "Def";
	public static final String KEY_COL_TITLE = "Key";
	protected Button applyButton = new Button("Apply");
	protected Button cancelButton = new Button("Cancel");
	protected BorderPane centerPane = new BorderPane();
	protected TableColumn<Preference, String> defCol = new TableColumn<>(DEF_COL_TITLE);
	protected boolean editable = false;
	protected Label errMssgLabel = new Label();
	protected TableColumn<Preference, String> keyCol = new TableColumn<>(KEY_COL_TITLE);
	protected Label paneLabel = new Label("Preferences Node");
	protected List<Preference> prefItems = new ArrayList<>();
	protected List<Preference> prefList = new ArrayList<>();
	protected String prefName = null;
	protected TextField prefNameTextField = new TextField();
	protected Preferences prefsNode = null;
	protected TableView<Preference> prefTable = new TableView<>();
	protected PrefTreeCell prefTreeCell = null;
	protected ObservableList<Preference> tableModel = FXCollections.observableArrayList(prefList);
	protected BorderPane topPane = new BorderPane();

	@SuppressWarnings("unchecked")
	public PrefPropsPane() {
		super();

		prefName = prefNameTextField.getText();

		Font baseFont = paneLabel.getFont();

		paneLabel.setFont(Font.font(baseFont.getFamily(), FontWeight.BOLD, baseFont.getSize() * 2));
		paneLabel.setAlignment(Pos.CENTER);

		topPane.setCenter(paneLabel);

		setTop(topPane);

		// center should be another BorderPane with the actual Preferences info

		prefNameTextField.setAlignment(Pos.CENTER);
		prefNameTextField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				applyButton.setVisible(true);
				cancelButton.setVisible(true);
			}

		});
		prefNameTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
//				System.out.println("PrefPropsPane.PrefPropsPane() prefNameTextField setOnKeyReleased handler: entry");
//				System.out.println("PrefPropsPane.PrefPropsPane() prefNameTextField setOnKeyReleased handler: event.getCode() = " + event.getCode());
//				System.out.println("PrefPropsPane.PrefPropsPane() prefNameTextField setOnKeyReleased handler: event.getCharacter() = " + event.getCharacter());
				if (KeyCode.ESCAPE == event.getCode()) {
//					System.out.println("PrefPropsPane.PrefPropsPane() prefNameTextField setOnKeyReleased handler: escape detected");
					prefNameTextField.setText(prefName);

					prefNameTextField.cancelEdit();
				}
			}

		});

		centerPane.setTop(prefNameTextField);

		keyCol.setCellValueFactory(new Callback<CellDataFeatures<Preference, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Preference, String> p) {
				// p.getValue() returns the Preference instance for a particular TableView row
				return p.getValue().keyProperty();
			}
		});
		keyCol.setCellFactory(new Callback<TableColumn<Preference, String>, TableCell<Preference, String>>() {

			@Override
			public TableCell<Preference, String> call(TableColumn<Preference, String> param) {
				PrefTableCell cell = new PrefTableCell(errMssgLabel);

				cell.setOnContextMenuRequested(new CellContextMenuRequestHandler(cell));
				cell.setEditable(true);

				return cell;
			}

		});
		keyCol.setEditable(true);
		keyCol.addEventHandler(TableColumn.editCommitEvent(), new EventHandler<CellEditEvent<Preference, String>>() {

			@Override
			public void handle(CellEditEvent<Preference, String> event) {
				applyButton.setVisible(true);
				cancelButton.setVisible(true);
			}

		});

		defCol.setCellValueFactory(new Callback<CellDataFeatures<Preference, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Preference, String> p) {
				// p.getValue() returns the Preference instance for a particular TableView row
				return p.getValue().defProperty();
			}
		});
		defCol.setCellFactory(new Callback<TableColumn<Preference, String>, TableCell<Preference, String>>() {

			@Override
			public TableCell<Preference, String> call(TableColumn<Preference, String> param) {
				PrefTableCell cell = new PrefTableCell(errMssgLabel);

				cell.setOnContextMenuRequested(new CellContextMenuRequestHandler(cell));
				cell.setEditable(true);

				return cell;
			}

		});
		defCol.setEditable(true);
		defCol.addEventHandler(TableColumn.editCommitEvent(), new EventHandler<CellEditEvent<Preference, String>>() {

			@Override
			public void handle(CellEditEvent<Preference, String> event) {
				applyButton.setVisible(true);
				cancelButton.setVisible(true);
			}

		});

		prefList = new ArrayList<Preference>();
		for (Preference prefItem : prefItems) {
			prefList.add(new Preference(prefItem.getKey(), prefItem.getDef()));
		}

		prefTable.getColumns().setAll(keyCol, defCol);
		prefTable.setItems(tableModel);
		prefTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		prefTable.setEditable(true); // TODO temporary for testing

		prefTable.itemsProperty().addListener(new ChangeListener<ObservableList<Preference>>() {

			@Override
			public void changed(ObservableValue<? extends ObservableList<Preference>> observable,
					ObservableList<Preference> oldValue, ObservableList<Preference> newValue) {
				applyButton.setVisible(true);
				cancelButton.setVisible(true);
			}

		});

		centerPane.setCenter(prefTable);

		errMssgLabel.setTextFill(Color.RED);

		centerPane.setBottom(errMssgLabel);

		setCenter(centerPane);

		// bottom is for buttons

		applyButton.setVisible(false);
		cancelButton.setVisible(false);

		applyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				doApply();
			}

		});
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				doCancel();
			}

		});

		FlowPane flowPane = new FlowPane();

		flowPane.setAlignment(Pos.BASELINE_RIGHT);
		flowPane.getChildren().add(cancelButton);
		flowPane.getChildren().add(applyButton);

		setBottom(flowPane);
	}

	public PrefTreeCell getPrefTreeCell() {
		return prefTreeCell;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;

		prefNameTextField.setEditable(editable);
		prefTable.setEditable(editable);
	}

	public void setPrefTreeCell(PrefTreeCell prefTreeCell) {
		boolean canEdit = true;

		this.prefTreeCell = prefTreeCell;

		// TODO set up prefItems, prefName, prefTable and prefNameTextField
		prefsNode = prefTreeCell.getItem();
		prefItems = new ArrayList<Preference>();
		prefList = new ArrayList<Preference>();

		if (prefsNode == null) {
			canEdit = false;
		} else {
			if (prefsNode.name().length() == 0) {
				canEdit = false;
			}

			try {
				String[] keys = prefsNode.keys();

				for (String key : keys) {
					String def = prefsNode.get(key, "<default>");
					Preference pref = new Preference(key, def);

					prefItems.add(pref);
					prefList.add(pref);
				}
			} catch (BackingStoreException e) {
				System.err.println("PrefPropsPane.setPrefTreeCell(): caught . . .");
				e.printStackTrace();

				System.exit(1);
			} catch (IllegalStateException e) {
				// report it and continue
//				System.err.println("PrefPropsPane.setPrefTreeCell(): caught . . .");
//				e.printStackTrace();
//				System.err.println("PrefPropsPane.setPrefTreeCell(): proceeding . . .");
			}
		}

		System.out.println("PrefPropsPane.setPrefTreeCell(): calling textForPref(prefsNode): prefsNode = " + prefsNode);

		prefName = prefTreeCell.textForPref(prefsNode);
		prefNameTextField.setText(prefName);

		tableModel = FXCollections.observableArrayList(prefList);
		prefTable.setItems(tableModel);

		setEditable(canEdit);
	}

	protected void doApply() {
		System.out.println("PrefPropsPane.doApply(): entry");

		String errMssg = null;
		String proposedName = prefNameTextField.getText();
		String existingName = prefsNode.name();

		errMssgLabel.setText("");

		if (!existingName.equals(proposedName)) {
			if (proposedName.length() == 0) {
				errMssg = "Node name length must be greater than zero.";
			} else {
				if (proposedName.length() > Preferences.MAX_NAME_LENGTH) {
					errMssg = "Node name length must be no greater than " + Preferences.MAX_NAME_LENGTH + ".";
				} else {
					if (proposedName.indexOf('/') > -1) {
						errMssg = "New node name is must not contain '/' characters.";
					} else {
						try {
							if (prefsNode.nodeExists(proposedName)) {
								errMssg = "New node name is in use by another node.";
							} else {
								Preferences contextNode = prefsNode.isUserNode() ? Preferences.userRoot()
										: Preferences.systemRoot();
								Preferences newNode = contextNode.node(proposedName);

								contextNode.sync();

								prefTreeCell.setItem(newNode);

								prefsNode.removeNode();
								prefsNode = newNode;

								contextNode.sync();
							}
						} catch (BackingStoreException e) {
							System.err.println("PrefPropsPane.doApply(): caught . . .");
							e.printStackTrace();
							errMssg = "BackingStoreException caught checking for existing node.";
						}
					}
				}
			}
		}

		// TODO validate the table contents: no duplicate keys, no illegal keys or
		// values, etc.

		Properties prefProps = new Properties();

		if (errMssg == null) {
			for (Preference pref : tableModel) {
				String key = pref.getKey();
				String def = pref.getDef();

				if ((key == null) || (key.length() > Preferences.MAX_KEY_LENGTH)) {
					errMssg = "A key is null or longer than " + Preferences.MAX_KEY_LENGTH + " characters.";

					break;
				}

				if ((def == null) || (def.length() > Preferences.MAX_VALUE_LENGTH)) {
					errMssg = "A def is null or longer than " + Preferences.MAX_VALUE_LENGTH + " characters.";

					break;
				}

				if (prefProps.containsKey(key)) {
					errMssg = "There are duplicate keys.";

					break;
				}

				prefProps.put(key, def);
			}
		}

		if (errMssg == null) {
			// really apply the changes
			try {
				prefsNode.clear();

				for (Object keyObj : prefProps.keySet().toArray()) {
					String key = (String) keyObj;
					String def = (String) prefProps.getProperty(key);

					prefsNode.put(key, def);
				}

				// the node name change, if any, already happened

				prefsNode.sync();
			} catch (BackingStoreException e) {
				System.err.println("PrefPropsPane.doApply(): caught . . .");
				e.printStackTrace();
				errMssg = "BackingStoreException caught while updating node.";
			}

			applyButton.setVisible(false);
			cancelButton.setVisible(false);
		}

		errMssgLabel.setText(errMssg);

		System.out.println("PrefPropsPane.doApply(): exit");
	}

	protected void doCancel() {
		System.out.println("PrefPropsPane.doCancel(): entry");

		Iterator<Preference> iter = tableModel.iterator();

		for (Preference prefItem : prefItems) {
			Preference model = iter.next();

			model.setKey(prefItem.getKey());
			model.setDef(prefItem.getDef());
		}

		prefNameTextField.setText(prefName);

		applyButton.setVisible(false);
		cancelButton.setVisible(false);

		errMssgLabel.setText("");
	}

}
