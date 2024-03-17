package com.devsuperior.desafioAUTHEntrega.services.exceptions;

public class ServicesDataBaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//construtor passando argumento para construtor da super classe
	public ServicesDataBaseException(String msg) {
		super(msg);
	}
}
