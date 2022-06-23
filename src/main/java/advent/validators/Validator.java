package advent.validators;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static advent.cons.GeneralCons.*;

@Service
public class Validator {

    public boolean email(String emailToValidate) {
        Pattern pattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailToValidate);
        return matcher.find();
    }
    public boolean onlyStringWithCapital(String firstName) {
        return firstName.matches(ONLY_STRING_START_WITH_CAPITAL);
    }

}
