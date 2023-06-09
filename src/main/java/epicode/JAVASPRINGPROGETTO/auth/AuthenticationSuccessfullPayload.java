package epicode.JAVASPRINGPROGETTO.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationSuccessfullPayload {
	private String accessToken;
}
