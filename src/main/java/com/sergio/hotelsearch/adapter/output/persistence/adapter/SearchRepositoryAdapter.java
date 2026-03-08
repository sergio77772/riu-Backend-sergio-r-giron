package com.sergio.hotelsearch.adapter.output.persistence.adapter;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.adapter.output.persistence.entity.SearchEntity;
import com.sergio.hotelsearch.adapter.output.persistence.repository.JpaSearchRepository;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchRepositoryAdapter implements SearchRepositoryPort {

    private final JpaSearchRepository repository;

    public SearchRepositoryAdapter(JpaSearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Search search) {

        String ages = search.ages()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        SearchEntity entity = new SearchEntity(
                search.searchId(),
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                ages
        );

        repository.save(entity);
    }

    @Override
    public Optional<Search> findBySearchId(String searchId) {

        return repository.findBySearchId(searchId)
                .map(entity -> {

                    List<Integer> ages = Arrays.stream(entity.getAges().split(","))
                            .map(Integer::parseInt)
                            .toList();

                    return new Search(
                            entity.getSearchId(),
                            entity.getHotelId(),
                            entity.getCheckIn(),
                            entity.getCheckOut(),
                            ages
                    );
                });
    }

    @Override
    public long countBySearch(Search search) {

        String ages = search.ages()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return repository.countByHotelIdAndCheckInAndCheckOutAndAges(
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                ages
        );
    }
}