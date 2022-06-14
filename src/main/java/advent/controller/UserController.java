package advent.controller;

import advent.dto.Mapper;
import advent.dto.requestDto.UserDetailDto;
import advent.dto.responseDto.UserCreateDto;
import advent.model.User;
import advent.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.configuration.Constants.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userServiceImpl;
	private final Mapper mapper;

	@GetMapping("/getAll")
	public Page<User> getUsers(
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = PAGE_NUMBER) int pageNo,
			@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = "id") String sortBy
	){ return userServiceImpl.getUsers(email, pageNo, pageSize, sortBy); }

	@GetMapping("/get/{id}")
	public UserDetailDto getUserById(@PathVariable Long id){
		return mapper.userToDetailDto(userServiceImpl.getById(id));
	}

	@PostMapping("addUser")
	public UserCreateDto createUser(@RequestBody @Valid User user) {
		/*User createdUser = userService.save(user);
		URI uri = URI.create("/users/" + createdUser.getId());
		UserDTO userDto = new UserDTO(createdUser.getId(), createdUser.getEmail());
		return ResponseEntity.created(uri).body(userDto);*/

		return mapper.userToCreateDto(userServiceImpl.addNew(user));
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		return "User " + userServiceImpl.deleteById(id).getEmail() + " is removed";
	}

	@PutMapping("/edit/{id}")
    private UserDetailDto editAuthor(@PathVariable final Long id, @RequestBody final UserDetailDto userRequestDto) {
        return mapper.userToDetailDto(userServiceImpl.editById(id, userRequestDto));
    }

	@PostMapping("/addRole/{roleId}/toUser/{userId}")
	public User addBenefit(@PathVariable Long roleId, @PathVariable Long userId) {
		return userServiceImpl.addRole(roleId, userId);
	}

	@PostMapping("/removeRole/{roleId}/toUser/{userId}")
	public User removeBenefit(@PathVariable Long roleId, @PathVariable Long userId) {
		return userServiceImpl.removeRole(roleId, userId);
	}
}
