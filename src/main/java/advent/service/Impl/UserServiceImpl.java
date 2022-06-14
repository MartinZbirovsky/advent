package advent.service.Impl;

import advent.dto.Mapper;
import advent.dto.requestDto.UserDetailDto;
import advent.model.Role;
import advent.model.User;
import advent.repository.RoleRepository;
import advent.repository.UserRepository;
import advent.service.Interface.RoleService;
import advent.service.Interface.UserService;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepo;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final Mapper mapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
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
		return roleService.save(role);
	}

	@Override
	public void addRoleToUse(String userName, String roleName) {
		User user = userRepo.findByEmail(userName);
		Role role = roleService.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
				//.orElseThrow(() -> new EntityNotFoundException("User" + email + "not found"));
	}

	@Override
	public Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return email.isEmpty()
				? userRepo.findAll(paging)
				: userRepo.findByEmailContaining(email, paging);
	}

	public User getById(Long userId) {
		return userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User" + userId + "not found"));
	}

	public User deleteById(String email) {
		User user = userRepo.findByEmail(email);
		if(user == null)
			new EntityNotFoundException("Email" + email + " not found");

		userRepo.deleteById(user.getId());
		return user;
	}

	public User editById(Long userId, UserDetailDto entityBody) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User" + userId + "not found"));

		user.setEmail(entityBody.getEmail());
		user.getFirstAddress().setCity(entityBody.getAddress().getCity());
		user.getFirstAddress().setStreet(entityBody.getAddress().getStreet());
		return userRepo.save(user);
	}

	public User removeRole(String roleName, Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User" + userId + "not found"));
		Role role = roleService.findByName(roleName);
				//.orElseThrow(() -> new EntityNotFoundException("Role" + userId + "not found"));;
		user.getRoles().remove(role);
		return userRepo.save(user);
	}
}
