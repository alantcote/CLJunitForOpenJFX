package net.sf.cotelab.jfxdemo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class BrowserPane extends javafx.scene.layout.BorderPane {

	protected Worker<Void> myLoadWorker;
	protected WebEngine myWebEngine;
	protected WebView myWebView;
	protected TextField urlTextField;
	protected ProgressBar loadProgressBar;

	public BrowserPane() {
		super();

		urlTextField = new TextField();

		setTop(urlTextField);

		myWebView = new WebView();
		myWebEngine = myWebView.getEngine();
		myLoadWorker = getLoadWorker();

		myWebEngine.locationProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				String location = getLocation();

				if (location == null) {
					urlTextField.setText("");
				} else {
					urlTextField.setText(location);
				}
			}

		});

		urlTextField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String url = urlTextField.getText();

				if ((url != null) && (url.length() > 0)) {
					load(url);
				}
			}

		});

		myWebView.prefWidth(1000);
		myWebView.prefHeight(1200);

		setCenter(myWebView);
		
		loadProgressBar = new ProgressBar();
		loadProgressBar.setProgress(0);
		
		setBottom(loadProgressBar);
		
		if (myLoadWorker == null) {
			System.out.println("myLoadWorker is null");
		} else {
			ReadOnlyDoubleProperty workDoneProperty = myLoadWorker.workDoneProperty();
			
			workDoneProperty.addListener(new ChangeListener<Object>() {

				@Override
				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
					double workDone = myLoadWorker.getWorkDone();
					double totalWork = myLoadWorker.getTotalWork();
					
					if ((workDone >= 0) && (totalWork > 0)) {
						workDone /= totalWork;
						
						if (workDone >= 0.99) {
							workDone = 0;
						}
					}
					
					loadProgressBar.setProgress(workDone);
				}
				
			});
		}
	}

	public WebHistory getHistory() {
		return myWebEngine.getHistory();
	}

	public final Worker<Void> getLoadWorker() {
		return myWebEngine.getLoadWorker();
	}

	public final String getLocation() {
		return myWebEngine.getLocation();
	}

	public final String getTitle() {
		return myWebEngine.getTitle();
	}

	public void load(String string) {
		myWebEngine.load(string);
	}

	public final ObjectProperty<EventHandler<WebEvent<Boolean>>> onVisibilityChangedProperty() {
		return myWebEngine.onVisibilityChangedProperty();
	}

	public void reload() {
		myWebEngine.reload();
	}

	public final ReadOnlyStringProperty titleProperty() {
		return myWebEngine.titleProperty();
	}
}
