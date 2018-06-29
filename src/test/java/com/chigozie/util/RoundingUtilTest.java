package com.chigozie.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.chigozie.util.RoundingUtil.*;

class RoundingUtilTest {

    private final double num = 14.3353333;

    @Test
    public void should_return_rounde_value_when_decimal_places_is_greater_than_zero() {
        Assertions.assertEquals(14.34, round(num, 2));
    }

    @Test
    public void should_throw_exception_when_decimal_places_is_less_than_zero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> round(num,-1));
    }

}