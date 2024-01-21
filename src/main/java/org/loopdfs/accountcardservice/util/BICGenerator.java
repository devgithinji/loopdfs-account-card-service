package org.loopdfs.accountcardservice.util;

import java.util.Random;

public final class BICGenerator {

    private static final String[] bics = {"EB456KE", "SB789TZ", "IM587UG", "NA678MQ"};
    private static final Random random = new Random();


    private BICGenerator() {

    }

    public static String getBIC() {
        int randoIndex = random.nextInt(bics.length);
        return bics[randoIndex];
    }
}
