package advent.service;

import advent.dto.Mapper;
import advent.dto.requestDto.UserDetailDto;
import advent.model.Role;
import advent.model.User;
import advent.repository.RoleRepository;
import advent.repository.UserRepository;
import advent.service.serviceinterface.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		log.info("loadUserByUsername");
		if(user == null) {
			log.error("User not found");
			throw new UsernameNotFoundException("no user");
		}else{
			log.error("User found");
		}

		List<SimpleGrantedAuthority> autorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			autorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User (user.getUsername(), user.getPassword(), autorities );
	}
	@Override
	public User saveUser(User user) {
		log.info("save user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("save role {}", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUse(String username, String rolename) {
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(rolename);
		user.getRoles().add(role);
	}

	@Override
	public User getUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

}


/*
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Mapper mapper;
	private final RoleServiceImpl roleService;

	@Transactional
	public User addNew(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		return userRepository.save(user);
	}
	public Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return email.isEmpty()
				? userRepository.findAll(paging)
				: userRepository.findByEmailContaining(email, paging);
	}
	public User getById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User" + userId + "not found"));
	}

	public User findByEmail(String email){
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));
	}

	@Transactional
	public User deleteById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User" + userId + " not found"));
		userRepository.deleteById(user.getId());
		return user;
	}

	@Transactional
	public User editById(Long userId, UserDetailDto entityBody) {
	/*	return  userRepository.findById(entityId)
				.map(user -> {
					user.setEmail(entityBody.getEmail());
					user.setAddress(entityBody.getAddress());
					return userRepository.save(user);
				})
				.orElseThrow(() -> new EntityNotFoundException("User detail not found"));*/
	/*	User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Advertisement" + userId + "not found"));

		user.setEmail(entityBody.getEmail());
		user.getFirstAddress().setCity(entityBody.getAddress().getCity());
		user.getFirstAddress().setStreet(entityBody.getAddress().getStreet());
		return userRepository.save(user);
	}

	@Transactional
	public User addRole(Long roleId, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Advertisement" + userId + "not found"));
		Role role = roleService.get(roleId);
		user.addRole(role);
		return userRepository.save(user);
	}

	@Transactional
	public User removeRole(Long roleId, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Advertisement" + userId + "not found"));
		Role role = roleService.get(roleId);
		user.removeRole(role);
		return userRepository.save(user);
	}
}*/
