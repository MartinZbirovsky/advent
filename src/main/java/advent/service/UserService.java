package advent.service;

import javax.transaction.Transactional;

import advent.model.User;
import advent.repository.AddressRepository;
import advent.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AddressRepository addressRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
	}

	public User save(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);

		return userRepository.save(user);
	}
	public List<User> getUsers(){ return userRepository.findAll();}
	public User getById(int id) { return userRepository.findById(id).orElse(null);}
}
