package epicode.JAVASPRINGPROGETTO.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import epicode.JAVASPRINGPROGETTO.entities.Dispositivo;
import epicode.JAVASPRINGPROGETTO.entities.StatoDispositivo;
import epicode.JAVASPRINGPROGETTO.entities.User;
import epicode.JAVASPRINGPROGETTO.entities.payloads.UserRegistrationPayload;
import epicode.JAVASPRINGPROGETTO.exceptions.BadRequestException;
import epicode.JAVASPRINGPROGETTO.exceptions.NotFoundException;
import epicode.JAVASPRINGPROGETTO.repositories.DispositiviRepository;
import epicode.JAVASPRINGPROGETTO.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	UsersRepository usersRepo;
	@Autowired
	DispositiviRepository disopsitiviRepo;

	public User create(UserRegistrationPayload u) {
		usersRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " already in use!");
		});
		List<Dispositivo> dispositivi = new ArrayList<>();
		User newUser = new User(u.getUsername(), u.getName(), u.getSurname(), u.getEmail(), u.getPassword(),
				dispositivi);
		return usersRepo.save(newUser);
	}

	public Page<User> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return usersRepo.findAll(pageable);
	}

	public User findById(UUID id) throws NotFoundException {
		return usersRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato!"));
	}

	public User findByIdAndUpdate(UUID id, UserRegistrationPayload u) throws NotFoundException {
		User found = this.findById(id);

		found.setId(id);
		found.setUsername(u.getUsername());
		found.setName(u.getName());
		found.setSurname(u.getSurname());
		found.setEmail(u.getEmail());
		found.setPassword(u.getPassword());
		found.setDispositivi(found.getDispositivi());

		return usersRepo.save(found);
	}

	public User findByUsername(String username) throws NotFoundException {
		return usersRepo.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("nessun utante avente questo username"));
	}

	public List<Dispositivo> findDispositiviPerUtente(UUID id) {
		User found = this.findById(id);
		return found.getDispositivi();
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		List<Dispositivo> dispositivi = found.getDispositivi();
		Iterator<Dispositivo> i = dispositivi.iterator();
		while (i.hasNext()) {
			Dispositivo current = i.next();
			current.setStato(StatoDispositivo.DISPONIBILE);
			current.setUser(null);
		}

		usersRepo.delete(found);
	}
}
