package advent.service.impl;

import advent.dto.requestDto.RegistrationReqDto;
import advent.dto.requestDto.RoleUserDto;
import advent.dto.responseDto.RoleUserResDto;
import advent.model.*;
import advent.model.Address;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
	private final Validator validator;
	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	public String register(RegistrationReqDto request) {

		if (!validator.email(request.getEmail())) throw new IllegalStateException("Email not valid");
		if (!validator.onlyStringWithCapital(request.getFirstName())) throw new IllegalStateException("First name not valid");
		if (!validator.onlyStringWithCapital(request.getFirstName())) throw new IllegalStateException("Second name not valid");

		String token = signUpUser(new User(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword()
		));
		sendEmail(request.getEmail(), token);

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
		user.setFirstAddress(new Address());
		user.setSecondAddress(new Address());
		user.setPassword(encodedPassword);
		userRepo.save(user);

		Role role = roleService.findByName("ROLE_ADMIN").orElseThrow(() -> new EntityNotFoundException("Email not found"));
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

	/**
	 * Charge money. Send user email and payment body to increase account money balance.
	 * @param email - User email
	 * @param payment - Payment
	 * @return
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
	 * @return
	 */
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
	public void sendEmail(String emailTo, String userToken) {
		String activationLink = "http://localhost:8080/api/users/registration/confirm?token=" + userToken;

		// Email setting
		String host = "smtp.gmail.com";
		String from = "";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		String msgToSend = buildEmail(emailTo, activationLink);
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "pass");
			}
		});

		// Message build
		session.setDebug(true);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject("ADS. Auth.");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msgToSend, "text/html; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			System.out.println("sending...");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**
	 * Create email template before send. Contains username and activation link.
	 * @param userEmail - User name or email
	 * @param link - Link for account activation
	 * @return
	 */
	private String buildEmail(String userEmail, String link) {
		return "<!DOCTYPE html>\n" +
				"<html>\n" +
				"\n" +
				"<head>\n" +
				"    <title></title>\n" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
				"    <style type=\"text/css\">\n" +
				"        @media screen {\n" +
				"            @font-face {\n" +
				"                font-family: 'Lato';\n" +
				"                font-style: normal;\n" +
				"                font-weight: 400;\n" +
				"                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
				"            }\n" +
				"\n" +
				"            @font-face {\n" +
				"                font-family: 'Lato';\n" +
				"                font-style: normal;\n" +
				"                font-weight: 700;\n" +
				"                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
				"            }\n" +
				"\n" +
				"            @font-face {\n" +
				"                font-family: 'Lato';\n" +
				"                font-style: italic;\n" +
				"                font-weight: 400;\n" +
				"                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
				"            }\n" +
				"\n" +
				"            @font-face {\n" +
				"                font-family: 'Lato';\n" +
				"                font-style: italic;\n" +
				"                font-weight: 700;\n" +
				"                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
				"            }\n" +
				"        }\n" +
				"\n" +
				"        /* CLIENT-SPECIFIC STYLES */\n" +
				"        body,\n" +
				"        table,\n" +
				"        td,\n" +
				"        a {\n" +
				"            -webkit-text-size-adjust: 100%;\n" +
				"            -ms-text-size-adjust: 100%;\n" +
				"        }\n" +
				"\n" +
				"        table,\n" +
				"        td {\n" +
				"            mso-table-lspace: 0pt;\n" +
				"            mso-table-rspace: 0pt;\n" +
				"        }\n" +
				"\n" +
				"        img {\n" +
				"            -ms-interpolation-mode: bicubic;\n" +
				"        }\n" +
				"\n" +
				"        /* RESET STYLES */\n" +
				"        img {\n" +
				"            border: 0;\n" +
				"            height: auto;\n" +
				"            line-height: 100%;\n" +
				"            outline: none;\n" +
				"            text-decoration: none;\n" +
				"        }\n" +
				"\n" +
				"        table {\n" +
				"            border-collapse: collapse !important;\n" +
				"        }\n" +
				"\n" +
				"        body {\n" +
				"            height: 100% !important;\n" +
				"            margin: 0 !important;\n" +
				"            padding: 0 !important;\n" +
				"            width: 100% !important;\n" +
				"        }\n" +
				"\n" +
				"        /* iOS BLUE LINKS */\n" +
				"        a[x-apple-data-detectors] {\n" +
				"            color: inherit !important;\n" +
				"            text-decoration: none !important;\n" +
				"            font-size: inherit !important;\n" +
				"            font-family: inherit !important;\n" +
				"            font-weight: inherit !important;\n" +
				"            line-height: inherit !important;\n" +
				"        }\n" +
				"\n" +
				"        /* MOBILE STYLES */\n" +
				"        @media screen and (max-width:600px) {\n" +
				"            h1 {\n" +
				"                font-size: 32px !important;\n" +
				"                line-height: 32px !important;\n" +
				"            }\n" +
				"        }\n" +
				"\n" +
				"        /* ANDROID CENTER FIX */\n" +
				"        div[style*=\"margin: 16px 0;\"] {\n" +
				"            margin: 0 !important;\n" +
				"        }\n" +
				"    </style>\n" +
				"</head>\n" +
				"\n" +
				"<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
				"    <!-- HIDDEN PREHEADER TEXT -->\n" +
				"    <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account.\n" +
				"    </div>\n" +
				"    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
				"        <!-- LOGO -->\n" +
				"        <tr>\n" +
				"            <td bgcolor=\"#FFA73B\" align=\"center\">\n" +
				"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
				"                    <tr>\n" +
				"                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
				"                    </tr>\n" +
				"                </table>\n" +
				"            </td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
				"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
				"                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Welcome " + userEmail + "!</h1> <img src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                </table>\n" +
				"            </td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
				"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
				"                            <p style=\"margin: 0;\">We're excited to have you get started. First, you need to confirm your account. Just press the button below.</p>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"left\">\n" +
				"                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
				"                                <tr>\n" +
				"                                    <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
				"                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
				"                                            <tr>\n" +
				"                                                <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#FFA73B\"><a href=" + link + "style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #FFA73B; display: inline-block;\">Confirm Account</a></td>\n" +
				"                                            </tr>\n" +
				"                                        </table>\n" +
				"                                    </td>\n" +
				"                                </tr>\n" +
				"                            </table>\n" +
				"                        </td>\n" +
				"                    </tr> <!-- COPY -->\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
				"                            <p style=\"margin: 0;\">If that doesn't work, copy and paste the following link in your browser:</p>\n" +
				"                        </td>\n" +
				"                    </tr> <!-- COPY -->\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
				"                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #FFA73B;\">" + link  +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
				"                            <p style=\"margin: 0;\">If you have any questions, just reply to this email&mdash;we're always happy to help out.</p>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
				"                            <p style=\"margin: 0;\">Cheers,<br>ADS Team</p>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                </table>\n" +
				"            </td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
				"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#FFECD1\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
				"                            <h2 style=\"font-size: 20px; font-weight: 400; color: #111111; margin: 0;\">Need more help?</h2>\n" +
				"                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #FFA73B;\">We&rsquo;re here to help you out</a></p>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                </table>\n" +
				"            </td>\n" +
				"        </tr>\n" +
				"        <tr>\n" +
				"            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
				"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
				"                    <tr>\n" +
				"                        <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\"> <br>\n" +
				"                            <p style=\"margin: 0;\">If these emails get annoying, please feel free to <a href=\"#\" target=\"_blank\" style=\"color: #111111; font-weight: 700;\">unsubscribe</a>.</p>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                </table>\n" +
				"            </td>\n" +
				"        </tr>\n" +
				"    </table>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
	}
}