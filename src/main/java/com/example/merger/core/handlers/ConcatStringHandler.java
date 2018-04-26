package com.example.merger.core.handlers;

public class ConcatStringHandler implements Handler<String> {

	@Override
	public String handle(String lElement, String rElement) {
		return lElement.concat(rElement);
	}
}