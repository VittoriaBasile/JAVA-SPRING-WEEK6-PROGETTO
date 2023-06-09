package epicode.JAVASPRINGPROGETTO.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epicode.JAVASPRINGPROGETTO.entities.Dispositivo;
import epicode.JAVASPRINGPROGETTO.entities.payloads.NewDispositivoPayload;
import epicode.JAVASPRINGPROGETTO.entities.payloads.UpdateDispositivoPayload;
import epicode.JAVASPRINGPROGETTO.exceptions.NotFoundException;
import epicode.JAVASPRINGPROGETTO.services.DispositiviService;

@RestController
@RequestMapping("/dispositivi")
public class DispositiviController {
	@Autowired
	private DispositiviService dispositiviService;

	@GetMapping("")
	public Page<Dispositivo> getDispositivi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return dispositiviService.find(page, size, sortBy);
	}

	@GetMapping("/{dispositivoId}")
	public Dispositivo getById(@PathVariable UUID dispositivoId) {
		return dispositiviService.findById(dispositivoId);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Dispositivo create(@RequestBody @Validated NewDispositivoPayload dispositivo) {
		return dispositiviService.create(dispositivo);
	}

	@PutMapping("/{dispositivoId}")
	public Dispositivo updateUser(@PathVariable UUID dispositivoId, @RequestBody UpdateDispositivoPayload body)
			throws Exception {
		return dispositiviService.findByIdAndUpdate(dispositivoId, body);
	}

	@DeleteMapping("/{dispositivoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispositivo(@PathVariable UUID dispositivoId) throws NotFoundException {
		dispositiviService.findByIdAndDelete(dispositivoId);
	}
}
