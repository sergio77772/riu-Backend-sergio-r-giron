package com.sergio.hotelsearch.adapter.output.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "searches")
public class SearchEntity {

    @Id
    private String searchId;

    private String hotelId;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private String ages;

    public SearchEntity() {}

    public SearchEntity(String searchId, String hotelId,
                        LocalDate checkIn, LocalDate checkOut, String ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getAges() {
        return ages;
    }
}