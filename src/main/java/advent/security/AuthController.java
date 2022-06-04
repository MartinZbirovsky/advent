package advent.security;

import javax.validation.Valid;

import advent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
	@Autowired AuthenticationManager authManager;
	@Autowired
	JwtTokenUtil jwtUtil;
	
	@PostMapping("/auth/login")
	public ResponseEntity login(@RequestBody @Valid AuthRequest request) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
			User user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			
			return ResponseEntity.ok().body(new AuthResponse(user.getEmail(), accessToken));
			
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/auth/logout")
	public void logout() throws Exception {

	}
}
