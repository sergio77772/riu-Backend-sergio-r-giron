package com.sergio.hotelsearch.adapter.output.persistence.repository;

import com.sergio.hotelsearch.adapter.output.persistence.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaSearchRepository extends JpaRepository<SearchEntity, String> {

    Optional<SearchEntity> findBySearchId(String searchId);
    long countByHotelIdAndCheckInAndCheckOutAndAges(
            String hotelId,
            LocalDate checkIn,
            LocalDate checkOut,
            String ages
    );
}
