package com.devsuperior.desafioAUTHEntrega.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.desafioAUTHEntrega.entities.User;
import com.devsuperior.desafioAUTHEntrega.repositories.UserRepository;
import com.devsuperior.desafioAUTHEntrega.services.exceptions.ForbiddenException;
import com.devsuperior.desafioAUTHEntrega.services.exceptions.UnauthorizedException;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	//pega usuario autenticado e logado
	@Transactional(readOnly = true) //evita lock de banco
	public User authenticatedUser() {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepository.findByEmail(userName);
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid User!");
		}
	}
	
	//verifica se usuario logado eh admin ou nao
	public void validateSelfOrAdmin(Long userId) {
		User user = authenticatedUser();
		if(!user.getId().equals(userId) && !user.hasHole("ROLE_ADMIN")) {
			throw new ForbiddenException("Access Denied!");
		}
		
	}

}
