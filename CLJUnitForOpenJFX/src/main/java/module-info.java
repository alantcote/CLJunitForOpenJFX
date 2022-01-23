/**
 * Support for JUnit testing of code built on OpenJFX.
 */
module cljunitforopenjfx {
	exports io.github.alantcote.jfxrunner;
	
	requires transitive javafx.graphics;
	requires junit;
}