package com.jpmc.theater;

import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private Showing showing;
    private int audienceCount;

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    public double totalFee() {
        return showing.calculateFee(audienceCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return Objects.equals(customer, reservation.customer)
                && Objects.equals(showing, reservation.showing)
                && Objects.equals(audienceCount, reservation.audienceCount);
    }
}