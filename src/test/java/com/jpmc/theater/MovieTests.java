package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    @ParameterizedTest
    @CsvSource({"True,15", "False,16"}) //25% discount (11AM-4PM), 20% discount (Movie Code Special)
    void testGetTicketPriceAfterDiscountPercentsOnly(boolean isBetween11AMand4PM, double expectedValue) {
        LocalDateTime datetime = isBetween11AMand4PM ? LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)) : LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));


        Movie movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1);
        Showing showing = new Showing(
                movie,
                1,
                datetime
        );

        assertEquals(movie.getTicketPriceAfterDiscount(showing), expectedValue);
    }

    @ParameterizedTest
    @CsvSource({"1,17.0", "2,18.0", "3,19.0"}) //25% discount (11AM-4PM), 20% discount (Movie Code Special)
    void testGetTicketPriceAfterDiscountFlatRateOnly(int sequence, double expectedValue) {
        //setting datetime to outside 11AM-4pm and on the 7th so the fee is $1 off if it's not the first or second showing
        LocalDateTime datetime = LocalDateTime.of(LocalDate.of(2022,11,7), LocalTime.of(23, 0));

        Movie movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 0);
        Showing showing = new Showing(
                movie,
                sequence,
                datetime
        );
        assertEquals(movie.getTicketPriceAfterDiscount(showing), expectedValue);
    }


}
