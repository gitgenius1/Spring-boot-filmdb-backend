package com.example.demo.controllers;

import java.util.ArrayList;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Film;
import com.example.demo.models.FilmDAO;

@RestController
@RequestMapping(value = "luis_filmdb/filmdb")
@CrossOrigin
public class FilmDB {

	FilmDAO filmdao = new FilmDAO();
	
	Film film = new Film();
	
	// ==========================================================================

	// Find film by id @PathVariable not key:value 
	
	@RequestMapping(value = "/film/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Film retrieveFilm(@PathVariable("id") int id) {

		try {
			
			film = filmdao.getFilmByID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return film;
	}

	// ============================================================================
	
	// Search for title pass key:value pair.

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ArrayList<Film> searchAll(@RequestParam("title") String str) {
		
		return filmdao.searchFilm(str); 
	}

	// ============================================================================

	// Return all films in d.b. 
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ArrayList<Film> allFilms() {

		return filmdao.getAllFilms();
	}

	// ============================================================================

	// delete film from d.b. key:value pair
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String deleteFilm(@RequestParam("id") int id) {

		return filmdao.deleteFilm(id);
	}

	// =============================================================================
	
	// update film from d.b. pass data in the body

	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String updateFilm(@RequestBody Film films) {
		
		return filmdao.updateFilm(films);
	}

	// =============================================================================

	// add film from d.b. pass data in the body

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String insertFilm(@RequestBody Film films) {
		
		return filmdao.insertFilm(films);
	}

}