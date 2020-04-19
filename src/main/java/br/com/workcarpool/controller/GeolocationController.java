package br.com.workcarpool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeolocationController {

	@GetMapping("/geolocation/startsearch")
	public String startSearch() {
		return "geolocation/search";
	}
}
