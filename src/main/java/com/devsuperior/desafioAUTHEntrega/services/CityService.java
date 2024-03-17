package com.devsuperior.desafioAUTHEntrega.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.desafioAUTHEntrega.dto.CityDTO;
import com.devsuperior.desafioAUTHEntrega.entities.City;
import com.devsuperior.desafioAUTHEntrega.repositories.CityRepository;
import com.devsuperior.desafioAUTHEntrega.services.exceptions.ServicesDataBaseException;
import com.devsuperior.desafioAUTHEntrega.services.exceptions.ServicesNotFoundException;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> cities = cityRepository.findAll(Sort.by("name"));
		return cities.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO cityDto) {
		City city = new City();
		city.setName(cityDto.getName());
		cityRepository.save(city);
		return new CityDTO(city);
	}
	
	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ServicesNotFoundException("ID ["+ id +"] Not Found!");
		} catch (DataIntegrityViolationException e) {
			throw new ServicesDataBaseException("DataBase Integrity Violation!");
		}
	}

}
