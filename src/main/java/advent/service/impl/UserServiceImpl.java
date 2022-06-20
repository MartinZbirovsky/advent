package advent.service.impl;

import advent.dto.Mapper;
import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.Payment;
import advent.model.Role;
import advent.model.User;
import advent.repository.UserRepository;
import advent.service.intf.RoleService;
import advent.service.intf.UserService;
import lombok.EqualsAndHashCode;
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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("loadUserByUsername");
		User user = userRepo.findByEmail(email).orElseThrow(() -> {
			log.error("User not found");
			return new UsernameNotFoundException("no user");
		});
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User (user.getEmail(), user.getPassword(), authorities );
	}
	@Override
	public User saveUser(User user) {
		log.info("save user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User editUser(User user) {
		User userToUpdate = userRepo.findByEmail(user.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setFirstAddress(user.getFirstAddress());
		userToUpdate.setSecondAddress(user.getSecondAddress());

		return userRepo.save(userToUpdate);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("save role {}", role.getName());
		return roleService.save(role);
	}

	@Override
	public RoleUserResDto addRoleToUse(String userEmail, String roleName) {
		User user = userRepo.findByEmail(userEmail)
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));
		Role role = roleService.findByName(roleName);

		if(role == null)
			throw new EntityNotFoundException("Role " + roleName + " not found");
		user.getRoles().add(role);

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role added.");
	}

	@Override
	public RoleUserResDto removeRole(RoleUserDto form) {
		User user = userRepo.findByEmail(form.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Email not found " + form.getEmail()));

		Role role = roleService.findByName(form.getRoleName());
		if(role == null)
			throw new EntityNotFoundException("Role  " + form.getRoleName() + " not found");

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role removed.");
	}

	@Override
	public BigDecimal chargeMoney(String email, Payment payment) {
		User updateUser = userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not found"));
		updateUser.getPayments().add(payment);
		updateUser.setCurrentMoney(updateUser.getCurrentMoney().add(payment.getAmount()));
		userRepo.save(updateUser);

		return updateUser.getCurrentMoney();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not found"));
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
		User user = userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not found"));
		userRepo.delete(user);
		return user;
	}
}