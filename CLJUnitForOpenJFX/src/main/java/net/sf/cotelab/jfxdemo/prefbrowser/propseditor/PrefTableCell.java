package net.sf.cotelab.jfxdemo.prefbrowser.propseditor;

import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PrefTableCell extends TableCell<Preference, String> {
	protected Label errMssgLabel = null;
	protected String newItem = null;
	protected String oldItem = null;
	protected TextField textField = new TextField();

	public PrefTableCell(Label errMssgLabel) {
		super();
		
		this.errMssgLabel = errMssgLabel;
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		// TODO is there anything extra needed here?
		
		setNormalGUI();
	}
	
	@Override
	public void commitEdit(String newValue) {
		super.commitEdit(newValue);

		// TODO is there anything extra needed here?
		
		setNormalGUI();
	}

	@Override
	public void startEdit() {
		super.startEdit();

		TableColumn<Preference, String> column = getTableColumn();
		String tcText = column.getText();
		int maxColCount = (tcText.equals(PrefPropsPane.KEY_COL_TITLE)) ? Preferences.MAX_KEY_LENGTH : Preferences.MAX_VALUE_LENGTH;

		int index = getIndex();
		oldItem = (String) column.getCellData(index);
		
		if (isEditable()) {
			textField.setPrefColumnCount(maxColCount);
			textField.setText(oldItem);
			textField.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					int newLength;
					
					newItem = textField.getText();
					
					if (newItem == null) {
						errMssgLabel.setText("Value is null");
						
						return;
					}
					
					newLength = newItem.length();

					// validate
					if (newLength > maxColCount) {
						errMssgLabel.setText("Entry is longer than " + maxColCount + " characters!");
						
						return;
					}
					
					commitEdit(newItem);
				}
				
			});
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
//					System.out.println("PrefTableCell.startEdit() textField setOnKeyReleased handler: entry");
//					System.out.println("PrefTableCell.startEdit() textField setOnKeyReleased handler: event.getCode() = " + event.getCode());
//					System.out.println("PrefTableCell.startEdit() textField setOnKeyReleased handler: event.getCharacter() = " + event.getCharacter());
					if (KeyCode.ESCAPE == event.getCode()) {
//						System.out.println("PrefTableCell.startEdit() textField setOnKeyReleased handler: escape detected");
						textField.setText(oldItem);
						
						textField.cancelEdit();
					}
				}
				
			});
			
			setText(null);
			setGraphic(textField);
		}
	}

	protected void setNormalGUI() {
		String item = getItem();

        setText(item.toString());
        setGraphic(null);
	}

	@Override
	protected void updateItem(String item, boolean empty) {
	     super.updateItem(item, empty);

	     if (empty || item == null) {
	         setText(null);
	         setGraphic(null);
	     } else {
	    	 setNormalGUI();
	     }
	 }

}
