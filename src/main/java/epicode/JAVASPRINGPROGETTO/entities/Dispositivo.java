package epicode.JAVASPRINGPROGETTO.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "dispositivi")
public class Dispositivo {
	@Id
	@GeneratedValue
	private UUID id;
	@Enumerated(EnumType.STRING)
	private TipoDispositivo tipo;
	@Enumerated(EnumType.STRING)
	private StatoDispositivo stato;
	@ManyToOne
	@JsonBackReference
	private User user;

	public Dispositivo(TipoDispositivo tipo) {
		super();
		this.tipo = tipo;
		this.stato = StatoDispositivo.DISPONIBILE;
	}

	public Dispositivo(TipoDispositivo tipo, StatoDispositivo stato, User user) {
		super();
		this.tipo = tipo;
		this.stato = stato;
		this.user = user;
	}

}
