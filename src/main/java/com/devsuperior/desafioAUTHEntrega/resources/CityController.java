package com.devsuperior.desafioAUTHEntrega.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.desafioAUTHEntrega.dto.CityDTO;
import com.devsuperior.desafioAUTHEntrega.services.CityService;

@RestController
@RequestMapping(value = "/cities")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping
	public ResponseEntity<List<CityDTO>> findAll() {
		List<CityDTO> citiesDto = cityService.findAll();
		return ResponseEntity.ok().body(citiesDto);
	}
	
	@PostMapping
	public ResponseEntity<CityDTO> insert(@RequestBody CityDTO cityDto) {
		cityDto = cityService.insert(cityDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(cityDto.getId()).toUri();
		return ResponseEntity.created(uri).body(cityDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		cityService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
