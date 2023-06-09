package epicode.JAVASPRINGPROGETTO.entities.payloads;

import epicode.JAVASPRINGPROGETTO.entities.TipoDispositivo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NewDispositivoPayload {
	@NotNull(message = "Il tipo di dispositivo è obbligatorio")
	TipoDispositivo tipo;

}
