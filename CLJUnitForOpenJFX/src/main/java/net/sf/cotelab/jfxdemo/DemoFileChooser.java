package net.sf.cotelab.jfxdemo;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DemoFileChooser extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"), new ExtensionFilter("All Files", "*.*"));
//		File selectedFile = fileChooser.showOpenDialog(primaryStage);
//		if (selectedFile == null) {
//			System.out.println("*** No file selected ***");
//		} else {
//			System.out.println(selectedFile);
//		}
		List<File> fileList = fileChooser.showOpenMultipleDialog(primaryStage);
		if (fileList == null) {
			System.out.println("*** No files selected ***");
		} else {
			for (File file : fileList) {
				System.out.println(file);
			}
		}

//		System.exit(0);
		
		TreeView<File> treeView = buildFileSystemBrowser();
		VBox mybox = new VBox(treeView);
		Scene myscene = new Scene(mybox, 960, 600);
		primaryStage.setScene(myscene);
		
		primaryStage.show();
	}

	private TreeView<File> buildFileSystemBrowser() {
		TreeItem<File> root = createNode(new File("/"));
		return new TreeView<File>(root);
	}

	// This method creates a TreeItem to represent the given File. It does this
	// by overriding the TreeItem.getChildren() and TreeItem.isLeaf() methods
	// anonymously, but this could be better abstracted by creating a
	// 'FileTreeItem' subclass of TreeItem. However, this is left as an exercise
	// for the reader.
	private TreeItem<File> createNode(final File f) {
		return new TreeItem<File>(f) {
			// We cache whether the File is a leaf or not. A File is a leaf if
			// it is not a directory and does not have any files contained within
			// it. We cache this as isLeaf() is called often, and doing the
			// actual check on File is expensive.
			private boolean isLeaf;

			// We do the children and leaf testing only once, and then set these
			// booleans to false so that we do not check again during this
			// run. A more complete implementation may need to handle more
			// dynamic file system situations (such as where a folder has files
			// added after the TreeView is shown). Again, this is left as an
			// exercise for the reader.
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override
			public ObservableList<TreeItem<File>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;

					// First getChildren() call, so we actually go off and
					// determine the children of the File contained in this TreeItem.
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					File f = (File) getValue();
					isLeaf = f.isFile();
				}

				return isLeaf;
			}

			private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
				File f = TreeItem.getValue();
				if (f != null && f.isDirectory()) {
					File[] files = f.listFiles();
					if (files != null) {
						ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

						for (File childFile : files) {
							children.add(createNode(childFile));
						}

						return children;
					}
				}

				return FXCollections.emptyObservableList();
			}
		};
	}

}
