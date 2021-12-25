/**
 * 
 */
package net.sf.cotelab.jfxdemo.prefbrowser;

import java.util.prefs.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * @author TEST
 *
 */
public class PrefCellFactory implements Callback<TreeView<Preferences>, TreeCell<Preferences>> {
	protected PreferencesBrowser preferencesBrowser = null;

	public PrefCellFactory(PreferencesBrowser preferencesBrowser) {
		super();
		
		this.preferencesBrowser = preferencesBrowser;
	}

	@Override
	public TreeCell<Preferences> call(TreeView<Preferences> param) {
		final TreeCell<Preferences> tc = new PrefTreeCell(preferencesBrowser);
		ObjectProperty<EventHandler<? super ContextMenuEvent>> ocmr = tc.onContextMenuRequestedProperty();

//	System.out.println("PrefCellFactory.call: param = " + param);

		ocmr.setValue(new ContextMenuRequestHandler(tc));
		
		tc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tc.startEdit();
				}
			}
			
		});

		return tc;
	}

}
