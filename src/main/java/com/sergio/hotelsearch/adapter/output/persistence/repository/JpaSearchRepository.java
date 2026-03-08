package com.sergio.hotelsearch.adapter.output.persistence.repository;

import com.sergio.hotelsearch.adapter.output.persistence.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaSearchRepository extends JpaRepository<SearchEntity, String> {

    Optional<SearchEntity> findBySearchId(String searchId);

    @Query("SELECT COUNT(s) FROM SearchEntity s " +
            "WHERE s.hotelId = :hotelId " +
            "AND s.checkIn = :checkIn " +
            "AND s.checkOut = :checkOut " +
            "AND s.ages = :ages")
    long countByHotelIdAndCheckInAndCheckOutAndAges(
            @Param("hotelId") String hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("ages") String ages);
}
