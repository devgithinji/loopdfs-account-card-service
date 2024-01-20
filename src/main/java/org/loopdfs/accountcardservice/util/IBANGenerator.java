package org.loopdfs.accountcardservice.util;

import java.util.Random;

public class IBANGenerator {
    private static final String[] COUNTRY_CODES = {"GB", "DE", "FR", "ES"};
    private static final String[] BANK_CODES = {"NWBK", "COBA", "BNPA", "SABE"};

    private static final Random random = new Random();


    public static String getIBAN() {
        String countryCode = getRandomElement(COUNTRY_CODES);
        String bankCode = getRandomElement(BANK_CODES);
        Long accountNumber = 10000000 + random.nextLong(99999999);
        return String.format("%s00%s%d", countryCode, bankCode, accountNumber);
    }

    private static String getRandomElement(String[] elements) {
        int randomIndex = random.nextInt(elements.length);
        return elements[randomIndex];
    }
}
