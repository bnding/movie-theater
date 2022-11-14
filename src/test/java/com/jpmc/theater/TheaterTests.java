package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TheaterTests {
    @ParameterizedTest
    @CsvSource({"3,4,27.0", "8,10,64.0"}) //25% discount (11AM-4PM), 20% discount (Movie Code Special)
    void testTheaterReservationsWithPercentageDiscounts(int sequence, int numTickets, double expectedFeeAfterDiscount) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer customer = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(customer, sequence, numTickets);
        assertEquals(reservation.totalFee(), expectedFeeAfterDiscount);
    }

    @Test
    void testTheaterReservationSuccess() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer customer = new Customer("John Doe", "id-12345");
        Reservation reservationMadeByTheater = theater.reserve(customer, 1, 3);

        Showing showing = theater.getShowingFromSchedule(1);
        Reservation reservationObj = new Reservation(customer, showing, 3);

        assertTrue(reservationObj.equals(reservationMadeByTheater));
    }

    @Test
    void testGetShowingSuccess() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Showing showing = theater.getSchedule().get(4);

        assertTrue(showing.equals(theater.getShowingFromSchedule(5)));
    }

    @Test
    void testGetShowingFailure() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        int invalidSequence = theater.getSchedule().size()+1;

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            theater.getShowingFromSchedule(invalidSequence);
        });

        assertEquals(exception.getMessage(), "ERROR: Unable to find any showing for given sequence " + invalidSequence);
    }
    @ParameterizedTest
    @CsvSource({"1,4,32.0", "2, 7, 42.0","9,2,16.0"})//$3 discount, $2 discount (flat rate > %off), $1 discount
    void testTheaterReservationsWithFlatRateDiscounts(int sequence, int numTickets, double expectedFeeAfterDiscount) {
        LocalDateProvider localDateProvider = mock(LocalDateProvider.class);
        when(localDateProvider.currentDate()).thenReturn(LocalDate.of(2022,11,7));

        Theater theater = new Theater(localDateProvider);
        Customer customer = new Customer("John Doe", "id-12345");

        Reservation reservation = theater.reserve(customer, sequence, numTickets);
        assertEquals(reservation.totalFee(), expectedFeeAfterDiscount);

    }
    @Test
    void testTheaterReservationsWithNoDiscount() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer customer = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(customer, 9, 3);

        assertEquals(reservation.totalFee(), 27);
    }

    @Test
    void testPrintSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void testPrintJson() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printJson();
    }
}
