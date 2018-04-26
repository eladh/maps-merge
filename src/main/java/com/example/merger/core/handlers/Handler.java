package com.example.merger.core.handlers;

public interface Handler<T> {
	T handle(T lElement , T rElement);
}