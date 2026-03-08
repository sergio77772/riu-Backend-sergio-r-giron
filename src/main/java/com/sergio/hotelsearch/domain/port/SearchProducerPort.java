package com.sergio.hotelsearch.domain.port;


import com.sergio.hotelsearch.domain.model.Search;

public interface SearchProducerPort {

    void publishSearch(Search search);

}