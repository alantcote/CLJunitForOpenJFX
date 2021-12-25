package net.sf.cotelab.jfxdemo.prefbrowser;

import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.cotelab.jfxdemo.prefbrowser.propseditor.PrefPropsPane;

public class PreferencesBrowser extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	protected PrefPropsPane prefPropsPane = new PrefPropsPane();

	public void setSelectedTreeCell(PrefTreeCell prefTreeCell) {
		prefPropsPane.setPrefTreeCell(prefTreeCell);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PrefTreeItem rootItem = new PrefTreeItem(null);
		TreeView<Preferences> prefTreeView = new TreeView<>(rootItem);

		prefTreeView.cellFactoryProperty().set(new PrefCellFactory(this));
		prefTreeView.setShowRoot(false);

		BorderPane sceneRootPane = new BorderPane();

		sceneRootPane.setLeft(prefTreeView);

//		prefPropsPane.setVisible(false);
		sceneRootPane.setCenter(prefPropsPane);

		Scene myscene = new Scene(sceneRootPane, 960, 600);

		primaryStage.setScene(myscene);
		primaryStage.setTitle("Preferences Browser");

		primaryStage.show();
	}

}
