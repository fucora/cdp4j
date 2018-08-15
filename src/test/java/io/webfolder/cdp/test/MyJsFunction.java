package io.webfolder.cdp.test;

import java.util.List;

import io.webfolder.cdp.JsFunction;

public interface MyJsFunction {

	@JsFunction("let attributes = []; document.querySelectorAll(selector).forEach(e => { attributes.push(e.getAttribute(attributeName)); }); return attributes;")
	List<String> listAttributes(String selector, String attributeName);

	@JsFunction("")
	void dummy();

	@JsFunction("console.error(message);")
	void consoleError(String message);

	@JsFunction("return a + b")
	int sum(int a, int b);

	@JsFunction("return str1 + str2")
	String concat(String str1, String str2);

	@JsFunction("let list = []; numbers.forEach(i => list.push(i + inc)); return list;")
	List<Double> increment(List<Integer> numbers, int inc);
}
