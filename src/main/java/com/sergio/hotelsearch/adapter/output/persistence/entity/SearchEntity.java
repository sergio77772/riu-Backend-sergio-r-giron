package com.sergio.hotelsearch.adapter.output.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "searches")
public class SearchEntity {

    @Id
    @Column(name = "search_id")
    private String searchId;

    @Column(name = "hotel_id")
    private String hotelId;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @Column(name = "ages")
    private String ages;

    protected SearchEntity() {
    }

    public SearchEntity(String searchId,
                        String hotelId,
                        LocalDate checkIn,
                        LocalDate checkOut,
                        String ages) {
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