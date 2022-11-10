package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;
import java.time.LocalTime;

public class Movie {
    private static final int MOVIE_CODE_SPECIAL = 1;
    private final String title;
    private final Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        LocalTime currentShowingLocalTime = showing.getStartDateTime().toLocalTime();
        int currentShowingDayOfMonth = showing.getStartDateTime().toLocalDate().getDayOfMonth();
        int currentShowingSequence = showing.getSequenceOfTheDay();

        return ticketPrice - getDiscount(currentShowingLocalTime, currentShowingDayOfMonth, currentShowingSequence);
    }

    private double getDiscount(LocalTime currentShowingLocalTime, int currentShowingDayOfMonth, int currentShowingSequence) {
        double percentOffDiscount = 0;
        double flatSumDiscount = 0;

        if(inTimeRangeExclusive(LocalTime.of(10, 59), LocalTime.of(16,1), currentShowingLocalTime)) {
            percentOffDiscount = ticketPrice * 0.25; // 25% discount for movies showing between 11AM - 4PM inclusive
        } else if (specialCode == MOVIE_CODE_SPECIAL) {
            percentOffDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        if (currentShowingSequence == 1) {
            flatSumDiscount = 3; // $3 discount for 1st show
        } else if (currentShowingSequence == 2) {
            flatSumDiscount = 2; // $2 discount for 2nd show
        } else if (currentShowingDayOfMonth == 7) {
            flatSumDiscount = 1; // $1 discount for showings on the 7th
        }

        // return largest between discounts from percent and flat sum
        return Math.max(percentOffDiscount, flatSumDiscount);
    }

    private boolean inTimeRangeExclusive(LocalTime after, LocalTime before, LocalTime currLocalTime) {
        return currLocalTime.isAfter(after) && currLocalTime.isBefore(before);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, runningTime, ticketPrice, specialCode);
    }
}