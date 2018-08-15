package io.webfolder.cdp;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * JsFunction marks a functional interface as being the definition of a JavaScript function.
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface JsFunction {

	/**
	 * javascript function body
	 */
	String value();
}
