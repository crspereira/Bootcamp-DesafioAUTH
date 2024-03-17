package com.devsuperior.desafioAUTHEntrega.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.desafioAUTHEntrega.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
