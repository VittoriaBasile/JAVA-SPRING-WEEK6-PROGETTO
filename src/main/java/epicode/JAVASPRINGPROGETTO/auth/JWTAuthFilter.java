package epicode.JAVASPRINGPROGETTO.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import epicode.JAVASPRINGPROGETTO.entities.User;
import epicode.JAVASPRINGPROGETTO.exceptions.NotFoundException;
import epicode.JAVASPRINGPROGETTO.exceptions.UnauthorizedException;
import epicode.JAVASPRINGPROGETTO.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	UsersService usersService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore aggiungi il token all'authorization header");

		String accessToken = authHeader.substring(7);

		JWTTools.isTokenValid(accessToken);

		String username = JWTTools.extractSubject(accessToken);
		try {
			User user = usersService.findByUsername(username);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);

			filterChain.doFilter(request, response);
		} catch (NotFoundException e) {
			throw new NotFoundException("richiesta non valida");
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}
