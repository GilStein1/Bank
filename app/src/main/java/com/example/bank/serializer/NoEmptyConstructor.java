package com.example.bank.serializer;

public class NoEmptyConstructor extends RuntimeException {
	public NoEmptyConstructor(Class<?> classWithNoEmptyConstructor) {
		super("class " + classWithNoEmptyConstructor.getName() + " has no empty constructor");
	}
}
