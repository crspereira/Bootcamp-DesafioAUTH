package com.devsuperior.desafioAUTHEntrega.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.desafioAUTHEntrega.dto.UserDTO;
import com.devsuperior.desafioAUTHEntrega.entities.User;
import com.devsuperior.desafioAUTHEntrega.repositories.UserRepository;
import com.devsuperior.desafioAUTHEntrega.services.exceptions.ServicesNotFoundException;

@Service
public class UserService implements UserDetailsService { //UserDetailsService interface para SpringSecuitiry Autentication

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	//dependecias
	@Autowired
	private UserRepository repository;
	@Autowired
	private AuthService authService;
	
	//endpoints
	//anotation que evita lock no Banco
	@Transactional(readOnly = true)
	/*anotation que substitu as configs estaticas na Classe ResourdeServerConfig.
	 a anotation @EnableGlobalMethodSecurity(prePostEnabled = true) precisa estar na classe WebSecurityConfig */
	@PreAuthorize(value = "hasAnyRole('ADMIN')")
	public UserDTO findById(Long id) {
		//verifica usuario logado
		authService.validateSelfOrAdmin(id);
		//
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ServicesNotFoundException("Entity not Found"));
		return new UserDTO(entity);
	}
	
	//metodo do UserDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error("User Not Found: " + username);
			throw new UsernameNotFoundException("Email Not Found!");
		}
		logger.info("User Found: " + username);
		return user;
	}
}