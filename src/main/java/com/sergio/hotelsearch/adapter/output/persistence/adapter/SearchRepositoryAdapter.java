package com.sergio.hotelsearch.adapter.output.persistence.adapter;

import com.sergio.hotelsearch.domain.model.Search;
import com.sergio.hotelsearch.adapter.output.persistence.entity.SearchEntity;
import com.sergio.hotelsearch.adapter.output.persistence.repository.JpaSearchRepository;
import com.sergio.hotelsearch.domain.port.SearchRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SearchRepositoryAdapter implements SearchRepositoryPort {

        private final JpaSearchRepository repository;

        public SearchRepositoryAdapter(JpaSearchRepository repository) {
                this.repository = repository;
        }

        @Override
        public void save(Search search) {

                SearchEntity entity = new SearchEntity(
                                search.searchId(),
                                search.hotelId(),
                                search.checkIn(),
                                search.checkOut(),
                                search.ages()
                );

                repository.save(entity);
        }

        @Override
        public Optional<Search> findBySearchId(String searchId) {

                return repository.findBySearchId(searchId)
                                .map(this::toDomain);
        }

        @Override
        public long countBySearch(Search search) {

                return repository.findByHotelIdAndCheckInAndCheckOut(
                                search.hotelId(),
                                search.checkIn(),
                                search.checkOut())
                                .stream()
                                .filter(e -> e.getAges().equals(search.ages()))
                                .count();
        }

        private Search toDomain(SearchEntity entity) {

                return new Search(
                                entity.getSearchId(),
                                entity.getHotelId(),
                                entity.getCheckIn(),
                                entity.getCheckOut(),
                                List.copyOf(entity.getAges()));
        }
}
