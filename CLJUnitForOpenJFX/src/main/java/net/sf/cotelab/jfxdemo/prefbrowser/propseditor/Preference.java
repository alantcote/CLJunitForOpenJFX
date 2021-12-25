package net.sf.cotelab.jfxdemo.prefbrowser.propseditor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Preference {

	protected StringProperty defProperty;
	protected StringProperty keyProperty;

	public Preference(String aKey, String aDef) {
		setKey(aKey);
		setDef(aDef);
	}

	public StringProperty defProperty() {
		if (defProperty == null) {
			defProperty = new SimpleStringProperty(this, "defProperty");
		}

		return defProperty;
	}

	public String getDef() {
		return defProperty().get();
	}

	public String getKey() {
		return keyProperty().get();
	}

	public StringProperty keyProperty() {
		if (keyProperty == null) {
			keyProperty = new SimpleStringProperty(this, "keyProperty");
		}

		return keyProperty;
	}

	public void setDef(String def) {
		defProperty().set(def);
	}

	public void setKey(String key) {
		keyProperty().set(key);
	}

}
