package advent.controller;

import advent.dto.mapper.Mapper;
import advent.dto.responseDto.UserDtoRes;
import advent.model.User;
import advent.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceImpl userServiceImpl;
	private final Mapper mapper;

	UserController(UserServiceImpl userServiceImpl, Mapper mapper){
		this.userServiceImpl = userServiceImpl;
		this.mapper = mapper;
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id){
		return userServiceImpl.getById(id);
	}

	@GetMapping("")
	public List<User> getUsers(){ return userServiceImpl.getUsers(); }

	@PostMapping("")
	public UserDtoRes createUser(@RequestBody @Valid User user) {
		/*User createdUser = userService.save(user);
		URI uri = URI.create("/users/" + createdUser.getId());
		UserDTO userDto = new UserDTO(createdUser.getId(), createdUser.getEmail());
		return ResponseEntity.created(uri).body(userDto);*/

		return mapper.UserToDto(userServiceImpl.save(user));
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable int id) {
		return "User " + userServiceImpl.deleteById(id).getEmail() + " is deleted";
	}
}
