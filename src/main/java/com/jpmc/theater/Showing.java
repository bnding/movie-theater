package com.jpmc.theater;

import java.time.LocalDateTime;
import java.util.Objects;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartDateTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartDateTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartDateTime = showStartDateTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartDateTime() {
        return showStartDateTime;
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public double calculateFee(int audienceCount) {
        return movie.getTicketPriceAfterDiscount(this) * audienceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showing showing = (Showing) o;
        return Objects.equals(movie, showing.movie)
                && Objects.equals(sequenceOfTheDay, showing.sequenceOfTheDay)
                && Objects.equals(showStartDateTime, showing.showStartDateTime);
    }
}
