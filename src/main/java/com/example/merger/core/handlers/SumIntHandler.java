package com.example.merger.core.handlers;


public class SumIntHandler implements Handler<Integer> {

	@Override
	public Integer handle(Integer lElement, Integer rElement) {
		return lElement + rElement;
	}
}