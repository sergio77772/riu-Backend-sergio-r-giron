package com.sergio.hotelsearch.domain.port;
import com.sergio.hotelsearch.domain.model.Search;

import java.util.Optional;

public interface SearchRepositoryPort {

    void save(Search search);

    Optional<Search> findBySearchId(String searchId);

    long countBySearch(Search search);
}