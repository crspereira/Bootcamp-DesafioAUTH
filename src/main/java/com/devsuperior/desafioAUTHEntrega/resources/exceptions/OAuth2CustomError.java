package com.devsuperior.desafioAUTHEntrega.resources.exceptions;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuth2CustomError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//variaveis de instância
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    
    //construtor
    public OAuth2CustomError() {
    }

    public OAuth2CustomError(String error, String errorDescription) {
		this.error = error;
		this.errorDescription = errorDescription;
	}

	//getters e setters
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
