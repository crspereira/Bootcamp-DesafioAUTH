package com.devsuperior.desafioAUTHEntrega.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardErrorResponse {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();

	public List<FieldMessage> getErros() {
		return erros;
	}
	
	public void addError(String fieldName, String message) {
		erros.add(new FieldMessage(fieldName, message));
	}
	
	
}
