package com.sergio.hotelsearch.adapter.output.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "search_ages", joinColumns = @JoinColumn(name = "search_id"))
    @Column(name = "age")
    @OrderColumn(name = "age_index")
    private List<Integer> ages;

    protected SearchEntity() {
    }

    public SearchEntity(String searchId,
            String hotelId,
            LocalDate checkIn,
            LocalDate checkOut,
            List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = List.copyOf(ages);
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

    public List<Integer> getAges() {
        return List.copyOf(ages);
    }
}
