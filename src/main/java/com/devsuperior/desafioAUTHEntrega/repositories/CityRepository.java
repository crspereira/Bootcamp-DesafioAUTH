package com.devsuperior.desafioAUTHEntrega.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.desafioAUTHEntrega.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
