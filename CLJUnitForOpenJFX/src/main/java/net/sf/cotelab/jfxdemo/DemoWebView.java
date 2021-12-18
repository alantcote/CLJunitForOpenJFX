package net.sf.cotelab.jfxdemo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DemoWebView extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(final Stage mystage) {
		System.out.println("Demo for webview");
//webview object creation
//web engine object creation
		WebView myview = new WebView();
// setting min height
		myview.minHeight(1050);
// setting preferred width
		myview.prefWidth(1950);
// setting pref heigth
		myview.prefHeight(1070);
//setting min width
		myview.minWidth(1050);
		final WebEngine mywebEngine = myview.getEngine();
		mywebEngine.load("http://www.google.com");
		VBox mybox = new VBox(myview);
		Scene myscene = new Scene(mybox, 960, 600);
		mystage.setScene(myscene);

		mystage.setOnShown(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				tryInDialog(mystage);

			}

		});

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
