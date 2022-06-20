package advent.controller;

import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.Payment;
import advent.model.Role;
import advent.model.User;
import advent.service.intf.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static advent.cons.GeneralCons.PAGE_NUMBER;
import static advent.cons.GeneralCons.PAGE_SIZE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity<Page<User>> getUsers(@RequestParam(defaultValue = "") String email,
											   @RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
											   @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
											   @RequestParam(defaultValue = "id") String sortBy){
		return ResponseEntity.ok().body(userService.getUsers(email, pageNo, pageSize, sortBy));
	}

	@PostMapping("/users/save")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}

	@PutMapping("/users/edit")
	public User editUser(@Valid @RequestBody User user){
		return userService.editUser(user);
	}

	@PostMapping("/users/role")
	public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping("/role/add")
	public RoleUserResDto addRoleToUser(@Valid @RequestBody RoleUserDto form){
		return userService.addRoleToUse(form.getEmail(), form.getRoleName());
	}
	@PostMapping("role/remove")
	public RoleUserResDto removeRole(@Valid @RequestBody RoleUserDto form){
		return userService.removeRole(form);
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);

		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.getUserByEmail(username);
				String access_token = JWT.create()
						.withSubject(user.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);

				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception exception) {
				log.error("Error " + exception.getMessage());
				response.setHeader("error" , exception.getMessage());
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());

				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Refresh token missing");
		}
	}

	@GetMapping("/users/logout")
	public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect("/api/ads");
	}
	@DeleteMapping("/users/{email}")
	public User deleteUserByEmail(@PathVariable String email) {
		return userService.deleteUserByEmail(email);
	}

	@PostMapping("/users/chargemoney")
	public BigDecimal chargeMoney(@RequestBody Payment charge, Principal principal){
		return userService.chargeMoney(/*principal.getName() */"5neco@neco.cz", charge);
	}
}
