package epicode.JAVASPRINGPROGETTO.auth.payloads;

import lombok.Getter;

@Getter
public class UserLoginPayload {
	String username;
	String password;
}
