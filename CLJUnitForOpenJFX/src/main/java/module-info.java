/**
 * Support for JUnit testing of code built on OpenJFX.
 *
 */
module cljunitforopenjfx {
	exports net.sf.cotelab.jfxrunner;
	
	requires transitive javafx.graphics;
	requires junit;
}