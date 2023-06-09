package epicode.JAVASPRINGPROGETTO.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import epicode.JAVASPRINGPROGETTO.entities.Dispositivo;
import epicode.JAVASPRINGPROGETTO.entities.User;
import epicode.JAVASPRINGPROGETTO.entities.payloads.NewDispositivoPayload;
import epicode.JAVASPRINGPROGETTO.entities.payloads.UpdateDispositivoPayload;
import epicode.JAVASPRINGPROGETTO.exceptions.NotFoundException;
import epicode.JAVASPRINGPROGETTO.repositories.DispositiviRepository;

@Service
public class DispositiviService {
	@Autowired
	DispositiviRepository dispositiviRepo;
	@Autowired
	UsersService usersService;

	public Dispositivo create(NewDispositivoPayload d) {
		Dispositivo newDispositivo = new Dispositivo(d.getTipo());
		return dispositiviRepo.save(newDispositivo);
	}

	public Page<Dispositivo> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return dispositiviRepo.findAll(pageable);
	}

	public Dispositivo findById(UUID id) throws NotFoundException {
		return dispositiviRepo.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo non trovato!"));
	}

	public Dispositivo findByIdAndUpdate(UUID id, UpdateDispositivoPayload d) throws NotFoundException {
		Dispositivo found = this.findById(id);
		User foundUser = usersService.findById(d.getIdUser());
		found.setId(id);
		found.setTipo(d.getTipo());
		found.setStato(d.getStato());
		found.setUser(foundUser);

		return dispositiviRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Dispositivo found = this.findById(id);
		dispositiviRepo.delete(found);
	}

}
