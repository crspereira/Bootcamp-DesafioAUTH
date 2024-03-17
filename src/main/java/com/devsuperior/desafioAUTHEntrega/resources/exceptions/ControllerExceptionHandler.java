package com.devsuperior.desafioAUTHEntrega.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.desafioAUTHEntrega.services.exceptions.ServicesDataBaseException;
import com.devsuperior.desafioAUTHEntrega.services.exceptions.ServicesNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(ServicesNotFoundException.class)
	public ResponseEntity<StandardErrorResponse> entityNotFound(ServicesNotFoundException e, HttpServletRequest request) {
		StandardErrorResponse error = new StandardErrorResponse();
		HttpStatus errorStatus = HttpStatus.NOT_FOUND;
		
		error.setTimestamp(Instant.now());
		error.setStatus(errorStatus.value());
		error.setError(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(errorStatus).body(error);
	}
	
	@ExceptionHandler(ServicesDataBaseException.class)
	public ResponseEntity<StandardErrorResponse> dataBaseIntegrity(ServicesDataBaseException e, HttpServletRequest request) {
		StandardErrorResponse error = new StandardErrorResponse();
		HttpStatus errorStatus = HttpStatus.BAD_REQUEST;
		
		error.setTimestamp(Instant.now());
		error.setStatus(errorStatus.value());
		error.setError(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(errorStatus).body(error);
	}

}
