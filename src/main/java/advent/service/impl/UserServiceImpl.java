package advent.service.impl;

import advent.dto.Mapper;
import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.dto.responseDto.UserListResDto;
import advent.model.Payment;
import advent.model.Role;
import advent.model.User;
import advent.repository.UserRepository;
import advent.service.intf.RoleService;
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
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements /*UserService,*/ UserDetailsService {
	private final UserRepository userRepo;
	private final RoleService roleService;
	private final Mapper mapper;

	/**
	 * Disable user/ban
	 * @param userId - User id
	 * @return - Message say who ben banned
	 */
	public User banUser(Long userId) {
		return userBanUnban(userId, true);
	}

	/**
	 * Disable user/ban
	 * @param userId - User id
	 * @return - Message say who ben unbanned
	 */
	public User unbanUser(Long userId) {
		return userBanUnban(userId, false);
	}

	public User saveNewUser(User user) {
		return userRepo.save(user);
	}

	public void enableUser(String email){
		userRepo.enableUser(email);
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email).
				orElseThrow(() -> new UsernameNotFoundException("user not found"));

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

		return new org.springframework.security.core.userdetails.User (user.getEmail(), user.getPassword(), authorities );
	}

	public User editUser(User user) {
		User userToUpdate = userRepo.findByEmail(user.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));

		userToUpdate.setEmail(user.getEmail());
		userToUpdate.getFirstAddress().setStreet(user.getFirstAddress().getStreet());
		userToUpdate.getFirstAddress().setCity(user.getFirstAddress().getCity());
		userToUpdate.getSecondAddress().setStreet(user.getSecondAddress().getStreet());
		userToUpdate.getSecondAddress().setCity(user.getSecondAddress().getCity());

		return userRepo.save(userToUpdate);
	}

	public Role saveRole(Role role) {
		log.info("save role {}", role.getName());
		return roleService.save(role);
	}

	public RoleUserResDto addRoleToUse(String userEmail, String roleName) {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Email not found"));
		Role role = roleService.findByName(roleName);
		user.getRoles().add(role);

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role added.");
	}

	public RoleUserResDto removeRole(RoleUserDto form) {
		User user = userRepo.findByEmail(form.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Email not found " + form.getEmail()));

		Role role = roleService.findByName(form.getRoleName());
		user.getRoles().remove(role);

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role removed.");
	}

	/**
	 * Charge money. Send user email and payment body to increase account money balance.
	 * @param email - User email
	 * @param payment - Payment
	 * @return Actual money balance
	 */
	public BigDecimal chargeMoney(String email, Payment payment) {
		User updateUser = userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not found"));
		updateUser.getPayments().add(payment);
		updateUser.setCurrentMoney(updateUser.getCurrentMoney().add(payment.getAmount()));
		userRepo.save(updateUser);

		return updateUser.getCurrentMoney();
	}

	/**
	 * Find user by his email else throw not found
	 * @param email - User email
	 * @return User
	 */
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));
	}

	public Page<UserListResDto> getUsers(String email, int pageNo, int pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<User> users;
		if(email.isEmpty()){
			users = userRepo.findAll(paging);
		} else {
			users = userRepo.findByEmailContaining(email, paging);
		}
		return users.map(user -> mapper.userToUserListResDto(user));
	}

	public User deleteUserByEmail(Long id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));
		userRepo.delete(user);
		return user;
	}

	public User findById(Long id) {
		return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
	}

	/**
	 * Ban or unban user with given ID true for ban / false for unban
	 * @param userId - User ID
	 * @param aBanned - Ban status
	 * @return User name
	 */
	private User userBanUnban (Long userId, boolean aBanned) {
		User userToDisable= userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		userToDisable.setLocked(aBanned);
		userRepo.save(userToDisable);
		return userRepo.save(userToDisable);
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}