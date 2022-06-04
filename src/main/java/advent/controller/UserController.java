package advent.controller;

import advent.model.User;
import advent.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	UserController(UserService userService){
		this.userService = userService;
	}

	@PostMapping("")
	public String createUser(@RequestBody @Valid User user) {
		/*User createdUser = userService.save(user);
		URI uri = URI.create("/users/" + createdUser.getId());

		UserDTO userDto = new UserDTO(createdUser.getId(), createdUser.getEmail());

		return ResponseEntity.created(uri).body(userDto);*/

		return userService.save(user).getUsername();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id){
		return userService.getById(id);
	}

	@GetMapping("")
	public List<User> getUsers(){ return userService.getUsers(); }
}
