package com.devsuperior.desafioAUTHEntrega.services.exceptions;

public class ServicesNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//construtor passando argumento para construtor da super classe
	public ServicesNotFoundException(String msg) {
		super(msg);
	}
}
