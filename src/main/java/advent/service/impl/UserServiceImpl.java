package advent.service.impl;

import advent.dto.requestDto.RegistrationReqDto;
import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.ConfirmationToken;
import advent.model.Payment;
import advent.model.Role;
import advent.model.User;
import advent.repository.UserRepository;
import advent.service.intf.RoleService;
import advent.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements /*UserService,*/ UserDetailsService {

	private final UserRepository userRepo;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenServiceImpl confirmationTokenServiceImpl;
	private final JavaMailSender mailSender;
	private final Validator validator;
	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	public String register(RegistrationReqDto request) {

		if (!validator.email(request.getEmail())) throw new IllegalStateException("Email not valid");
		if (!validator.firstName(request.getFirstName())) throw new IllegalStateException("First name not valid");
		if (!validator.secondName(request.getFirstName())) throw new IllegalStateException("Second name not valid");

		String token = signUpUser(new User(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword()
		));
       /* String link = "http://localhost:8080/api/users/registration/confirm?token=" + token;
        send(request.getEmail(), buildEmail(request.getFirstName(), link));*/

		return token;
	}

	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenServiceImpl
				.getToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmedAt() != null)
			throw new IllegalStateException("email already confirmed");

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();
		if (expiredAt.isBefore(LocalDateTime.now()))
			throw new IllegalStateException("token expired");

		confirmationTokenServiceImpl.setConfirmedAt(token);
		userRepo.enableUser(confirmationToken.getAppUser().getEmail());
		return "confirmed";
	}

	/**
	 * Create new user in registration.
	 * @param user
	 * @return
	 */
	private String signUpUser(User user) {
		boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent();
		if (userExists) {
			// TODO check of attributes are the same and
			// TODO if email not confirmed send confirmation email.
			throw new IllegalStateException("Email already taken");
		}

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);

		Role role = roleService.findByName("ROLE_ADMIN")
				.orElse(roleService.addNew(new Role("ROLE_ADMIN")));
		user.getRoles().add(role);

		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user
		);
		confirmationTokenServiceImpl.saveConfirmationToken(confirmationToken);
//       TODO: SEND EMAIL
		return token;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email).
				orElseThrow(() -> new UsernameNotFoundException("no user"));

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

		return new org.springframework.security.core.userdetails.User (user.getEmail(), user.getPassword(), authorities );
	}

	public User editUser(User user) {
		User userToUpdate = userRepo.findByEmail(user.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));

		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setFirstAddress(user.getFirstAddress());
		userToUpdate.setSecondAddress(user.getSecondAddress());

		return userRepo.save(userToUpdate);
	}

	public Role saveRole(Role role) {
		log.info("save role {}", role.getName());
		return roleService.save(role);
	}

	public RoleUserResDto addRoleToUse(String userEmail, String roleName) {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("Email not found"));
		Role role = roleService.findByName(roleName).orElseThrow(() -> new EntityNotFoundException("Role not found"));
		user.getRoles().add(role);

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role added.");
	}

	public RoleUserResDto removeRole(RoleUserDto form) {
		User user = userRepo.findByEmail(form.getEmail())
				.orElseThrow(() -> new EntityNotFoundException("Email not found " + form.getEmail()));

		Role role = roleService.findByName(form.getRoleName())
				.orElseThrow(() -> new EntityNotFoundException("Role not found "  + form.getRoleName()));
		user.getRoles().remove(role);

		return new RoleUserResDto(user.getEmail(), role.getName(), "Role removed.");
	}

	public BigDecimal chargeMoney(String email, Payment payment) {
		User updateUser = userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not found"));
		updateUser.getPayments().add(payment);
		updateUser.setCurrentMoney(updateUser.getCurrentMoney().add(payment.getAmount()));
		userRepo.save(updateUser);

		return updateUser.getCurrentMoney();
	}

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));
	}

	public Page<User> getUsers(String email, int pageNo, int pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return email.isEmpty()	? userRepo.findAll(paging) : userRepo.findByEmailContaining(email, paging);
	}

	public User deleteUserByEmail(String email) {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Email not found"));
		userRepo.delete(user);
		return user;
	}

	@Async
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper =
					new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			helper.setFrom("hello@lol.com");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("failed to send email", e);
			throw new IllegalStateException("failed to send email");
		}
	}
	private String buildEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
				"\n" +
				"<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
				"\n" +
				"  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
				"    <tbody><tr>\n" +
				"      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
				"        \n" +
				"        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
				"          <tbody><tr>\n" +
				"            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
				"                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
				"                  <tbody><tr>\n" +
				"                    <td style=\"padding-left:10px\">\n" +
				"                  \n" +
				"                    </td>\n" +
				"                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
				"                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
				"                    </td>\n" +
				"                  </tr>\n" +
				"                </tbody></table>\n" +
				"              </a>\n" +
				"            </td>\n" +
				"          </tr>\n" +
				"        </tbody></table>\n" +
				"        \n" +
				"      </td>\n" +
				"    </tr>\n" +
				"  </tbody></table>\n" +
				"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
				"    <tbody><tr>\n" +
				"      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
				"      <td>\n" +
				"        \n" +
				"                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
				"                  <tbody><tr>\n" +
				"                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
				"                  </tr>\n" +
				"                </tbody></table>\n" +
				"        \n" +
				"      </td>\n" +
				"      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
				"    </tr>\n" +
				"  </tbody></table>\n" +
				"\n" +
				"\n" +
				"\n" +
				"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
				"    <tbody><tr>\n" +
				"      <td height=\"30\"><br></td>\n" +
				"    </tr>\n" +
				"    <tr>\n" +
				"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
				"      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
				"        \n" +
				"            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
				"        \n" +
				"      </td>\n" +
				"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
				"    </tr>\n" +
				"    <tr>\n" +
				"      <td height=\"30\"><br></td>\n" +
				"    </tr>\n" +
				"  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
				"\n" +
				"</div></div>";
	}
}