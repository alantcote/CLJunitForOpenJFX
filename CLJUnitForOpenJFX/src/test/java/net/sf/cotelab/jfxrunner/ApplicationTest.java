package net.sf.cotelab.jfxrunner;

import static org.junit.Assert.*;
import javafx.scene.Group;
import javafx.scene.Scene;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This is a sample test class for java fx tests.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class ApplicationTest {
	/**
	 * Daft normal test.
	 */
	@Test
	public void testNormal() {
		assertTrue(true);
	}

	/**
	 * Test which would normally fail without running on the JavaFX thread.
	 */
	@Test
	public void testNeedsJavaFX() {
		@SuppressWarnings("unused")
		Scene scene = new Scene(new Group());
		assertTrue(true);
	}
}
