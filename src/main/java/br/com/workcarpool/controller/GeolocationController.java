package br.com.workcarpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.workcarpool.model.Worker;
import br.com.workcarpool.repository.WorkerRepository;

@Controller
public class GeolocationController {

	@Autowired
	private WorkerRepository repository;
	
	@GetMapping("/geolocation/startsearch")
	public String startSearch(Model model) {
		List<Worker> workers = repository.findAll();
		model.addAttribute("workers", workers);
		return "geolocation/search";
	}
	
	@GetMapping("/geolocation/search")
	public String search(@RequestParam("workerId") String workerId, Model model) {
	    Worker worker = repository.findById(workerId);
	    model.addAttribute("nearbyWorkers", repository.searchByGeoLocation(worker));
	    return "geolocalizacao/pesquisar";
	}
}
