package advent.service.Impl;

import advent.dto.Mapper;
import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.Role;
import advent.model.User;
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
import java.math.BigDecimal;
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
		return new org.springframework.security.core.userdetails.User (/*user.getUsername()*/ user.getEmail(), user.getPassword(), autorities );
	}
	@Override
	public User saveUser(User user) {
		log.info("save user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User editUser(User user) {
		User userToUpdate = userRepo.findByEmail(user.getEmail());

		if(userToUpdate == null) {
			throw new EntityNotFoundException("Email not found");
		} else {
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setFirstAddress(user.getFirstAddress());
			userToUpdate.setSecondAddress(user.getSecondAddress());
			userToUpdate.setCompanyName(user.getCompanyName());
		}
		return userRepo.save(userToUpdate);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("save role {}", role.getName());
		return roleService.save(role);
	}

	@Override
	public RoleUserResDto addRoleToUse(String userEmail, String roleName) {
		User user = userRepo.findByEmail(userEmail);
		Role role = roleService.findByName(roleName);

		if(user == null)
			throw new EntityNotFoundException("Email " + userEmail + " not found");

		if(role == null)
			throw new EntityNotFoundException("Role " + roleName + " not found");
		user.getRoles().add(role);

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role added.");
	}

	@Override
	public RoleUserResDto removeRole(RoleUserDto form) {
		User user = userRepo.findByEmail(form.getEmail());
		if(user == null)
			throw new EntityNotFoundException("Email " + form.getEmail() + " not found");

		Role role = roleService.findByName(form.getRolename());
		if(role == null)
			throw new EntityNotFoundException("Role  " + form.getRolename() + " not found");

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role removed.");
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return email.isEmpty()
				? userRepo.findAll(paging)
				: userRepo.findByEmailContaining(email, paging);
	}

	@Override
	public User deleteUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		if(user == null)
			throw new EntityNotFoundException("Email" + email + " not found");

		userRepo.delete(user);
		return user;
	}


}
