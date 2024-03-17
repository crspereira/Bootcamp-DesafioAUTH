package com.devsuperior.desafioAUTHEntrega.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.desafioAUTHEntrega.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	//query method
	User findByEmail(String name);

}
