package advent.service;

import javax.transaction.Transactional;

import advent.model.User;
import advent.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User save(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);

		return userRepository.save(user);
	}
	public List<User> getUsers(){ return userRepository.findAll();}
	public User getById(int id) { return userRepository.findById(id).orElse(null);}

	public User findByEmail(String email){
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));
	}
}
