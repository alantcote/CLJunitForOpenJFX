package net.sf.cotelab.jfxdemo;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DemoWebView extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(final Stage mystage) {
		final BrowserPane myBrowserPane = new BrowserPane();
		ReadOnlyStringProperty titleProperty = myBrowserPane.titleProperty();

		titleProperty.addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				String title = myBrowserPane.getTitle();

				if (title != null) {
					mystage.setTitle("DemoWebView - " + title);
				}
			}

		});

//		myBrowserPane.load("http://www.google.com");

		Scene myscene = new Scene(myBrowserPane, 960, 600);

		mystage.setScene(myscene);

//		mystage.setOnShown(new EventHandler<WindowEvent>() {
//
//			@Override
//			public void handle(WindowEvent event) {
//
//				tryInDialog(mystage);
//
//			}
//
//		});

		mystage.show();
	}

	protected void tryInDialog(Stage parentStage) {
		Dialog<ButtonType> myDialog = new Dialog<ButtonType>();
		DialogPane myDialogPane = myDialog.getDialogPane();
		ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		WebView myview = new WebView();

		myDialog.initOwner(parentStage);
		myDialog.setTitle("Sample WebView Dialog");
		myDialogPane.getButtonTypes().add(okButtonType);
		myDialogPane.lookupButton(okButtonType).setDisable(false);

		// setting min height
		myview.minHeight(1050);
		// setting preferred width
		myview.prefWidth(1950);
		// setting pref heigth
		myview.prefHeight(1070);
		// setting min width
		myview.minWidth(1050);
		final WebEngine mywebEngine = myview.getEngine();
		mywebEngine.load("http://openjfx.io");

		myDialogPane.setContent(myview);
		myDialog.showAndWait();
	}
}
