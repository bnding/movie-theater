package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTests {

    @ParameterizedTest
    @CsvSource({"True,45.0", "False,48.0"}) //25% discount (11AM-4PM), 20% discount (Movie Code Special)
    void testTotalFeeWithPercentDiscounts(boolean isBetween11AMand4PM, double expectedValue) {
        Customer customer = new Customer("John Doe", "unused-id");
        LocalDateTime datetime = isBetween11AMand4PM ? LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)) : LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));

        Showing showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1),
                1,
                datetime
        );
        Reservation reservation = new Reservation(customer, showing, 3);
        assertEquals(reservation.totalFee(), expectedValue);
    }

    @ParameterizedTest
    @CsvSource({"1,34.0", "2,36.0", "3,38.0"}) //25% discount (11AM-4PM), 20% discount (Movie Code Special)
    void testTotalFeeWithFlatRateDiscounts(int sequence, double expectedValue) {
        Customer customer = new Customer("John Doe", "unused-id");

        //setting datetime to outside 11AM-4pm and on the 7th so the fee is $1 off if it's not the first or second showing
        LocalDateTime datetime = LocalDateTime.of(LocalDate.of(2022,11,7), LocalTime.of(23, 0));

        Showing showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 0),
                sequence,
                datetime
        );
        Reservation reservation = new Reservation(customer, showing, 2);
        assertEquals(reservation.totalFee(), expectedValue);
    }
}
