package br.com.workcarpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.workcarpool.model.Worker;
import br.com.workcarpool.repository.WokerRepository;
import br.com.workcarpool.service.GeolocationService;

@Controller
@RequestMapping("/worker")
public class WorkerController {
	
	@Autowired
	private WokerRepository repository;
	
	@Autowired
	private GeolocationService geoService;
	
	

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("worker", new Worker());
		return "worker/register";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Worker worker) {
		try {
			List<Double> latlng = geoService.getLatLongByAdress(worker.getHomeAddress());
			worker.getHomeAddress().setCoordinates(latlng);
			repository.save(worker);
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("workers", repository.findAll());
		return "worker/list";
	}
	
	@GetMapping("/searchbyname")
	public String searchByName() {
		return "worker/searchbyname";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("name") String name, Model model) {
		List<Worker> workers = repository.findByName(name);
		model.addAttribute("workers", workers);
		return "worker/searchbyname";
	}
}
