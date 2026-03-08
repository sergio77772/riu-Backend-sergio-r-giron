package com.sergio.hotelsearch.adapter.output.persistence.repository;

import com.sergio.hotelsearch.adapter.output.persistence.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaSearchRepository extends JpaRepository<SearchEntity, String> {

    Optional<SearchEntity> findBySearchId(String searchId);

    @Query("SELECT COUNT(s) FROM SearchEntity s WHERE s.hotelId = :hotelId AND s.checkIn = :checkIn AND s.checkOut = :checkOut AND s.ages = :ages")
    long countByHotelIdAndCheckInAndCheckOutAndAges(
            String hotelId,
            LocalDate checkIn,
            LocalDate checkOut,
            String ages);
}