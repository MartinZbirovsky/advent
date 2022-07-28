package advent.validators;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static advent.cons.GeneralCons.*;

/**
 * This class contains validation methods
 */
@Service
public class Validator {

    /**
     * Verify that the email has the required format
     * @param emailToValidate
     * @return
     */
    public boolean email(String emailToValidate) {
        Pattern pattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailToValidate);
        return matcher.find();
    }

    /**
     * Verify that the name has the required format - only letters allowed and fis is capital
     * @param name - Name to validate
     * @return
     */
    public boolean onlyStringWithCapital(String name) {
        return name.matches(ONLY_STRING_START_WITH_CAPITAL);
    }

    /**
     * Change first character to upper case other to lower.
     * @param textToCapitalize - Text to capitalize
     * @return - Text after capitalize
     */
    public String capitalizeFirstCharacter(String textToCapitalize){
        String firstLetter = textToCapitalize.substring(0, 1);
        String remainingLetters = textToCapitalize.substring(1, textToCapitalize.length());

        firstLetter = firstLetter.toUpperCase();

        return firstLetter + remainingLetters;
    }
}
