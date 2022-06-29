package advent.cons;

import java.math.BigDecimal;

/**
 * Generaly used values like page size, default page and regex patterns for validation
 */
public final class GeneralCons {

    // Pagination default values
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "50";
    public static final BigDecimal ADS_PRICE = new BigDecimal(-10);

    // User validation
    public static final String ONLY_STRING_START_WITH_CAPITAL = "[A-Z][a-z]*";
    public static final String VALID_EMAIL_ADDRESS_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

}
