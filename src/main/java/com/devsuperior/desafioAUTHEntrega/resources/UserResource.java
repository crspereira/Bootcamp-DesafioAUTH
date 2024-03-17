package com.devsuperior.desafioAUTHEntrega.resources;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.desafioAUTHEntrega.dto.UserDTO;
import com.devsuperior.desafioAUTHEntrega.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id){
		UserDTO userDto = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

}
