package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateProviderTests {
    @Test
    void testCurrentTime() {
        assertEquals(LocalDateTime.now().toLocalDate(), LocalDateProvider.singleton().currentDate());
    }
}
