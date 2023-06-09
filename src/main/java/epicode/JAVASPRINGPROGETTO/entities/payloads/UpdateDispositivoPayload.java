package epicode.JAVASPRINGPROGETTO.entities.payloads;

import java.util.UUID;

import epicode.JAVASPRINGPROGETTO.entities.StatoDispositivo;
import epicode.JAVASPRINGPROGETTO.entities.TipoDispositivo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateDispositivoPayload {
	@NotNull(message = "Il tipo di dispositivo è obbligatorio")
	TipoDispositivo tipo;
	@NotNull(message = "Lo stato del dispositivo è obbligatorio")
	StatoDispositivo stato;
	@NotNull(message = "L? id dell' utente è obbligatorio")
	UUID idUser;
}
