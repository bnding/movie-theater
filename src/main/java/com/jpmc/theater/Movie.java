package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;
import java.time.LocalTime;

public class Movie {
    private static final int MOVIE_CODE_SPECIAL = 1;
    private final String title;
    private final Duration runningTime;
    private double fullTicketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.fullTicketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public int getSpecialCode() {
        return specialCode;
    }

    public double getFullTicketPrice() {
        return fullTicketPrice;
    }

    //Breaking down showing object for local time and day to calculate discounts in getDiscount()
    public double getTicketPriceAfterDiscount(Showing showing) {
        LocalTime currentShowingLocalTime = showing.getStartDateTime().toLocalTime();
        int currentShowingDayOfMonth = showing.getStartDateTime().toLocalDate().getDayOfMonth();
        int currentShowingSequence = showing.getSequenceOfTheDay();

        return fullTicketPrice - calculateDiscount(currentShowingLocalTime, currentShowingDayOfMonth, currentShowingSequence);
    }

    //Takes in showing's local time, day of month, and sequence to calculate discounts
    private double calculateDiscount(LocalTime currentShowingLocalTime, int currentShowingDayOfMonth, int currentShowingSequence) {
        double percentOffDiscount = 0;
        double flatSumDiscount = 0;

        //percent discounts grouped together
        if(inTimeRangeExclusive(LocalTime.of(10, 59), LocalTime.of(16,1), currentShowingLocalTime)) {
            percentOffDiscount = fullTicketPrice * 0.25; // 25% discount for movies showing between 11AM - 4PM inclusive
        } else if (specialCode == MOVIE_CODE_SPECIAL) {
            percentOffDiscount = fullTicketPrice * 0.2;  // 20% discount for special movie
        }

        //flate rate discounts grouped together
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
        return Double.compare(movie.fullTicketPrice, fullTicketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, runningTime, fullTicketPrice, specialCode);
    }
}