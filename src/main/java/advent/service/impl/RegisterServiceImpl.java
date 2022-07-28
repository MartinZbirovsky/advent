package advent.service.impl;

import advent.dto.requestDto.RegistrationReqDto;
import advent.dto.responseDto.UserCreateResDto;
import advent.model.Address;
import advent.model.ConfirmationToken;
import advent.model.Role;
import advent.model.User;
import advent.service.intf.RoleService;
import advent.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RegisterServiceImpl {
    private final Validator validator;
    private final UserServiceImpl userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenServiceImpl confirmationTokenServiceImpl;
    private final EmailServiceImpl emailService;

    /**
     * Validate user registration and change fist character to upper case
     * @param userToRegister - User model with basic data line name email password
     * @return Validated instance or User with full detail
     */
    public UserCreateResDto registerUser(RegistrationReqDto userToRegister) {
        userToRegister.setFirstName(validator.capitalizeFirstCharacter(userToRegister.getFirstName()));
        userToRegister.setLastName(validator.capitalizeFirstCharacter(userToRegister.getLastName()));

        if (userService.findByEmail(userToRegister.getEmail()).isPresent())
            throw new IllegalStateException("Email already taken");

        if (validator.email(userToRegister.getEmail())
                && validator.onlyStringWithCapital(userToRegister.getFirstName())
                && validator.onlyStringWithCapital(userToRegister.getLastName()))
            throw new IllegalStateException("Invalid credentials");

        return createRegisterUser( new User(
                userToRegister.getFirstName(),
                userToRegister.getLastName(),
                userToRegister.getEmail(),
                userToRegister.getPassword()
        ));
    }

    /**
     * Find confirm token and set him as confirm and check if is still valid or if exists.
     * @param token - Token as string
     * @return - Return message - token / email confirm
     */
    public String confirmTokenWithEmailLink(String token) {
        String userEmail;
        ConfirmationToken confirmationToken = confirmationTokenServiceImpl.getToken(token);

        if (confirmationToken.getConfirmedAt() != null)
            throw new IllegalStateException("email already confirmed");

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new IllegalStateException("token expired");

        confirmationTokenServiceImpl.setConfirmedAt(token);
        userEmail = confirmationToken.getAppUser().getEmail();
        userService.enableUser(userEmail);

        return userEmail + " confirmed";
    }

    /**
     * Send new confirm token if first expire.
     * @param userEmail - User email
     * @return - Return message, new confirm token was sent to user email.
     */
    public String resendConfirmToken(String userEmail){
        User user = userService.getUserByEmail(userEmail);

        if(user.getConfirmationTokens().getConfirmedAt() != null || user.isEnabled())
            throw new UsernameNotFoundException("User has already been verified");

        user.getConfirmationTokens().setExpiresAt(LocalDateTime.now().plusMinutes(30));
        emailService.sendEmail(user.getEmail(), user.getConfirmationTokens().getToken());

        return "New confirm email send to " + userEmail;
    }

    /**
     * Create new user in registration.
     * @param user - User model with full details
     * @return User with reduced information ID and email
     */
    private UserCreateResDto createRegisterUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setFirstAddress(new Address());
        user.setSecondAddress(new Address());
        user.setPassword(encodedPassword);
        User createdUser = userService.saveNewUser(user);

        Role role = roleService.findByName("ROLE_ADMIN");
        user.getRoles().add(role);

        String confirmToken = createConfirmToken(user);
        emailService.sendEmail(user.getEmail(), confirmToken);

        return new UserCreateResDto(createdUser.getId(), createdUser.getEmail());
    }

    /**
     * Create confirm token with user email
     * @param user - User model
     * @return Confirm token as String
     */
    private String createConfirmToken(User user){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenServiceImpl.saveConfirmationToken(confirmationToken);
        return token;
    }
}
