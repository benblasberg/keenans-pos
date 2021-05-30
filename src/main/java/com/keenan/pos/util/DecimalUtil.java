package com.keenan.pos.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {

    /**
     * Rounds the given double to the nearest hundredths place.
     * @param d the double to round
     * @return the given double rounded to the nearest hundredths place
     */
    public static BigDecimal toDecimal(final double d) {
        return BigDecimal.valueOf( d ).setScale( 2, RoundingMode.HALF_EVEN );
    }
}
